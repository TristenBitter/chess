package dataaccess.memory;

import dataaccess.GameDAO;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {

  private ArrayList<GameData> gameData;
  public MemoryGameDAO(){this.gameData = new ArrayList<GameData>();}

  public ArrayList<GameData> getAll(){return gameData;}

  public void createGame(GameData gameInfo){
    this.gameData.add(gameInfo);
  }
  @Override
  public void clear() {
    gameData.clear();
  }
}
