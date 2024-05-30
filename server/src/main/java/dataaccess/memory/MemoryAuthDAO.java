package dataaccess.memory;

import dataaccess.AuthDAO;
import model.AuthData;
import model.LogoutRequest;
import model.UserData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
  private static final ArrayList<AuthData> authData = new ArrayList<>();

  public MemoryAuthDAO(){}

  public ArrayList<AuthData> getAll(){return authData;}
  public AuthData makeAuthToken(String username){
    AuthData authToken = new AuthData(tokenizer(),username);

    this.authData.add(authToken);

    return authToken;
  }

  public ArrayList<String> getAuthTokens(){
    ArrayList<String> listOfTokens = new ArrayList<>();
    for (AuthData data: authData

    ) { listOfTokens.add(data.authToken());
    }
    return listOfTokens;
  }

  public boolean validateAuthToken(String authToken){
    ArrayList<String> listOfTokens = getAuthTokens();
    for (String token: listOfTokens
         ) { if(authToken.equals(token)){                      // Let's see if this can compare two strings
                return true;
    }
    }
    return false;
  }

  public LogoutRequest getAuthTok(String username){
    for (AuthData data: authData
         ) { if(username.equals(data.username())){
              LogoutRequest token = new LogoutRequest(data.authToken());
              return token;
    }
    }
    return null;
  }

  public String getUsername(String authToken){
    for (AuthData data: authData
         ) {if(authToken.equals(data.authToken())){
                String username = data.username();
                return username;
    }
    }

    return null;
  }

  public void deleteAuthData(LogoutRequest authToken){
    String token = authToken.authToken();
    for (AuthData data: authData

    ) { if(token.equals(data.authToken())){
          authData.remove(data);
          break;
        }
    }
  }

  public String tokenizer(){
//    long Token = System.currentTimeMillis() % 1000;
    String token =UUID.randomUUID().toString();
    return token;
  }
  @Override
  public void clear() {
    authData.clear();
  }
}
