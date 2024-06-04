package service;

import dataaccess.*;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import model.JoinGameRequest;

public class JoinGameService {

  private static AuthDAO authDAO = new MySqlAuthDAO();
  private static GameDAO gameDAO = new MySqlGameDAO();
  public int joinGame(JoinGameRequest requestedGame, String authToken) throws DataAccessException {

    //Verify AuthToken
    if(!authDAO.validateAuthToken(authToken))return 401;
    //Verify that the requested game exists
    if(!gameDAO.doesGameExist(requestedGame.gameID())){
      return 400;
    }

    // get username from authToken
    String username = authDAO.getUsername(authToken);
    // call joinGame
    int joinGame = gameDAO.joinGame(requestedGame, username);

    return joinGame;
  }

}
