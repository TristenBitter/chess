package passoff.server;

import Service.LoginService;
import Service.RegisterService;
import dataaccess.memory.MemoryUserDAO;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTestLogin {

  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final LoginRequest wrongUserCredentials = new LoginRequest("kyle", "hello");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  private final MemoryUserDAO UserDAO = new MemoryUserDAO();

  @Test
  public void LoginSuccess(){
    RegisterService req = new RegisterService(userData);
    LoginService login = new LoginService(loginCredentials);
    assertNotEquals(null, login);
  }

  @Test
  public void LoginFailure(){
   LoginService login = new LoginService(wrongUserCredentials);

    assertEquals(null, login.loginUser(wrongUserCredentials));
  }

}
