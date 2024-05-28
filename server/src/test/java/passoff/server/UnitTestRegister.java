package passoff.server;

import Service.RegisterService;
import dataaccess.memory.MemoryUserDAO;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTestRegister {
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  @Test
  public void RegisterSuccess(){

    MemoryUserDAO UserDAO = new MemoryUserDAO();
    RegisterService req = new RegisterService(userData);
    //req.registerUser(userData);

    //assertEquals(1,UserDAO.getAll().size());
    assertEquals(userData.username().length(), req.registerUser(userData).username().length());
  }

  @Test
  public void RegisterErrorCheck(){
    //Username already exists
    RegisterService req1 = new RegisterService(userData);
    RegisterService req2 = new RegisterService(userData);

    assertNotEquals(req1.registerUser(userData), req2.registerUser(userData));
  }
}
