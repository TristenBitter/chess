package Service;

import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.LoginRequest;

public class LoginService {
  private static MemoryAuthDAO authObject = new MemoryAuthDAO();
  private static MemoryUserDAO userObject = new MemoryUserDAO();

  public LoginService(LoginRequest loginCredentials){
    loginUser(loginCredentials);
  }
  public AuthData loginUser(LoginRequest loginData){
    //AuthenticateUser
    if(!authenticateUser(loginData)){
      return null;
    }

    //Make AuthToken
    AuthData authData = new AuthData(authObject.makeAuthToken(loginData.username()).authToken(), loginData.username());

    return authData;
  }

  public boolean authenticateUser(LoginRequest loginCredentials){
    //check Username and Password
    String username = loginCredentials.username();
    String password = loginCredentials.password();

    boolean usernameMatch = false;
    boolean passwordMatch = false;

    int StringNumUser = 0;
    for (String name: userObject.getUsername()
     ) { if(name.equals(username)){
            usernameMatch = true;
            break;
            }
       StringNumUser++;
    }
    int StringNumPassword = 0;
    for (String word: userObject.getPassword()
    ) { if(word.equals(password)){
            passwordMatch = true;
            break;
            }
        StringNumPassword++;
    }

    if((StringNumPassword == StringNumUser) && passwordMatch && usernameMatch){
      return true;
    }

    return false;
  }
}
