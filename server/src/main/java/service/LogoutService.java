package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.sql.MySqlAuthDAO;
import model.LogoutRequest;

public class LogoutService {
  private static AuthDAO authDAO = new MySqlAuthDAO();
  public LogoutService(LogoutRequest authToken){

  }
  public boolean logoutUser(LogoutRequest authToken) throws DataAccessException {
    //ValidateAuthToken()
    if(!validateAuthToken(authToken)) return false;

    //Delete the Token from the MemoryAuthDAO DB
    authDAO.deleteAuthData(authToken);

    return true;
  }

  public boolean validateAuthToken(LogoutRequest authData) throws DataAccessException {
    String authToken =authData.authToken();
    MySqlAuthDAO mySqlAuthDAO = new MySqlAuthDAO();

    if(mySqlAuthDAO.validateAuthToken(authToken)) return true;

    return false;
  }

}
