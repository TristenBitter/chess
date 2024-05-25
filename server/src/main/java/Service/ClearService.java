package Service;

import dataaccess.memory.MemmoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;

public class ClearService {
  public ClearService(MemmoryUserDAO userData, MemoryAuthDAO authData, MemoryGameDAO gameData){
    ClearDB(userData, authData, gameData);
  }

  public void ClearDB(MemmoryUserDAO userData, MemoryAuthDAO authData, MemoryGameDAO gameData){
    // make 3 objects
    MemmoryUserDAO UserObject = userData;
    MemoryAuthDAO AuthObject = authData;
    MemoryGameDAO GameObject = gameData;

    UserObject.clear();
    AuthObject.clear();
    GameObject.clear();
  }


}
