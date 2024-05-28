package Service;

import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;

public class ClearService {
  public ClearService(){
    ClearDB();
  }

  public void ClearDB(){
    MemoryUserDAO userData = new MemoryUserDAO();
    MemoryAuthDAO authData = new MemoryAuthDAO();
    MemoryGameDAO gameData = new MemoryGameDAO();
    // make 3 objects
    MemoryUserDAO UserObject = userData;
    MemoryAuthDAO AuthObject = authData;
    MemoryGameDAO GameObject = gameData;

    UserObject.clear();
    AuthObject.clear();
    GameObject.clear();
  }


}
