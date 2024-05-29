package Service;

import model.JoinGameRequest;

public class JoinGameService {
  public int joinGame(JoinGameRequest requestedGame, String AuthToken){

    //Verify AuthToken

    //Verify that the requested game exists
    if(!doesGameExist(requestedGame.gameID())){
      return 401;
    }
    //add the player (authToken) to the game as the requested color

    return 0;
  }

  public boolean doesGameExist(int gameID){

    return false;
  }
}
