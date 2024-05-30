package Service;

import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;

public class ClearService {
  public ClearService(){
    ClearDB();
  }
  private static MemoryUserDAO userData = new MemoryUserDAO();
  private static MemoryAuthDAO authData = new MemoryAuthDAO();
  private static MemoryGameDAO gameData = new MemoryGameDAO();
  public void ClearDB(){
    // make 3 objects
    MemoryUserDAO UserObject = userData;
    MemoryAuthDAO AuthObject = authData;
    MemoryGameDAO GameObject = gameData;

    UserObject.clear();
    AuthObject.clear();
    GameObject.clear();
  }


}
