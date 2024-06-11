package client;

import dataaccess.DataAccessException;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import service.ClearService;
import ui.ServerFacade;

import java.io.IOError;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);

    }
    @BeforeEach
    public void clearDB() throws DataAccessException{
        ClearService clearDB = new ClearService();
        clearDB.clearDB();
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void registerSuccessTest() throws IOException, URISyntaxException {
      AuthData data = facade.register(new RegisterRequest("Tee", "t", "frogLover@gmail.com"));

        assertEquals("Tee",data.username());
        assertTrue(data.authToken().length() > 10);
    }
    @Test
    public void registerFailureTest() throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "t", "frogLover@gmail.com"));

        assertThrows(IOException.class, ()->facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com")));
    }


    @Test
    public void loginSuccessTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        AuthData data2 = facade.login(new LoginRequest("Tee", "b"));

        assertEquals(data.username(), data2.username());
    }
    @Test
    public void loginFailureTest()throws IOException, URISyntaxException {

        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));

        assertThrows(IOException.class, ()->facade.login(new LoginRequest("Tee", "t")));
    }
    @Test
    public void logoutSuccessTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        AuthData data2 = facade.login(new LoginRequest("Tee", "b"));

        assertDoesNotThrow(()->facade.logout(new LogoutRequest(data2.authToken()), data.authToken()));

    }
    @Test
    public void logoutFailureTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        AuthData data2 = facade.login(new LoginRequest("Tee", "b"));

        assertThrows(IOException.class, ()-> facade.logout(new LogoutRequest("hello"), "hello"));
    }
    @Test
    public void createSuccessTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));

        assertDoesNotThrow(()->facade.create(new GameNameRequest("game"), data.authToken()));
    }
    @Test
    public void createFailureTest()throws IOException, URISyntaxException {

        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));

        assertThrows(IOException.class, ()->facade.create(new GameNameRequest("game"), "hello"));
    }
    @Test
    public void joinSuccessTest()throws IOException, URISyntaxException {

        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        CreateGameRequest gameID = facade.create(new GameNameRequest("game"), data.authToken());

        assertDoesNotThrow(()->facade.join(new JoinGameRequest("WHITE", gameID.gameID()) ,data.authToken()));
    }
    @Test
    public void joinFailureTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        CreateGameRequest gameID = facade.create(new GameNameRequest("game"), data.authToken());

        assertThrows(IOException.class, ()->facade.join(new JoinGameRequest("WHITE", gameID.gameID()) ,"hello"));
        assertThrows(IOException.class, ()->facade.join(new JoinGameRequest("WHITE", 3) , data.authToken()));
    }
    @Test
    public void listSuccessTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        ArrayList<ListGamesRequest> games = facade.list(data.authToken());
        assertDoesNotThrow(()->facade.list(data.authToken()));
    }
    @Test
    public void listFailureTest()throws IOException, URISyntaxException {
        AuthData data = facade.register(new RegisterRequest("Tee", "b", "froger@gmail.com"));
        assertThrows(IOException.class ,()->facade.list("data.authToken()"));
    }


}
