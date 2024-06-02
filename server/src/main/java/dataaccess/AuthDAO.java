package dataaccess;

import model.AuthData;
import model.LogoutRequest;

import java.util.ArrayList;

public interface AuthDAO {
  // plan for what the classes will do
  public void clear();

  public String tokenizer();

  public AuthData makeAuthToken(String username);

  public ArrayList<String> getAuthTokens();

  public boolean validateAuthToken(String authToken);

  public String getUsername(String authToken);

  public void deleteAuthData(LogoutRequest authToken);


}
