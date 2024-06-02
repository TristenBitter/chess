package service;

import dataaccess.AuthDAO;
import dataaccess.UnauthorizedException;
import dataaccess.memory.MemoryAuthDAO;
import model.LogoutRequest;

public class LogoutService {
  private static AuthDAO authDAO = new MemoryAuthDAO();
  public LogoutService(LogoutRequest authToken){

  }
  public boolean logoutUser(LogoutRequest authToken) throws UnauthorizedException {
    //ValidateAuthToken()
    if(!validateAuthToken(authToken)) return false;

    //Delete the Token from the MemoryAuthDAO DB
    authDAO.deleteAuthData(authToken);

    return true;
  }

  public boolean validateAuthToken(LogoutRequest authData){
    String authToken =authData.authToken();

    for (String token: authDAO.getAuthTokens()
         ) { if(token.equals(authToken)) return true;

    }
    return false;
  }

}
