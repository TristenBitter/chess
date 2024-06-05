package dataaccess;

import chess.ChessGame;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;
public class AuthDAOTest {
  private final MySqlUserDAO userDAO= new MySqlUserDAO();
  private final MySqlAuthDAO authDAO= new MySqlAuthDAO();
  private final MySqlGameDAO gameDAO= new MySqlGameDAO();

  private final chess.ChessGame game = new ChessGame();

  private GameData gameInfo = new GameData(1, "white", "black", "game1",game);

  @Test
  public void authSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();

    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);

    assertTrue(authDAO.validateAuthToken(data.authToken()));
  }

  @Test
  public void authFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);

    assertFalse(authDAO.validateAuthToken("john"));
  }
}
