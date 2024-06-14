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
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.*;
import websocket.messages.LoadGame;
import websocket.messages.Notifications;

import javax.management.Notification;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static spark.Spark.connect;

@WebSocket
public class WebSocketServer{


  private Map<Integer, Map<String, Session>> sessionData = new HashMap<>();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) {
    try {
      UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

      MemoryAuthDAO authDAO = new MemoryAuthDAO();

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
    sessionData.get(connect.getGameID()).put(username, session);
    MySqlGameDAO gameDAO = new MySqlGameDAO();
    GameData gameData = gameDAO.getGame(connect.getGameID());

    //notify everyone but the current user that the current user connected (as observer or player) for this game
    for(Session sesh : sessionData.get(connect.getGameID()).values()) {
      if(!(sesh.equals(session))){
        // inform them that the current user has joined them game
        // get the color of the player somehow... if there is no color then write as an observer
        String player = null;
        //MySqlAuthDAO authDAO = new MySqlAuthDAO();
        //gets username
        //authDAO.getUsername(connect.getAuthString());


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
        sesh.getRemote().sendString(new Gson().toJson(notifications));
        //sesh.getRemote().sendString("Player " + username + " has joined the game as the " + player );

      }

    }
    sessionData.get(connect.getGameID()).get(session);

    session.getRemote().sendString("you've joined the game, congrats!");

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

  private void leaveGame(Session session, String username, String message) {
    Leave leave = new Gson().fromJson(message, Leave.class);
    int gameID = leave.getGameID();
    // do stuff

  }

  private void resign(Session session, String username, String message) {
    Resign resign = new Gson().fromJson(message, Resign.class);
    int gameID = resign.getGameID();
    // do stuff

  }




}
