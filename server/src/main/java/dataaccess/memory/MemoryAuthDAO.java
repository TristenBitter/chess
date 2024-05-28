package dataaccess.memory;

import dataaccess.AuthDAO;
import model.AuthData;
import model.LogoutRequest;
import model.UserData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
  private static ArrayList<AuthData> authData;
  private long Token = Tokenizer();

  public MemoryAuthDAO(){this.authData = new ArrayList<AuthData>();}

  public ArrayList<AuthData> getAll(){return authData;}
  public AuthData makeAuthToken(String username){
    AuthData authToken = new AuthData(toString(),username);

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

  public void deleteAuthData(LogoutRequest authToken){
    String token = authToken.authToken();
    for (AuthData data: authData

    ) { if(token.equals(data.authToken())){
          authData.remove(data);
          break;
        }
    }
  }

  @Override
  public String toString() {
    return "{" + "authToken=" + authData + Token + '}';
  }

  public long Tokenizer(){
    long Token = System.currentTimeMillis() % 1000;
    return Token;
  }
  @Override
  public void clear() {
    authData.clear();
  }
}
