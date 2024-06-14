package websocket.messages;

public class Notifications extends ServerMessage{
  private String message;
  public Notifications( String message) {
    super(ServerMessageType.NOTIFICATION);
    this.message=message;
    this.serverMessageType=ServerMessageType.NOTIFICATION;
  }
  public String getMessage() {
    return message;
  }
}
