package service;

import Service.LoginService;
import Service.RegisterService;
import com.google.gson.Gson;
import model.AuthData;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTestLogin {

  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final LoginRequest wrongUserCredentials = new LoginRequest("kyle", "hello");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");

  @Test
  public void LoginSuccess(){
    RegisterService req = new RegisterService(userData);
    req.registerUser(userData);
    LoginService login = new LoginService(loginCredentials);
    AuthData result = login.loginUser(loginCredentials);
    assertNotEquals(null, new Gson().toJson(result));

  }

  @Test
  public void LoginFailure(){
   LoginService login = new LoginService(wrongUserCredentials);

    assertEquals(null, login.loginUser(wrongUserCredentials));
  }

}
