package dataaccess.memory;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import model.ListGamesRequest;

import java.util.ArrayList;
import java.util.Random;

public class MemoryGameDAO implements GameDAO {

  private static final ArrayList<GameData> GAME_DATA= new ArrayList<>();

  private ChessGame game = new ChessGame();

  public MemoryGameDAO(){}

  public ArrayList<GameData> getAll(){return GAME_DATA;}

  public void createGame(GameData gameInfo){
    this.GAME_DATA.add(gameInfo);
  }


  public ArrayList<ListGamesRequest> getListOfGames(){
    ArrayList<ListGamesRequest> listOfGames = new ArrayList<>();

    return listOfGames;
  }

  public CreateGameRequest createNewGame(String gameName){
    int newID = generateRandomID();
    GameData newGame = new GameData(newID, null, null, gameName, game);

    this.GAME_DATA.add(newGame);

    CreateGameRequest newGameID = new CreateGameRequest(newID);

    return newGameID;
  }

  public int generateRandomID(){

    return 0;
  }

  public boolean doesGameExist(int gameID){

    return false;
  }

  public GameData getGame(int gameID){

    return null;
  }

  public int findGameToJoin(int gameID){

    return 0;
  }

  public void addPlayerAsColor(String color,String username, GameData game ){
    if(color.equals("WHITE")){
      GameData updatedGame = new GameData(game.gameID(),username, game.blackUsername(), game.gameName(), game.game());
      int index = findGameToJoin(game.gameID());
      GAME_DATA.set(index, updatedGame);
    }
    else{
      GameData updatedGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
      int index = findGameToJoin(game.gameID());
      GAME_DATA.set(index, updatedGame);
    }
  }
  public int joinGame(JoinGameRequest requestedGame, String username){

    return 0;
  }


  @Override
  public void clear() {
    GAME_DATA.clear();
  }
}
