package service;

import dataaccess.*;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import model.CreateGameRequest;

public class CreateGameService {

  private static AuthDAO authDAO = new MySqlAuthDAO();
  private static GameDAO gameDAO = new MySqlGameDAO();
  public CreateGameRequest createGame(String nameOfGame, String authToken) throws UnauthorizedException, BadRequestException, DataAccessException {
      // ValidateToken
      if(!authDAO.validateAuthToken(authToken))return null;

      // Create a new Game
      CreateGameRequest newGame = gameDAO.createNewGame(nameOfGame);

      // return the new game

    return newGame;
  }

}
