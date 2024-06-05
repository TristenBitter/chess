package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.sql.MySqlUserDAO;
import model.*;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LoginService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest {
  private final MySqlUserDAO userDAO= new MySqlUserDAO();
  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final LoginRequest wrongUserCredentials = new LoginRequest("kyle", "hello");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
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
}
