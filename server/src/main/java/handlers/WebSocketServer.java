package handlers;

import chess.*;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import model.AuthData;
import model.ErrorMessage;
import model.GameData;
import model.JoinGameRequest;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.*;
import websocket.messages.LoadGame;
import websocket.messages.Notifications;
import websocket.messages.Error;

import javax.management.Notification;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketServer{
  public WebSocketServer(){}

  private Map<Integer, Set<Session>> sessionData = new HashMap<>();
  private MySqlGameDAO gameDAO = new MySqlGameDAO();


  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
      UserGameCommand command=new Gson().fromJson(message, UserGameCommand.class);

      MySqlAuthDAO authDAO=new MySqlAuthDAO();
      // Throws a custom UnauthorizedException. Yours may work differently.

      String username=authDAO.getUsername(command.getAuthString());

      //saveSession(command.getGameID(), session);

      switch (command.getCommandType()) {
        case CONNECT -> connect(session, message, username);
        case MAKE_MOVE -> makeMove(session, username, message);
        case LEAVE -> leaveGame(session, username, message);
        case RESIGN -> resign(session, username, message);
      }

  }

  private void sessionTimeout(){

    for (Set<Session> sessions : sessionData.values()) {
      Set<Session> oldSessions=new HashSet<>();
      for (Session session : sessions) {
        if (!(session.isOpen())) {
          oldSessions.add(session);
        }
      }
      for (Session session : oldSessions) {
        sessions.remove(session);
      }
    }

  }

  private void connect(Session session, String message, String username) throws IOException, DataAccessException {
    Connect connect = new Gson().fromJson(message, Connect.class);
    MySqlGameDAO gameDAO = new MySqlGameDAO();

    sessionData.computeIfAbsent(connect.getGameID(), v -> new HashSet<>());
    sessionData.get(connect.getGameID()).add(session);

    if(username == null){

      sessionTimeout();
      if(session.isOpen()) {
        Error error=new Error("Error connecting to game, Bad AuthToken");
        String e=new Gson().toJson(error);
        session.getRemote().sendString(e);
      }
      return;
    }


    if(gameDAO.getGame(connect.getGameID()) == null) {
      sessionTimeout();
      if(session.isOpen()) {
        Error error=new Error("Error connecting to game, Bad GameID");
        String e=new Gson().toJson(error);
        session.getRemote().sendString(e);
      }
      return;
    }

    GameData gameData = gameDAO.getGame(connect.getGameID());

//    // initialize the map if I haven't yet
//    sessionData.computeIfAbsent(connect.getGameID(), v -> new HashSet<>());
//    // Add the current session to the session set for the game
//    sessionData.get(connect.getGameID()).add(session);

    //notify everyone but the current user that the current user connected (as observer or player) for this game
    for(Session s : sessionData.get(connect.getGameID())) {
      if(!(s.equals(session))){
        sessionTimeout();
        if (s.isOpen()){
        String player = null;

        String blackUsername = gameData.blackUsername();
        String whiteUsername = gameData.whiteUsername();

        if(blackUsername.equals(username)){
          player = "BLACK Player";
        }
        if(whiteUsername.equals(username)){
          player = "WHITE Player";
        }
        else{
          player = "Observer";
        }
          sessionTimeout();
          if (s.isOpen()) {
            Notifications notifications=new Notifications("Player " + username + " has joined the game as the " + player);
            s.getRemote().sendString(new Gson().toJson(notifications));
          }
        }
      }

    }

    sessionTimeout();
    if(session.isOpen()) {
      LoadGame loadGame=new LoadGame(gameData.game());

      session.getRemote().sendString(new Gson().toJson(loadGame));
    }

  }


  private void makeMove(Session session, String username, String message) throws InvalidMoveException, IOException, DataAccessException {
    MakeMove makeMove = new Gson().fromJson(message, MakeMove.class);
    ChessMove move = makeMove.getMove();
    int gameID = makeMove.getGameID();

    if(username == null){
      sessionTimeout();
      if(session.isOpen()) {
        Error error=new Error("Error connecting to game, Bad AuthToken");
        session.getRemote().sendString(new Gson().toJson(error));
        return;
      }
    }
    GameData gameData = gameDAO.getGame(gameID);
    MySqlAuthDAO authDAO = new MySqlAuthDAO();
    ChessGame chessGame = gameData.game();
    if(!((username.equals(gameData.blackUsername())) || (username.equals(gameData.whiteUsername())))){
      Error error = new Error("Error , you are an observer; you can't make a move");
      session.getRemote().sendString(new Gson().toJson(error));
      return;
    }
    // find the username of who's turn it is

    makeMove.getAuthString();
    String user = authDAO.getUsername( makeMove.getAuthString());
    String player ="";
    String turn = chessGame.getTeamTurn().toString();
    if(turn.equals("WHITE")){
      player =gameData.whiteUsername();
    }
    else if(turn.equals("BLACK")){
      player =gameData.blackUsername();
    }
    if(!(user.equals(player))){

      Error error = new Error("Error , it's not your turn");
      session.getRemote().sendString(new Gson().toJson(error));
      return;
    }

    try {
      if(chessGame.isGameOver()){
        //send back to the user that they won the game instead of this line below
        System.out.println("The Game is over, no more moves can be made");
        sessionTimeout();
        if (session.isOpen()) {
          Error error = new Error("Error , the Game is over");
          session.getRemote().sendString(new Gson().toJson(error));
        }

      }
      else {
        chessGame.makeMove(move);
        boolean isInCheck = false;
        boolean isInCheckmate = false;
        boolean isInStalemate = false;
        if(chessGame.isInCheck(chessGame.getOpponentsTeamColor(chessGame.getTeamTurn()))){
          // notify the other player that they are in check
          isInCheck = true;
        }
        if(chessGame.isInCheckmate(chessGame.getOpponentsTeamColor(chessGame.getTeamTurn()))){
          isInCheckmate = true;
          chessGame.setGameOver(true);
        }
        if(chessGame.isInStalemate(chessGame.getOpponentsTeamColor(chessGame.getTeamTurn()))){
          isInStalemate = true;
          chessGame.setGameOver(true);
        }


        gameDAO.updateGame(gameData);



        for(Session s : sessionData.get(gameID)) {
          sessionTimeout();
          if (s.isOpen()) {
            // send load_game msg to all clients
            LoadGame loadGame=new LoadGame(gameData.game());

            s.getRemote().sendString(new Gson().toJson(loadGame));

            // send a notification to the others
            if (!(s.equals(session))) {

              String sP=move.getStartPosition().toString();
              String eP=move.getEndPosition().toString();

              sessionTimeout();
              if(s.isOpen()) {
                Notifications notifications=new Notifications("Player " + username + " has made a Move from " + sP + " to " + eP);
                s.getRemote().sendString(new Gson().toJson(notifications));
              }

              Notifications congratulationsNote=new Notifications("YOU WIN!!! CONGRATULATIONS!!!");

              sessionTimeout();
              if(s.isOpen()) {
                if (isInCheck) {
                  Notifications notificationInCheck=new Notifications("You are in Check");
                  s.getRemote().sendString(new Gson().toJson(notificationInCheck));
                }
                if (isInCheckmate) {
                  Notifications notificationInCheckmate=new Notifications("You are in Checkmate... Game Over");
                  s.getRemote().sendString(new Gson().toJson(notificationInCheckmate));
                  session.getRemote().sendString(new Gson().toJson(congratulationsNote));
                }
                if (isInStalemate) {
                  Notifications notificationInStalemate=new Notifications("You are in Stalemate... Game Over");
                  s.getRemote().sendString(new Gson().toJson(notificationInStalemate));
                  session.getRemote().sendString(new Gson().toJson(congratulationsNote));
                }
              }
            }
          }
        }
      }
    }catch(Exception e){
      Error error = new Error("Error " + e.getMessage());
      session.getRemote().sendString(new Gson().toJson(error));
    }
  }

  private void leaveGame(Session session, String username, String message) throws DataAccessException, IOException {
    Leave leave = new Gson().fromJson(message, Leave.class);

    MySqlGameDAO gameDAO = new MySqlGameDAO();
    String authToken = leave.getAuthString();
    int gameID = leave.getGameID();
    // remove the player from the game in the DB
    GameData gameData = gameDAO.getGame(gameID);
    ChessGame chessGame = gameData.game();
    if(chessGame.isGameOver()){
      Error error = new Error("Error , you have already resigned; the game is over");
      session.getRemote().sendString(new Gson().toJson(error));
      return;
    }
    String color="";
    if(gameData.blackUsername().equals(username)){
      color = "BLACK";
    }
    else if(gameData.whiteUsername().equals(username)){
      color = "WHITE";
    }
    else {
      System.out.println("Invalid color type");
    }
    JoinGameRequest requestedGame = new JoinGameRequest(color, gameID);
    int result = gameDAO.joinGame(requestedGame, "null");
    if(result != 200){
      System.out.println("Error invalid leave request");
    }




    for(Session s : sessionData.get(gameID)) {
      if (!(s.equals(session))) {
        sessionTimeout();
        if (s.isOpen()) {
          Notifications notifications=new Notifications(" " + username + " has just left the game");
          s.getRemote().sendString(new Gson().toJson(notifications));
        }
      }
    }
    sessionData.get(gameID).remove(session);

  }

  private void resign(Session session, String username, String message) throws DataAccessException, IOException {
    Resign resign = new Gson().fromJson(message, Resign.class);
    MySqlGameDAO gameDAO = new MySqlGameDAO();
    GameData gameData = gameDAO.getGame(resign.getGameID());

    // change the chess game
    ChessGame chessGame = gameData.game();
    if((username.equals(gameData.blackUsername())) || (username.equals(gameData.whiteUsername()))){
      //if game is already over send error and return
      if(chessGame.isGameOver()){
        Error error = new Error("Error , you have already resigned; the game is over");
        session.getRemote().sendString(new Gson().toJson(error));
        return;
      }

      //call the setter to change the value
      chessGame.setGameOver(true);
      //update it in the database
      gameDAO.updateGame(gameData);

      //send Notification
      int gameID = resign.getGameID();
      for(Session s : sessionData.get(gameID)) {
          String player = gameData.blackUsername();
          String blackUsername = gameData.blackUsername();

          if(blackUsername.equals(username)){
            player = gameData.whiteUsername();
          }
          sessionTimeout();
          if (s.isOpen()) {
            Notifications notifications=new Notifications(" " + username + " has just forfeited the game, " + player + " Wins the Game!!!");
            s.getRemote().sendString(new Gson().toJson(notifications));
          }
        }
    }
    else{
      Error error = new Error("Error , you are an observer; this action is restricted");
      session.getRemote().sendString(new Gson().toJson(error));
    }
  }

}
