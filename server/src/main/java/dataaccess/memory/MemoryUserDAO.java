package dataaccess.memory;

import dataaccess.UserDAO;
import model.UserData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
  private static ArrayList<UserData> userData;
  public MemoryUserDAO(){
    this.userData = new ArrayList<UserData>();
  }

  public void insertUserData(UserData userInfo){
    this.userData.add(userInfo);
  }

  public static ArrayList<UserData> getAll(){
    return userData;
  }
  public ArrayList<String> getUsername(){
    ArrayList<String> listOfNames = new ArrayList<>();
    for (UserData data: userData
         ) { listOfNames.add(data.username());
    }
    return listOfNames;
  }

  public ArrayList<String> getPassword(){
    ArrayList<String> listOfPasswords = new ArrayList<>();
    for (UserData data: userData
    ) { listOfPasswords.add(data.password());
    }
    return listOfPasswords;
  }
  @Override
  public void clear() {
    userData.clear();
  }
}
