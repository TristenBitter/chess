package service;

import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;

public class ClearService {
  public ClearService(){
    clearDB();
  }
  private static MemoryUserDAO userData = new MemoryUserDAO();
  private static MemoryAuthDAO authData = new MemoryAuthDAO();
  private static MemoryGameDAO gameData = new MemoryGameDAO();
  public void clearDB(){
    // make 3 objects
    MemoryUserDAO userObject = userData;
    MemoryAuthDAO authObject = authData;
    MemoryGameDAO gameObject = gameData;

    userObject.clear();
    authObject.clear();
    gameObject.clear();
  }


}
