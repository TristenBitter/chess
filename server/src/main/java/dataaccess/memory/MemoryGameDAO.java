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

  private static final ArrayList<GameData> gameData = new ArrayList<>();

  private ChessGame game = new ChessGame();

  public MemoryGameDAO(){}

  public ArrayList<GameData> getAll(){return gameData;}

  public void createGame(GameData gameInfo){
    this.gameData.add(gameInfo);
  }


  public ArrayList<ListGamesRequest> getListOfGames(){
    ArrayList<ListGamesRequest> listOfGames = new ArrayList<>();
    ArrayList<GameData> allData = getAll();

//    if(allData.isEmpty()){
//      return null;
//    }

    for (GameData data: allData
         ) {int id =data.GameID();
            String wP =data.whiteUsername();
            String bP =data.blackUsername();
            String name =data.gameName();
            ListGamesRequest gameReq = new ListGamesRequest(id, wP, bP, name);
            listOfGames.add(gameReq);
    }

    return listOfGames;
  }

  public CreateGameRequest createNewGame(String gameName){
    int newID = generateRandomID();
    GameData newGame = new GameData(newID, null, null, gameName, game);

    this.gameData.add(newGame);

    CreateGameRequest newGameID = new CreateGameRequest(newID);

    return newGameID;
  }

  public int generateRandomID(){
    Random random = new Random();
    int bounds = 2147483645;
    int randomNum = random.nextInt(bounds);

    return randomNum;
  }

  public boolean doesGameExist(int gameID){
    ArrayList<GameData> allData = getAll();
    for (GameData data:allData
         ) {if(gameID == data.GameID()){
           return true;
    }
    }

    return false;
  }

  public GameData getGame(int gameID){
    ArrayList<GameData> allData = getAll();
    for (GameData data:allData
    ) {if(gameID == data.GameID()){
      return data;
    }
    }

    return null;
  }

  public int findGameToJoin(int gameID){
    int index = 0;

    ArrayList<GameData> allGames = gameData;
    for (GameData data:allGames
    ) {if(gameID == data.GameID()){
      return index;
    }
    index++;
    }

    return index;
  }

  public void addPlayerAsColor(String color,String username, GameData game ){
    if(color.equals("WHITE")){
      GameData updatedGame = new GameData(game.GameID(),username, game.blackUsername(), game.gameName(), game.game());
      int index = findGameToJoin(game.GameID());
      gameData.set(index, updatedGame);
    }
    else{
      GameData updatedGame = new GameData(game.GameID(), game.whiteUsername(), username, game.gameName(), game.game());
      int index = findGameToJoin(game.GameID());
      gameData.set(index, updatedGame);
    }
  }
  public int joinGame(JoinGameRequest requestedGame, String username){
    // getGame
    GameData game = getGame(requestedGame.gameID());

    if(requestedGame.playerColor() == null){
      return 400;
    }
    //check Users/Colors
    if(game.whiteUsername() == null){
      if(requestedGame.playerColor().equals("WHITE")){
      // add the username as the white player
      addPlayerAsColor("WHITE", username, game);
      }
    }
    else{
      if(requestedGame.playerColor().equals("WHITE")){
        //white is already taken return error code 403
        return 403;
      }
    }

    if(game.blackUsername() == null){
      if(requestedGame.playerColor().equals("BLACK")){
        // add the username as the white player
        addPlayerAsColor("BLACK", username, game);
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
  public void clear() {
    gameData.clear();
  }
}
