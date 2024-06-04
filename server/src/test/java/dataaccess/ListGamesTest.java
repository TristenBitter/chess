package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.ListGamesRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import service.CreateGameService;
import service.ListGamesService;
import service.RegisterService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
public class ListGamesTest {

  private final RegisterRequest newUserData = new RegisterRequest("coolDude", "dude123", "cooldude@gmail.com");
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");

  @Test
  public void listGamesSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);

    CreateGameService createGameService = new CreateGameService();
    createGameService.createGame("newGame", authInfo.authToken());

    ListGamesService listGamesService = new ListGamesService();
    ArrayList<ListGamesRequest> result = listGamesService.listGames(authInfo.authToken());

    //assertThrowsExactly(DataAccessException.class, () -> listGamesService.listGames(authInfo.authToken()), "Error: unauthorized");
    assertNotEquals(null, new Gson().toJson(result));
  }

  @Test
  public void listGamesFailure() throws BadRequestException, AlreadyTakenException, DataAccessException, UnauthorizedException {

    RegisterService req = new RegisterService(newUserData);
    AuthData authInfo = req.registerUser(newUserData);

    CreateGameService createGameService = new CreateGameService();
    createGameService.createGame("newGame", authInfo.authToken());

    ListGamesService listGamesService = new ListGamesService();
    //ArrayList<ListGamesRequest> result = listGamesService.listGames("hehehe");

    //assertEquals(null, result);
    assertThrowsExactly(DataAccessException.class, () -> listGamesService.listGames("hehehe"), "Error: unauthorized");

  }
}



