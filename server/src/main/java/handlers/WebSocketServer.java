package handlers;

import dataaccess.UnauthorizedException;
import model.ErrorMessage;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.UserGameCommand;

import static spark.Spark.connect;

@WebSocket
public class WebSocketServer{


  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
    System.out.printf("Received: %s", message);
    session.getRemote().sendString("WebSocket response: " + message);
  }

//  @OnWebSocketMessage
//  public void onMessage(Session session, String msg) {
//    try {
//      UserGameCommand command = Serializer.fromJson(message, UserGameCommand.class);
//
//      // Throws a custom UnauthorizedException. Yours may work differently.
//      String username = getUsername(command.getAuthString());
//
//      saveSession(command.getGameID(), session);
//
//      switch (command.getCommandType()) {
//        case CONNECT -> connect(session, username, (ConnectCommand) command);
//        case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
//        case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
//        case RESIGN -> resign(session, username, (ResignCommand) command);
//      }
//    } catch (UnauthorizedException ex) {
//      // Serializes and sends the error message
//      sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
//    } catch (Exception ex) {
//      ex.printStackTrace();
//      sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
//    }
//  }

  private void sendMessage(RemoteEndpoint remote, ErrorMessage errorMessage) {
  }

//  private void makeMove(Session session, String username, MakeMoveCommand command) {
//  }

}
