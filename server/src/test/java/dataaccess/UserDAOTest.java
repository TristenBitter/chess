package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlUserDAO;
import model.*;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LoginService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest {
  private final MySqlUserDAO userDAO= new MySqlUserDAO();
  private final MySqlAuthDAO authDAO = new MySqlAuthDAO();
  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final LoginRequest wrongUserCredentials = new LoginRequest("kyle", "hello");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  private final UserData gameData = new UserData("kylee", "koolAid", "littleRascal23@gmail.com");
  @Test
  public void userSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();

    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    RegisterService req = new RegisterService(userData);
    req.registerUser(userData);
    LoginService login = new LoginService(loginCredentials);
    AuthData result = login.loginUser(loginCredentials);
    assertNotEquals(null, new Gson().toJson(result));
  }

  @Test
  public void userFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    RegisterService req = new RegisterService(userData);
    req.registerUser(userData);
    LoginService login = new LoginService(wrongUserCredentials);
    AuthData result = login.loginUser(wrongUserCredentials);
    assertEquals(null, result);
  }

  @Test
  public void insertUserDataSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();

    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    userDAO.insertUserData(gameData);
    assertTrue(userDAO.verifyUser("kylee", "koolAid"));
  }

  @Test
  public void insertUserDataFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();

    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    userDAO.insertUserData(gameData);
    assertFalse(userDAO.verifyUser("dude", "hola"));
  }

  @Test
  public void clearUserSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    assertFalse(authDAO.validateAuthToken("tee"));
  }

  @Test
  public void clearUserFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    userDAO.insertUserData(new UserData("sea", "t", "gameID" ));
    AuthData data = authDAO.makeAuthToken("pee");
    assertTrue(authDAO.validateAuthToken(data.authToken()));
  }
}
