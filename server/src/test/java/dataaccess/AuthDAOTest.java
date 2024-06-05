package dataaccess;

import chess.ChessGame;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import model.AuthData;
import model.GameData;
import model.LogoutRequest;
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
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();

    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);

    assertTrue(authDAO.validateAuthToken(data.authToken()));
  }

  @Test
  public void authFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    userDAO.insertUserData(new UserData("tee", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("tee");
    gameDAO.createGame(gameInfo);

    assertFalse(authDAO.validateAuthToken("john"));
  }

  @Test
  public void makeAuthTokenSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    userDAO.insertUserData(new UserData("see", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("see");
    assertNotNull(data);
  }

  @Test
  public void makeAuthTokenFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    userDAO.insertUserData(new UserData("sea", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("pee");
    assertNotNull(data);
  }

  @Test
  public void clearSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    assertFalse(authDAO.validateAuthToken("tee"));
  }

  @Test
  public void clearFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    userDAO.insertUserData(new UserData("sea", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("pee");
    assertTrue(authDAO.validateAuthToken(data.authToken()));
  }

  @Test
  public void getUsernameSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    AuthData data = authDAO.makeAuthToken("pee");
    assertEquals("pee", authDAO.getUsername(data.authToken()));

  }

  @Test
  public void getUsernameFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    assertNull(authDAO.getUsername("hello"));
  }

  @Test
  public void deleteUsernameSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    AuthData data = authDAO.makeAuthToken("pee");
    LogoutRequest logout = new LogoutRequest(data.authToken());
    authDAO.deleteAuthData(logout);
    assertNotEquals("pee", authDAO.getUsername(data.authToken()));

  }

  @Test
  public void deleteUsernameFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    AuthData data = authDAO.makeAuthToken("pee");
    LogoutRequest logout = new LogoutRequest("the world is your urinal");
    authDAO.deleteAuthData(logout);
    assertEquals("pee", authDAO.getUsername(data.authToken()));

  }

}
