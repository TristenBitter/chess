package Service;

import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.GameData;
import model.JoinGameRequest;

public class JoinGameService {

  private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
  private static MemoryGameDAO gameDAO = new MemoryGameDAO();
  public int joinGame(JoinGameRequest requestedGame, String authToken){

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
