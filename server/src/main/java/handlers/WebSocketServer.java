package handlers;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.UnauthorizedException;
import dataaccess.memory.MemoryAuthDAO;
import model.ErrorMessage;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
//        case RESIGN -> resign(session, username, (ResignCommand) command);
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      //sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
    }
  }

  private void sendMessage(RemoteEndpoint remote, String arg) throws IOException {
    remote.sendString(arg);
  }

  private void connect(Session session, String message, String username){
    Connect connect = new Gson().fromJson(message, Connect.class);
    sessionData.get(connect.getGameID()).put(username, session);
    //sessionData.put(connect.getGameID(), session);
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

}
