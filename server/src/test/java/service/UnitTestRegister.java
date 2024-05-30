package service;

import Service.RegisterService;
import com.google.gson.Gson;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTestRegister {
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  @Test
  public void RegisterSuccess(){

    MemoryUserDAO UserDAO = new MemoryUserDAO();
    RegisterService req = new RegisterService(userData);
    AuthData result = req.registerUser(userData);

    assertNotEquals(null,new Gson().toJson(result));

  }

  @Test
  public void RegisterErrorCheck(){
    //Username already exists
    RegisterService req1 = new RegisterService(userData);
    RegisterService req2 = new RegisterService(userData);

    assertNotEquals(req1.registerUser(userData), req2.registerUser(userData));
  }
}
