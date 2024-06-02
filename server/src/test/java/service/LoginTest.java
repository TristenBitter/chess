package service;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import model.AuthData;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LoginTest {

  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final LoginRequest wrongUserCredentials = new LoginRequest("kyle", "hello");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");

  @Test
  public void loginSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
    RegisterService req = new RegisterService(userData);
    req.registerUser(userData);
    LoginService login = new LoginService(loginCredentials);
    AuthData result = login.loginUser(loginCredentials);
    assertNotEquals(null, new Gson().toJson(result));

  }

  @Test
  public void loginFailure() throws UnauthorizedException, DataAccessException {
   LoginService login = new LoginService(wrongUserCredentials);

    assertEquals(null, login.loginUser(wrongUserCredentials));
  }

}
