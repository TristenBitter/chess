package websocket.messages;

public class Error extends ServerMessage{
  private String errorMessage;
  public Error(ServerMessageType type, String errorMessage) {
    super(type);
    this.errorMessage=errorMessage;
    this.serverMessageType=ServerMessageType.ERROR;
  }
  public String getErrorMessage() {
    return errorMessage;
  }
}