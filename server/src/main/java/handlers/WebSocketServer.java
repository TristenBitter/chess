package handlers;

import chess.ChessGame;
import chess.ChessMove;
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

import javax.management.Notification;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static spark.Spark.connect;

@WebSocket
public class WebSocketServer{


  private Map<Integer, Set<Session>> sessionData = new HashMap<>();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) {
    try {
      UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

      MemoryAuthDAO authDAO = new MemoryAuthDAO();
      Connect connect = new Gson().fromJson(message, Connect.class);

      sessionData.computeIfAbsent(connect.getGameID(), v->new HashSet<>());
      sessionData.get(connect.getGameID()).add(session);

      // Throws a custom UnauthorizedException. Yours may work differently.

      String username =authDAO.getUsername(command.getAuthString());

      //saveSession(command.getGameID(), session);

      switch (command.getCommandType()) {
        case CONNECT -> connect(session, message, username);
        case MAKE_MOVE -> makeMove(session, username, message);
        case LEAVE -> leaveGame(session, username, message);
        case RESIGN -> resign(session, username, message);
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      //sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
    }
  }

  private void sendMessage(RemoteEndpoint remote, String arg) throws IOException {
    remote.sendString(arg);
  }

  private void connect(Session session, String message, String username) throws IOException, DataAccessException {
    Connect connect = new Gson().fromJson(message, Connect.class);
    MySqlGameDAO gameDAO = new MySqlGameDAO();
    GameData gameData = gameDAO.getGame(connect.getGameID());

    //notify everyone but the current user that the current user connected (as observer or player) for this game
    for(Session s : sessionData.get(connect.getGameID())) {
      if(!(s.equals(session))){
        String player = null;

        String BlackUsername = gameData.blackUsername();
        String WhiteUsername = gameData.whiteUsername();

        if(BlackUsername.equals(username)){
          player = "BLACK Player";
        }
        if(WhiteUsername.equals(username)){
          player = "WHITE Player";
        }
        else{
          player = "Observer";
        }
        Notifications notifications = new Notifications("Player " + username + " has joined the game as the " + player);
        s.getRemote().sendString(new Gson().toJson(notifications));
        //s.getRemote().sendString("Player " + username + " has joined the game as the " + player );

      }

    }
    //sessionData.get(connect.getGameID()).get(session);

    //session.getRemote().sendString("you've joined the game, congrats!");

    //send a LOAD_GAME message back to the client

    LoadGame loadGame = new LoadGame(gameData.game());

    session.getRemote().sendString(new Gson().toJson(loadGame));

    //session.getRemote().send

    //


  }


  private void makeMove(Session session, String username, String message) {
    MakeMove makeMove = new Gson().fromJson(message, MakeMove.class);
    ChessMove move = makeMove.getMove();
    // do stuff now



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
    JoinGameRequest requestedGame = new JoinGameRequest(color, gameID);
    gameDAO.joinGame(requestedGame, "null");

    for(Session s : sessionData.get(gameID)) {
      if (!(s.equals(session))) {
        Notifications notifications=new Notifications(" " + username + " has just left the game");
        s.getRemote().sendString(new Gson().toJson(notifications));
      }
    }
  }

  private void resign(Session session, String username, String message) throws DataAccessException, IOException {
    Resign resign = new Gson().fromJson(message, Resign.class);
    MySqlGameDAO gameDAO = new MySqlGameDAO();
    GameData gameData = gameDAO.getGame(resign.getGameID());



    // change the chess game
    ChessGame chessGame = gameData.game();
 cx
    //call the setter to change the value


    //update it in the database




    int gameID = resign.getGameID();
    for(Session s : sessionData.get(gameID)) {
      if (!(s.equals(session))) {
        String player = gameData.blackUsername();
        String BlackUsername = gameData.blackUsername();

        if(BlackUsername.equals(username)){
          player = gameData.whiteUsername();
        }
        Notifications notifications=new Notifications(" " + username + " has just forfeited the game, " + player + " Wins the Game!!!");
        s.getRemote().sendString(new Gson().toJson(notifications));
      }
    }

  }




}
