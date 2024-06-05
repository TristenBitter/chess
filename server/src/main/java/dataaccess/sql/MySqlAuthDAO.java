package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import model.LogoutRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MySqlAuthDAO implements AuthDAO {
  private static
  final String[] CREATE_STATEMENTS= {
          """
            CREATE TABLE IF NOT EXISTS  authDataTable (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(authToken),
              INDEX(username)
            );
            """
  };

  public static void createAuthDBTable()throws DataAccessException{
    MySqlAuthDAO authDAO = new MySqlAuthDAO();
    authDAO.createTable(CREATE_STATEMENTS);

  }

  public static void createTable(String[] createStatements) throws DataAccessException{
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
      String dataToInsert = "INSERT INTO authDataTable (username, authToken) VALUES (?, ?);";
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
  public ArrayList<String> getAuthTokens() throws DataAccessException {
    return null;
  }

  @Override
  public boolean validateAuthToken(String authToken) throws DataAccessException{
    String query = "SELECT authToken FROM authDataTable WHERE authToken=?";

    try (var conn = DatabaseManager.getConnection()) {
      var preparedStatement=conn.prepareStatement(query);
      preparedStatement.setString(1, authToken);
      var result=preparedStatement.executeQuery();
      if(result.next()){
        return true;
      }
      else{
        return false;
      }
      //return result.next();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    //return false;
  }


  @Override
  public String getUsername(String authToken) throws DataAccessException{
    String query = "SELECT username FROM authDataTable WHERE authToken =?;";
    //String token = authToken.authToken();
    String username = null;
    try (var conn = DatabaseManager.getConnection()) {
      var preparedStatement=conn.prepareStatement(query);
      preparedStatement.setString(1, authToken);
      var result=preparedStatement.executeQuery();
      if(result.next()){
        username = result.getString("username");
      }
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
    return username;
  }
  @Override
  public void deleteAuthData(LogoutRequest authToken) throws DataAccessException{
    String token = authToken.authToken();
    String username = getUsername(token);

    try (var conn = DatabaseManager.getConnection()) {
      String dataToInsert = "DELETE FROM authDataTable WHERE authToken = ? AND username = ?;";
      var preparedStatement = conn.prepareStatement(dataToInsert);
      preparedStatement.setString(1, token);
      preparedStatement.setString(2, username);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
  @Override
  public String tokenizer(){
    return UUID.randomUUID().toString();
  }

@Override
public ArrayList<AuthData> getAll(){return null;}

}
