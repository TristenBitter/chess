package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import model.LogoutRequest;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlAuthDAO implements AuthDAO {

  private static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  authDataTable (
              `id` int NOT NULL AUTO_INCREMENT,
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(authToken),
              INDEX(username)
            );
            """
  };

  public static void createAuthDBTable()throws DataAccessException{
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
      String clear = "DELETE FROM authDataTable";
      var preparedStatement = conn.prepareStatement(clear);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
  @Override
  public AuthData makeAuthToken(String username){

    return null;
  }
  @Override
  public ArrayList<String> getAuthTokens(){
    return null;
  }
  @Override
  public boolean validateAuthToken(String authToken){
    return false;
  }
  @Override
  public String getUsername(String authToken){
    return null;
  }
  @Override
  public void deleteAuthData(LogoutRequest authToken){

  }
  @Override
  public String tokenizer(){
    return null;
  }

}
