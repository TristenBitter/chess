package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", this::register);
        Spark.post("/game", this::createGame);
        Spark.post("/session", this::login);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);
        Spark.delete("/session", this::logout);
        Spark.delete("/db", this::clearApplication);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    //new methods to call handlers

    private Object clearApplication(Request req, Response res) {

        return 0;
    }
    private Object register(Request req, Response res) {

        return 0;
    }

    private Object createGame(Request req, Response res) {

        return 0;
    }

    private Object login(Request req, Response res) {

        return 0;
    }

    private Object logout(Request req, Response res) {

        return 0;
    }

    private Object listGames(Request req, Response res) {
        res.type("application/json");
        //return new Gson().toJson(Map.of("name", names));
        return 0;
    }

    private Object joinGame(Request req, Response res) {

        return 0;
    }


    //handlers

    //gson is a json java interpreter



}


