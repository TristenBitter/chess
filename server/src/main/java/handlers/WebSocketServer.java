package handlers;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
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

import static spark.Spark.connect;

@WebSocket
public class WebSocketServer{
  public WebSocketServer(){}

  private Map<Integer, Set<Session>> sessionData = new HashMap<>();
  private ChessGame chessGame = new ChessGame();
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
    Set<Session> oldSessions=new HashSet<>();
    for (Set<Session> sessions : sessionData.values()) {
      for (Session session : sessions) {
        if (!(session.isOpen())) {
          oldSessions.add(session);
        }
      }
    }
    for (Session session : oldSessions) {
      sessionData.values().remove(session);
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
        //throw new IOException(error.getMessage());
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
//
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
            //s.getRemote().sendString("Player " + username + " has joined the game as the " + player );
          }
        }
      }

    }
    //sessionData.get(connect.getGameID()).get(session);

    //session.getRemote().sendString("you've joined the game, congrats!");

    //send a LOAD_GAME message back to the client

    sessionTimeout();
    if(session.isOpen()) {
      LoadGame loadGame=new LoadGame(gameData.game());

      session.getRemote().sendString(new Gson().toJson(loadGame));
    }

  }


  private void makeMove(Session session, String username, String message) throws InvalidMoveException {
    MakeMove makeMove = new Gson().fromJson(message, MakeMove.class);
    ChessMove move = makeMove.getMove();
    int gameID = makeMove.getGameID();
    // do stuff now
    try {
      if(chessGame.isGameOver()){
        //send back to the user that they won the game instead of this line below
        System.out.println("The Game is over, no more moves can be made");
        sessionTimeout();
        if (session.isOpen()) {
          session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Error making move. The game is over")));
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
          chessGame.setGameOver();
        }
        if(chessGame.isInStalemate(chessGame.getOpponentsTeamColor(chessGame.getTeamTurn()))){
          isInStalemate = true;
          chessGame.setGameOver();
        }

        GameData gameData2=gameDAO.getGame(gameID);
        gameDAO.updateGame(gameData2);



        for(Session s : sessionData.get(gameID)) {
          sessionTimeout();
          if (s.isOpen()) {
            // send load_game msg to all clients
            LoadGame loadGame=new LoadGame(gameData2.game());

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
    }
  }

  private void leaveGame(Session session, String username, String message) throws DataAccessException, IOException {
    Leave leave = new Gson().fromJson(message, Leave.class);

    MySqlGameDAO gameDAO = new MySqlGameDAO();
    String authToken = leave.getAuthString();
    int gameID = leave.getGameID();
    // remove the player from the game in the DB
    GameData gameData = gameDAO.getGame(gameID);
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
    //call the setter to change the value
    chessGame.setGameOver();
    GameData gameData2 = gameDAO.getGame(resign.getGameID());
    //update it in the database
    gameDAO.updateGame(gameData2);

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
}
