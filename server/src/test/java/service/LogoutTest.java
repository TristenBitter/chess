package service;

import model.AuthData;
import model.LogoutRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LogoutTest {
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  //private final RegisterRequest wrongUserCredentials = new RegisterRequest("kyle", "hello", "kyle@gmail.com");
  private final AuthData wrongUserCredentials = new AuthData("kyle", "hello");
  @Test
  public void logoutSuccess(){
    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);
    assertNotNull(authInfo);
    LogoutRequest logoutAuthTok = new LogoutRequest(authInfo.authToken());
    LogoutService logout = new LogoutService(logoutAuthTok);
    boolean result = logout.logoutUser(logoutAuthTok);
    assertTrue(result);

  }

  @Test
  public void logoutFailure(){
    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);
    LogoutRequest logoutAuthTok = new LogoutRequest(wrongUserCredentials.authToken());
    LogoutService logout = new LogoutService(logoutAuthTok);
    boolean result = logout.logoutUser(logoutAuthTok);
    assertEquals(false, result);
  }
}
