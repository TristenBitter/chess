package service;

import com.google.gson.Gson;
import model.AuthData;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RegisterTest {
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  private final RegisterRequest newUserData = new RegisterRequest("coolDude", "dude123", "cooldude@gmail.com");
  @Test
  public void registerSuccess(){

    RegisterService req = new RegisterService(userData);
    AuthData result = req.registerUser(userData);

    assertNotEquals(null,new Gson().toJson(result));

  }

  @Test
  public void registerErrorCheck(){
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    //Username already exists
    RegisterService req1 = new RegisterService(userData);
    RegisterService req2 = new RegisterService(newUserData);

    assertNotEquals(req1.registerUser(userData), req2.registerUser(newUserData));
  }
}
