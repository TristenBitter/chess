package ui;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import websocket.commands.Connect;
import websocket.commands.UserGameCommand;
import websocket.messages.Error;
import websocket.messages.LoadGame;
import websocket.messages.Notifications;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

public class WebSocketClient extends Endpoint {

  public Session session;

  public void webSocketClient() throws Exception {
    URI uri = new URI("ws://localhost:8080/ws");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String message) {

        ServerMessage command=new Gson().fromJson(message, ServerMessage.class);


        //saveSession(command.getGameID(), session);

        switch (command.getServerMessageType()) {
          case ERROR -> errorMsg(session, message);
          case LOAD_GAME -> loadGame(session, message);
          case NOTIFICATION -> notification(session, message);

        }
        String msg = new Gson().fromJson(message, String.class);
        System.out.println(msg);

      }
    });

  }

  public void send(String msg) throws Exception {
    this.session.getBasicRemote().sendText(msg);
  }

  public void onOpen(Session session, EndpointConfig endpointConfig){}

  public void errorMsg(Session session, String message){
    // send back the error
    Error error = new Gson().fromJson(message, Error.class);

      String msg = error.getErrorMessage();
      System.out.println(msg);
  }

  public void loadGame(Session session, String message){
    //send back the message
    LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);

    ChessGame game = loadGame.getGame();
    ChessBoard board = game.getBoard();
    ChessBoardDrawer drawer = new ChessBoardDrawer();
    String turn = game.getTeamTurn().toString();

    if(turn.equals("WHITE")){
      drawer.printWhiteBoard(board);
    }
    else if(turn.equals("BLACK")){
      drawer.printBlackBoard(board);
    }
    else{
      drawer.printWhiteBoard(board);
      drawer.printBlackBoard(board);
    }
  }

  public void notification(Session session, String message){
    // send back the notification
    Notifications note = new Gson().fromJson(message, Notifications.class);

      String msg = note.getMessage();
      System.out.println(msg);
  }

}


