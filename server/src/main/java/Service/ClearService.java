package Service;

import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;

public class ClearService {
  public ClearService(MemoryUserDAO userData, MemoryAuthDAO authData, MemoryGameDAO gameData){
    ClearDB(userData, authData, gameData);
  }

  public void ClearDB(MemoryUserDAO userData, MemoryAuthDAO authData, MemoryGameDAO gameData){
    // make 3 objects
    MemoryUserDAO UserObject = userData;
    MemoryAuthDAO AuthObject = authData;
    MemoryGameDAO GameObject = gameData;

    UserObject.clear();
    AuthObject.clear();
    GameObject.clear();
  }


}
