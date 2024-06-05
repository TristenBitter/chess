package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.UserDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlUserDAO;
import model.AuthData;
import model.LoginRequest;

public class LoginService {
  private static AuthDAO authObject = new MySqlAuthDAO();
  private static UserDAO userObject = new MySqlUserDAO();

  public LoginService(LoginRequest loginCredentials){
    //loginUser(loginCredentials);
  }
  public AuthData loginUser(LoginRequest loginData) throws UnauthorizedException, DataAccessException {
    //AuthenticateUser
    if(!authenticateUser(loginData)){
      return null;
    }

    //Make AuthToken
    AuthData authData = new AuthData(authObject.makeAuthToken(loginData.username()).authToken(), loginData.username());

    return authData;
  }

  public boolean authenticateUser(LoginRequest loginCredentials) throws DataAccessException {
    //check Username and Password
    MySqlUserDAO userDAO = new MySqlUserDAO();
    if(userDAO.verifyUser(loginCredentials.username(), loginCredentials.password())) return true;

    return false;
  }
}
