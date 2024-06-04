package service;

import dataaccess.*;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.CreateGameRequest;

public class CreateGameService {

  private static AuthDAO authDAO = new MemoryAuthDAO();
  private static GameDAO gameDAO = new MemoryGameDAO();
  public CreateGameRequest createGame(String nameOfGame, String authToken) throws UnauthorizedException, BadRequestException, DataAccessException {
      // ValidateToken
      if(!authDAO.validateAuthToken(authToken))return null;

      // Create a new Game
      CreateGameRequest newGame = gameDAO.createNewGame(nameOfGame);

      // return the new game

    return newGame;
  }

}
