
package service;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
    public class CreateGameTest {
      private final RegisterRequest newUserData = new RegisterRequest("coolDude", "dude123", "cooldude@gmail.com");

      private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");

      @Test
      public void createGameSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
        ClearService clearDB = new ClearService();
        clearDB.clearDB();
        RegisterService req = new RegisterService(userData);
        AuthData authInfo = req.registerUser(userData);

        CreateGameService createGameService = new CreateGameService();
        CreateGameRequest result = createGameService.createGame("newGame", authInfo.authToken());

        assertNotEquals(null, new Gson().toJson(result));
      }

      @Test
      public void createGameFailure() throws Exception {
        RegisterService req = new RegisterService(newUserData);
        AuthData authInfo = req.registerUser(newUserData);

        CreateGameService createGameService = new CreateGameService();
        CreateGameRequest result = createGameService.createGame("newGame", "hello");

        assertEquals(null, result);
      }
    }
