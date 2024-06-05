package dataaccess;
import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import model.*;
import org.junit.jupiter.api.Test;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTest {
  private final MySqlUserDAO userDAO= new MySqlUserDAO();
  private final MySqlAuthDAO authDAO= new MySqlAuthDAO();
  private final MySqlGameDAO gameDAO= new MySqlGameDAO();
  private final RegisterRequest userData = new RegisterRequest("TristenBitter", "Tee123", "tristenkbitter@gmail.com");
  @Test
  public void gameSuccess() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();

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
  public void gameFailure() throws Exception{

    DatabaseManager.createDatabase();
    userDAO.createUserDBTable();
    authDAO.createAuthDBTable();
    gameDAO.createGameDBTable();

    ClearService clearDB = new ClearService();
    clearDB.clearDB();

    RegisterService req = new RegisterService(userData);
    AuthData authInfo = req.registerUser(userData);

    CreateGameService createGameService = new CreateGameService();
    CreateGameRequest game = createGameService.createGame("newGame", authInfo.authToken());

    JoinGameService joinGameService = new JoinGameService();
    JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", game.gameID());
    int result = joinGameService.joinGame(joinGameRequest, "SaludosAmigos");

    assertNotEquals(200, result);

  }

}
