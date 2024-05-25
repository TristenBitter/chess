package dataaccess.memory;

import dataaccess.UserDAO;
import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
  private ArrayList<UserData> userData;
  public MemoryUserDAO(){
    this.userData = new ArrayList<UserData>();
  }

  public void insertUserData(UserData userInfo){
    this.userData.add(userInfo);
  }

  public ArrayList<UserData> getAll(){
    return userData;
  }
  @Override
  public void clear() {
    userData.clear();
  }
}
