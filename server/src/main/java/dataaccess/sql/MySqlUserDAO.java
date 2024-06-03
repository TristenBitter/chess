package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlUserDAO implements UserDAO {

  private static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  userDataTable (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(password),
              INDEX(username),
              INDEX(email)
            );
            """
  };

  public void createUserDBTable() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = conn.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
}

  @Override
  public void clear() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      String clear = "DELETE FROM userDataTable";
      var preparedStatement = conn.prepareStatement(clear);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }

  @Override
  public void insertUserData(UserData userInfo) {
    //try (var conn = DatabaseManager.getConnection()) {
    String dataToInsert = "INSERT INTO userDataTable (username, password, email) VALUES (?, ?, ?);";

  }

  public void storeUserPassword(String username, String password) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    // write the hashed password in database along with the user's other information
    writeHashedPasswordToDatabase(username, hashedPassword);
  }

  public void writeHashedPasswordToDatabase(String username,String hashedPassword){

  }

  public String readHashedPasswordFromDatabase(String username){
    // get the hashed password with the username

    String password= "undo BCrypt some how for the hashed password";

    return password;
  }

  public boolean verifyUser(String username, String providedClearTextPassword) {
    // read the previously hashed password from the database
    var hashedPassword = readHashedPasswordFromDatabase(username);

    return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
  }

  @Override
  public ArrayList<String> getUsername() {
    return null;
  }

  @Override
  public ArrayList<String> getPassword(){
    return null;
  }

//  @Override
//  public static ArrayList<UserData> getAll(){
//    return null;
//  }

}
