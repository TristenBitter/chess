package service;

import dataaccess.DataAccessException;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.UserDAO;

public class ClearService {
  public ClearService() throws DataAccessException {
    clearDB();
  }
  private static UserDAO userData = new MemoryUserDAO();  //new mySqlUserDAO
  private static MemoryAuthDAO authData = new MemoryAuthDAO();
  private static MemoryGameDAO gameData = new MemoryGameDAO();
  public void clearDB() throws DataAccessException {
    // make 3 objects
    UserDAO userObject = userData;
    MemoryAuthDAO authObject = authData;
    MemoryGameDAO gameObject = gameData;

    try {
      userObject.clear();
      authObject.clear();
      gameObject.clear();
    }catch(DataAccessException e){
        throw new DataAccessException(e.getMessage());
    }
  }


}
