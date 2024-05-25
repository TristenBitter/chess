package server;

import dataaccess.memory.MemmoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        MemmoryUserDAO userData = new MemmoryUserDAO();
        MemoryAuthDAO authData = new MemoryAuthDAO();
        MemoryGameDAO gameData = new MemoryGameDAO();

        Spark.post("/user", new Register());
        Spark.post("/game", new CreateGame());
        Spark.post("/session", new Login());
        Spark.get("/game", new ListGames());
        Spark.put("/game", new JoinGame());
        Spark.delete("/session", new Logout());
        Spark.delete("/db", new ClearApp(userData, authData, gameData));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }



    //gson is a json java interpreter

}


