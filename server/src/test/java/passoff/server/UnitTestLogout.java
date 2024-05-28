package passoff.server;

import Service.LoginService;
import Service.LogoutService;
import Service.RegisterService;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.LoginRequest;
import model.LogoutRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTestLogout {

  private final LoginRequest loginCredentials = new LoginRequest("TristenBitter", "Tee123");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  //private final RegisterRequest wrongUserCredentials = new RegisterRequest("kyle", "hello", "kyle@gmail.com");
  private final AuthData wrongUserCredentials = new AuthData("kyle", "hello");
  private MemoryAuthDAO authDAO = new MemoryAuthDAO();
  @Test
  public void LogoutSuccess(){
    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);
    //LoginService login = new LoginService(loginCredentials);
    //login.loginUser(loginCredentials);
    LogoutRequest logoutAuthTok = new LogoutRequest(authInfo.authToken());
    LogoutService logout = new LogoutService(logoutAuthTok);
    boolean result = logout.logoutUser(logoutAuthTok);
    assertEquals(true, result);
//    assertNotEquals(null, logout);
//    assertEquals(null, logout);
  }

  @Test
  public void LogoutFailure(){
    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);
    LogoutRequest logoutAuthTok = new LogoutRequest(wrongUserCredentials.authToken());
    LogoutService logout = new LogoutService(logoutAuthTok);
    boolean result = logout.logoutUser(logoutAuthTok);
    assertEquals(false, result);
    //assertEquals(null, logout);
  }
}
