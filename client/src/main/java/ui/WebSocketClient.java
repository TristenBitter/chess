package ui;

import com.google.gson.Gson;

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
        String msg = new Gson().fromJson(message, String.class);
        System.out.println(msg);
      }
    });
  }

  public void send(String msg) throws Exception {
    this.session.getBasicRemote().sendText(msg);
  }

  public void onOpen(Session session, EndpointConfig endpointConfig){}

}


