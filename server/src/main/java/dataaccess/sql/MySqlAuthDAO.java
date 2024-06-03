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
import java.util.UUID;

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
  public AuthData makeAuthToken(String username) throws DataAccessException{
    // add a new authToken and Username to the DB
    String token = tokenizer();
    AuthData authToken = new AuthData(token,username);
    try (var conn = DatabaseManager.getConnection()) {
      String dataToInsert = "INSERT INTO authDataTable (authToken, username) VALUES (?, ?);";
      var preparedStatement = conn.prepareStatement(dataToInsert);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, token);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    return authToken;
  }
  @Override
  public ArrayList<String> getAuthTokens() throws DataAccessException{
    return null;
  }
  @Override
  public boolean validateAuthToken(String authToken) throws DataAccessException{
    return false;
  }
  @Override
  public String getUsername(String authToken) throws DataAccessException{
    String dbAuthToken;
    String query = "SELECT username FROM userDataTable WHERE authToken =?;";

    try (var conn = DatabaseManager.getConnection()) {
      var preparedStatement=conn.prepareStatement(query);
      var result=preparedStatement.executeQuery();
      if(result.next()){
        dbAuthToken = result.getString("authToken");
      }
      if(authToken.equals(dbAuthToken)) return true;
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    return null;
  }
  @Override
  public void deleteAuthData(LogoutRequest authToken){

  }
  @Override
  public String tokenizer(){
    return UUID.randomUUID().toString();
  }

}
