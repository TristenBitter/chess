package Service;

import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.RegisterRequest;
import model.UserData;

import java.util.ArrayList;

public class RegisterService {
  private static MemoryAuthDAO authObject = new MemoryAuthDAO();
  private static MemoryUserDAO userObject = new MemoryUserDAO();
  public RegisterService(RegisterRequest userData){
//   AuthData result = registerUser(userData);

  }

  public AuthData registerUser(RegisterRequest userData){
    // getUser to check if the username is taken
    if(!hasUsernameBeenTaken(userData)){
      return null;
    }
    // add the user to our DB
    UserData newUserData = new UserData(userData.username(), userData.password(), userData.email());
    userObject.insertUserData(newUserData);

    //CreateAuthToken to add the username and pair it with the AuthToken
   // AuthData newUserToken = new AuthData(authObject.makeAuthToken(userData.username()).authToken(), userData.username() );

    AuthData newUserToken = authObject.makeAuthToken(userData.username());
    return newUserToken;
  }

  public boolean hasUsernameBeenTaken(RegisterRequest userData){
    ArrayList<String> newUser = new ArrayList<>();

    //newUser.addAll(userObject.getAll());
    newUser = userObject.getUsername();
    if(newUser.contains(userData.username())){
      return false;
    }
    return true;
  }
}
