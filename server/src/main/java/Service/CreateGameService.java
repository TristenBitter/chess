package Service;

import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.CreateGameRequest;

public class CreateGameService {

  private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
  private static MemoryGameDAO gameDAO = new MemoryGameDAO();
  public CreateGameRequest createGame(String nameOfGame, String authToken){
      // ValidateToken
      if(!authDAO.validateAuthToken(authToken))return null;

      // Create a new Game
      CreateGameRequest newGame = gameDAO.createNewGame(nameOfGame);

      // return the new game

    return newGame;
  }

}
