package websocket.messages;

public class LoadGame extends ServerMessage{
  private int game;
  public LoadGame(ServerMessageType type, int game) {
    super(type);
    this.game=game;
    this.serverMessageType= ServerMessageType.LOAD_GAME;
  }
}
