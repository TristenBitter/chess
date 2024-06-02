package dataaccess;

import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import model.ListGamesRequest;

import java.util.ArrayList;

public interface GameDAO {
  public void clear();

  public int joinGame(JoinGameRequest requestedGame, String username);

  public void addPlayerAsColor(String color,String username, GameData game );

  public int findGameToJoin(int gameID);

  public GameData getGame(int gameID);

  public boolean doesGameExist(int gameID);

  public int generateRandomID();

  public CreateGameRequest createNewGame(String gameName);

  public ArrayList<ListGamesRequest> getListOfGames();

  public void createGame(GameData gameInfo);

  public ArrayList<GameData> getAll();
}
