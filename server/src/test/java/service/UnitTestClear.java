package service;

import chess.ChessGame;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestClear {
  private final MemoryUserDAO userDAO= new MemoryUserDAO();
  private final MemoryAuthDAO authDAO= new MemoryAuthDAO();
  private final MemoryGameDAO gameDAO= new MemoryGameDAO();

  private final chess.ChessGame game = new ChessGame();

  private GameData gameInfo = new GameData(1, "white", "black", "game1",game);

  @Test
  public void didItClearAllTheData(){
    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);
    new ClearService();
    assertEquals(0, userDAO.getAll().size());
    assertEquals(0, authDAO.getAll().size());
    assertEquals(0, gameDAO.getAll().size());
  }


}
