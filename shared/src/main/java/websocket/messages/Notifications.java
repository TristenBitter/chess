package websocket.messages;

public class Notifications extends ServerMessage{
  private String message;
  public Notifications(ServerMessageType type, String message) {
    super(type);
    this.message=message;
    this.serverMessageType=ServerMessageType.NOTIFICATION;
  }
}
