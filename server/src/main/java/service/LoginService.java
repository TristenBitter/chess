package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.UserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.LoginRequest;

public class LoginService {
  private static AuthDAO authObject = new MemoryAuthDAO();
  private static UserDAO userObject = new MemoryUserDAO();

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
    String username = loginCredentials.username();
    String password = loginCredentials.password();

    boolean usernameMatch = false;
    boolean passwordMatch = false;

    int stringNumUser = 0;
    for (String name: userObject.getUsername()
     ) { if(name.equals(username)){
            usernameMatch = true;
            break;
            }
       stringNumUser++;
    }
    int stringNumPassword = 0;
    for (String word: userObject.getPassword()
    ) { if(word.equals(password)){
            passwordMatch = true;
            break;
            }
        stringNumPassword++;
    }

    if((stringNumPassword == stringNumUser) && passwordMatch && usernameMatch){
      return true;
    }

    return false;
  }
}
