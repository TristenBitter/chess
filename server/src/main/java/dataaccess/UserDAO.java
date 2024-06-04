package dataaccess;

import model.UserData;

import java.util.ArrayList;

public interface UserDAO {
  public void clear() throws DataAccessException;
  public void insertUserData(UserData userInfo) throws DataAccessException;
  public ArrayList<String> getUsername()throws DataAccessException;

  ArrayList<String> getPassword()throws DataAccessException;

  abstract ArrayList<UserData> getAll();


//  static ArrayList<UserData> getAll();
}
