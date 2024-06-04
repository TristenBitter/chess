package dataaccess;

import chess.ChessGame;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearTest {
  private final MySqlUserDAO userDAO= new MySqlUserDAO();
  private final MySqlAuthDAO authDAO= new MySqlAuthDAO();
  private final MySqlGameDAO gameDAO= new MySqlGameDAO();

  private final ChessGame game = new ChessGame();

  private GameData gameInfo = new GameData(1, "white", "black", "game1",game);

  @Test
  public void didItClearAllTheData() throws Exception{
    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);
    new ClearService();
//    assertEquals(0, userDAO.getAll().size());
//    assertEquals(0, authDAO.getAll().size());
    assertEquals(0, gameDAO.getAll().size());
  }


}
