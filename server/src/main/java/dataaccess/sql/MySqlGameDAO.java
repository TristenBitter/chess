package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import model.ListGamesRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlGameDAO implements GameDAO {

  private static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  gameDataTable (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` text NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(password),
              INDEX(username),
              INDEX(email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
  };

  public static void createGameDBTable() throws DataAccessException {
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
  public int joinGame(JoinGameRequest requestedGame, String username){
    return 0;
  }

  @Override
  public void addPlayerAsColor(String color,String username, GameData game ){
  }

  @Override
  public int findGameToJoin(int gameID){
    return 0;
  }

  @Override
  public GameData getGame(int gameID){
    return null;
  }

  @Override
  public boolean doesGameExist(int gameID){
    return false;
  }

  @Override
  public int generateRandomID(){
    return 0;
  }

  @Override
  public CreateGameRequest createNewGame(String gameName){
    return null;
  }

  @Override
  public ArrayList<ListGamesRequest> getListOfGames(){
    return null;
  }

  @Override
  public void createGame(GameData gameInfo){
  }

  @Override
  public ArrayList<GameData> getAll(){
    return null;
  }

}
