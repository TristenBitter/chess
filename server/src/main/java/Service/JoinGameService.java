package Service;

import dataaccess.memory.MemoryAuthDAO;
import model.JoinGameRequest;

public class JoinGameService {

  private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
  public int joinGame(JoinGameRequest requestedGame, String authToken){

    //Verify AuthToken
    if(!authDAO.validateAuthToken(authToken))return 401;
    //Verify that the requested game exists
    if(!doesGameExist(requestedGame.gameID())){
      return 400;
    }
    //General Synopsis [add the player (authToken) to the game as the requested color else return 403 already taken]

    //get the game from the game id

    // get username from authToken

    //check if the color he wanted to be is null  (if both are taken and he wanted a color then return 403

    // add username to proper color spot

    return 0;
  }

  public boolean doesGameExist(int gameID){

    return false;
  }
}
