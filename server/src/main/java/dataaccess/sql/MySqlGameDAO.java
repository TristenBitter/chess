package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import model.ListGamesRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MySqlGameDAO implements GameDAO {
  private ChessGame newChessGame = new ChessGame();

  private static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  gameDataTable (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` text NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName)
            );
            """
  };

  //TODO: I need to Serialize the chess game into Json to change thee game status...
  //

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
  public void clear() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      String clear = "DELETE FROM gameDataTable";
      var preparedStatement = conn.prepareStatement(clear);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
  @Override
  public int joinGame(JoinGameRequest requestedGame, String username) throws DataAccessException{
    // getGame
    GameData gameData = getGame(requestedGame.gameID());

    if(requestedGame.playerColor() == null){
      return 400;
    }
    //check Users/Colors
    if(gameData.whiteUsername() == null){
      if(requestedGame.playerColor().equals("WHITE")){
        // add the username as the white player
        addPlayerAsColor("WHITE", username, gameData);
      }
    }
    else{
      if(requestedGame.playerColor().equals("WHITE")){
        //white is already taken return error code 403
        return 403;
      }
    }

    if(gameData.blackUsername() == null){
      if(requestedGame.playerColor().equals("BLACK")){
        // add the username as the white player
        addPlayerAsColor("BLACK", username, gameData);
      }
    }
    else{
      if(requestedGame.playerColor().equals("BLACK")){
        //black is already taken return error code 403
        return 403;
      }
    }

    return 200;
  }

  @Override
  public void addPlayerAsColor(String color,String username, GameData game ) throws DataAccessException{
    int gameID = game.gameID();
    if(color.equals("WHITE")) {
      try (var conn=DatabaseManager.getConnection()) {
        String dataToInsert="UPDATE gameDataTable SET whiteUsername=? WHERE gameID=?;";
        var preparedStatement=conn.prepareStatement(dataToInsert);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, gameID);
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
      }
    }
    else{
      try (var conn=DatabaseManager.getConnection()) {
        String dataToInsert="UPDATE gameDataTable SET blackUsername=? WHERE gameID=?;";
        var preparedStatement=conn.prepareStatement(dataToInsert);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, gameID);
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
      }
    }
  }

  @Override
  public int findGameToJoin(int gameID) throws DataAccessException {
    return 0;
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException{
    ArrayList<GameData> allData = getAll();
    for (GameData data:allData
    ) {if(gameID == data.gameID()){
        return data;
      }
    }
    return null;
  }

  @Override
  public boolean doesGameExist(int gameID) throws DataAccessException{
    ArrayList<GameData> allData = getAll();
    for (GameData data:allData
    ) {if(gameID == data.gameID()){
        return true;
      }
    }
    return false;
  }

  @Override
  public int generateRandomID(){
    Random random = new Random();
    int bounds = 2147483645;
    int randomNum = random.nextInt(bounds);

    return randomNum;
  }

  @Override
  public CreateGameRequest createNewGame(String gameName) throws DataAccessException{
    int gameID = generateRandomID();
    GameData newGame = new GameData(gameID, null, null, gameName, newChessGame);
    createGame(newGame);

    return new CreateGameRequest(gameID);
  }

  @Override
  public ArrayList<ListGamesRequest> getListOfGames() throws DataAccessException{
    ArrayList<ListGamesRequest> listOfGames = new ArrayList<>();
    ArrayList<GameData> allData = getAll();

    for (GameData data: allData
    ) {int id =data.gameID();
      String wP =data.whiteUsername();
      String bP =data.blackUsername();
      String name =data.gameName();
      ListGamesRequest gameReq = new ListGamesRequest(id, wP, bP, name);
      listOfGames.add(gameReq);
    }
    return listOfGames;
  }

  @Override
  public void createGame(GameData gameInfo) throws DataAccessException{

    // serialize chess game from object to json
    var game = new Gson().toJson(gameInfo.game());

    try (var conn = DatabaseManager.getConnection()) {
      String dataToInsert = "INSERT INTO gameDataTable (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?);";
      var preparedStatement = conn.prepareStatement(dataToInsert);
      preparedStatement.setInt(1, gameInfo.gameID());
      preparedStatement.setString(2, gameInfo.whiteUsername());
      preparedStatement.setString(3, gameInfo.blackUsername());
      preparedStatement.setString(4, gameInfo.gameName());
      preparedStatement.setString(5, game);
      preparedStatement.executeUpdate();
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }

  @Override
  public ArrayList<GameData> getAll() throws DataAccessException{
    ArrayList<GameData> allGames = new ArrayList<>();

    String query = "SELECT * FROM gameDataTable;";
    try (var conn = DatabaseManager.getConnection()) {
      var preparedStatement=conn.prepareStatement(query);
      var result=preparedStatement.executeQuery();
      while(result.next()){
        int gameID = result.getInt("gameID");
        String wP = result.getString("whiteUsername");
        String bP = result.getString("blackUsername");
        String name = result.getString("gameName");
        String chessGame = result.getString("game");

        ChessGame chessGame1 = new Gson().fromJson( chessGame, ChessGame.class);
        GameData data = new GameData(gameID, wP, bP, name, chessGame1);

        allGames.add(data);
      }
    }catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }

    return allGames;
  }

}
