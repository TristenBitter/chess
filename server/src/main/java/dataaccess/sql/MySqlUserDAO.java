package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;

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
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(password),
              INDEX(username),
              INDEX(email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
  };

  public static void createUserDBTable() throws DataAccessException {
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
  public void clear() {

  }

  @Override
  public void insertUserData(UserData userInfo) {

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
