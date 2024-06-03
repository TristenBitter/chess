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
  public void insertUserData(UserData userInfo) throws DataAccessException{
    try (var conn = DatabaseManager.getConnection()) {
      String dataToInsert = "INSERT INTO userDataTable (username, password, email) VALUES (?, ?, ?);";
      var preparedStatement = conn.prepareStatement(dataToInsert);
      preparedStatement.setString(1, userInfo.username());
      // get the hashed password
      String hashedPassword = passwordHasher(userInfo.password());
      preparedStatement.setString(2, hashedPassword);
      preparedStatement.setString(3, userInfo.email());
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }


  }

  public String passwordHasher(String password) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    return hashedPassword;
  }


  public String readHashedPasswordFromDatabase(String username) throws DataAccessException{
    // get the hashed password with the username

    String password= null;
    String query = "SELECT password FROM userDataTable WHERE username = ?;";

    try (var conn = DatabaseManager.getConnection()){
         var preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);

        var result = preparedStatement.executeQuery();
        if(result.next()){
          password = result.getString("password");
        }
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    return password;
  }

  public boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException {
    // read the previously hashed password from the database
    String hashedPassword = readHashedPasswordFromDatabase(username);

    return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
  }

  @Override
  public ArrayList<String> getUsername() throws DataAccessException{

    ArrayList<String> listOfUsernames = new ArrayList<>();
    String query = "SELECT username FROM userDataTable";

    try (var conn = DatabaseManager.getConnection()) {
      var preparedStatement=conn.prepareStatement(query);
      var result=preparedStatement.executeQuery();
      while(result.next()){
        listOfUsernames.add(result.getString("username"));
      }
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    return listOfUsernames;
  }

  @Override
  public ArrayList<String> getPassword(){
    return null;
  }

}
