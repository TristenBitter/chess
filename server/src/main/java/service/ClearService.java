package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.UserDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;

public class ClearService {
  public ClearService() throws DataAccessException {
    clearDB();
  }
  private static UserDAO userData = new MySqlUserDAO();  //new mySqlUserDAO
  private static AuthDAO authData = new MySqlAuthDAO();
  private static GameDAO gameData = new MySqlGameDAO();
  public void clearDB() throws DataAccessException {
    // make 3 objects
    UserDAO userObject = userData;
    AuthDAO authObject = authData;
    GameDAO gameObject = gameData;

    try {
      userObject.clear();
      authObject.clear();
      gameObject.clear();
    }catch(DataAccessException e){
        throw new DataAccessException(e.getMessage());
    }
  }


}
