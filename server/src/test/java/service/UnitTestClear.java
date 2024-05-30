package service;

import Service.ClearService;
import chess.ChessGame;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestClear {
  private final MemoryUserDAO UserDAO = new MemoryUserDAO();
  private final MemoryAuthDAO AuthDAO = new MemoryAuthDAO();
  private final MemoryGameDAO GameDAO = new MemoryGameDAO();

  private final chess.ChessGame game = new ChessGame();

  private GameData gameInfo = new GameData(1, "white", "black", "game1",game);

  @Test
  public void didItClearAllTheData(){
    UserDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    AuthDAO.makeAuthToken("tee");
    GameDAO.createGame(gameInfo);
    new ClearService();
    assertEquals(0, UserDAO.getAll().size());
    assertEquals(0, AuthDAO.getAll().size());
    assertEquals(0, GameDAO.getAll().size());
  }


}
