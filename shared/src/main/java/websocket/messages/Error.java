package websocket.messages;

public class Error extends ServerMessage{
  private String errorMessage;
  public Error( String errorMessage) {
    super(ServerMessageType.ERROR);
    this.errorMessage=errorMessage;
    this.serverMessageType=ServerMessageType.ERROR;
  }
  public String getErrorMessage() {
    return errorMessage;
  }
}
