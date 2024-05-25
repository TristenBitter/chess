package dataaccess.memory;

import dataaccess.AuthDAO;
import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
  private ArrayList<AuthData> authData;
  private long Token = Tokenizer();

  public MemoryAuthDAO(){this.authData = new ArrayList<AuthData>();}

  public ArrayList<AuthData> getAll(){return authData;}
  public AuthData makeAuthToken(String username){
    AuthData authToken = new AuthData(toString(),username);

    this.authData.add(authToken);

    return authToken;
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
