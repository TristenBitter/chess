package dataaccess.memory;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.CreateGameRequest;
import model.GameData;
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

    if(allData.isEmpty())return null;

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
  @Override
  public void clear() {
    gameData.clear();
  }
}
