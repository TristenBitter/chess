package dataaccess.memory;

import dataaccess.UserDAO;
import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
  private static final ArrayList<UserData> USER_DATA= new ArrayList<>();
  public MemoryUserDAO(){
  }

  public void insertUserData(UserData userInfo){
    this.USER_DATA.add(userInfo);
  }

  public static ArrayList<UserData> getAll(){
    return USER_DATA;
  }
  public ArrayList<String> getUsername(){
    ArrayList<String> listOfNames = new ArrayList<>();
    for (UserData data: USER_DATA
         ) { listOfNames.add(data.username());
    }
    return listOfNames;
  }

  public ArrayList<String> getPassword(){
    ArrayList<String> listOfPasswords = new ArrayList<>();
    for (UserData data: USER_DATA
    ) { listOfPasswords.add(data.password());
    }
    return listOfPasswords;
  }
  @Override
  public void clear() {
    USER_DATA.clear();
  }
}
