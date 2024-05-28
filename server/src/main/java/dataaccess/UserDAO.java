package dataaccess;

import model.UserData;

import java.util.ArrayList;

public interface UserDAO {
  public void clear();
  public void insertUserData(UserData userInfo);
  public ArrayList<String> getUsername();

}
