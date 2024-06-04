package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.CreateGameRequest;
import model.JoinGameRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.CreateGameService;
import service.JoinGameService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class JoinGameTest {
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  private final RegisterRequest newUserData = new RegisterRequest("coolDude", "dude123", "cooldude@gmail.com");
  @Test
  public void joinGameSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);

    CreateGameService createGameService = new CreateGameService();
    CreateGameRequest game = createGameService.createGame("newGame", authInfo.authToken());

    JoinGameService joinGameService = new JoinGameService();
    JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", game.gameID());
    int result = joinGameService.joinGame(joinGameRequest, authInfo.authToken());

    assertNotEquals(null, new Gson().toJson(result));
    assertEquals(200, result);
  }

  @Test
  public void joinGameFailure() throws BadRequestException, AlreadyTakenException, DataAccessException, UnauthorizedException {
    RegisterService req = new RegisterService(newUserData);
    AuthData authInfo = req.registerUser(newUserData);

    CreateGameService createGameService = new CreateGameService();
    CreateGameRequest game = createGameService.createGame("newGame", authInfo.authToken());

    JoinGameService joinGameService = new JoinGameService();
    JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 333);
    int result = joinGameService.joinGame(joinGameRequest, authInfo.authToken());

    assertNotEquals(200, result);
  }
}





