package server;

import handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", new Register());
        Spark.post("/game", new CreateGame());
        Spark.post("/session", new Login());
        Spark.get("/game", new ListGames());
        Spark.put("/game", new JoinGame());
        Spark.delete("/session", new Logout());
        Spark.delete("/db", new ClearApp());

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


    //gson is a json java interpreter

}


