package service;

import Service.CreateGameService;
import Service.ListGamesService;
import Service.RegisterService;
import com.google.gson.Gson;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class UnitTestListGames {

  private final RegisterRequest newUserData = new RegisterRequest("coolDude", "dude123", "cooldude@gmail.com");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");

  @Test
  public void ListGamesSuccess(){
    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);

    CreateGameService createGameService = new CreateGameService();
    createGameService.createGame("newGame", authInfo.authToken());

    ListGamesService listGamesService = new ListGamesService();
    ArrayList<ListGamesRequest> result = listGamesService.listGames(authInfo.authToken());

    assertNotEquals(null, new Gson().toJson(result));
  }

  @Test
  public void ListGamesFailure(){

    RegisterService req = new RegisterService(newUserData);
    AuthData authInfo = req.registerUser(newUserData);

    CreateGameService createGameService = new CreateGameService();
    createGameService.createGame("newGame", authInfo.authToken());

    ListGamesService listGamesService = new ListGamesService();
    ArrayList<ListGamesRequest> result = listGamesService.listGames("hehehe");

    assertEquals(null, result);

  }
}



