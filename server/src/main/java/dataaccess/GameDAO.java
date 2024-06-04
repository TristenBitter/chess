package dataaccess;

import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import model.ListGamesRequest;

import java.util.ArrayList;

public interface GameDAO {
  public void clear() throws DataAccessException;

  public int joinGame(JoinGameRequest requestedGame, String username) throws DataAccessException;

  public void addPlayerAsColor(String color,String username, GameData game ) throws DataAccessException;

  public int findGameToJoin(int gameID) throws DataAccessException;

  public GameData getGame(int gameID) throws DataAccessException;

  public boolean doesGameExist(int gameID) throws DataAccessException;

  public int generateRandomID();

  public CreateGameRequest createNewGame(String gameName) throws DataAccessException;

  public ArrayList<ListGamesRequest> getListOfGames() throws DataAccessException;

  public void createGame(GameData gameInfo) throws DataAccessException;

  public ArrayList<GameData> getAll() throws DataAccessException;
}
