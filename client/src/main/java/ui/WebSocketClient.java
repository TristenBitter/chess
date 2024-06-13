package ui;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebSocketClient extends ServerFacade{

  public WebSocketClient(int port) {
    super(port);
  }


  public Session session;

  public void WSClient() throws Exception {
    URI uri = new URI("ws://localhost:8080/ws");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    this.session = container.connectToServer(this, uri);

    this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String message) {
        System.out.println(message);
      }
    });
  }

  public void send(String msg) throws Exception {
    this.session.getBasicRemote().sendText(msg);
  }

  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}


