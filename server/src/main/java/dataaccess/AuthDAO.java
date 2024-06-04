package dataaccess;

import model.AuthData;
import model.LogoutRequest;

import java.util.ArrayList;

public interface AuthDAO {
  // plan for what the classes will do
  public void clear() throws DataAccessException;

  public String tokenizer();

  public AuthData makeAuthToken(String username) throws DataAccessException;

  public ArrayList<String> getAuthTokens() throws DataAccessException;

  public boolean validateAuthToken(String authToken) throws DataAccessException;

  public String getUsername(String authToken) throws DataAccessException;

  public void deleteAuthData(LogoutRequest authToken) throws DataAccessException;


  abstract ArrayList<AuthData> getAll();
}
