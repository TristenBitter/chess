package server;


import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import handlers.*;
import model.ErrorMessage;
import spark.*;

public class Server {
    //private static final DatabaseManager databaseManager = new DatabaseManager();
    private static final MySqlUserDAO  mySqlUserDAO = new MySqlUserDAO();
    private static final MySqlAuthDAO mySqlAuthDAO = new MySqlAuthDAO();
    private static final MySqlGameDAO mySqlGameDAO = new MySqlGameDAO();

    public int run(int desiredPort) {
        //call a method to create a database
        try {
            DatabaseManager.createDatabase();
            //call a method to create the tables for all 3 DAO's
            mySqlUserDAO.createUserDBTable();
            mySqlAuthDAO.createAuthDBTable();
            mySqlGameDAO.createGameDBTable();

        }catch(DataAccessException e){
            System.out.println("error creating database and tables");
            return 0;
        }

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/user", new Register());
        Spark.post("/game", new CreateGame());
        Spark.post("/session", new Login());
        Spark.get("/game", new ListGames());
        Spark.put("/game", new JoinGame());
        Spark.delete("/session", new Logout());
        Spark.delete("/db", new ClearApp());

//        Spark.exception(AlreadyTakenException.class, (AlreadyTaken, request, response) ->
//        {response.status(403);
//            response.body(new Gson().toJson(new ErrorMessage("Error: already exists")))};
//
//        Spark.exception(UnauthorizedException.class, (Unauthorized, request, response) ->
//        {response.status(401);
//            response.body(new Gson().toJson(new ErrorMessage("Error: unauthorized")))};
//
//        Spark.exception(BadRequestException.class, (BadRequest, request, response) ->
//        {response.status(400);
//            response.body(new Gson().toJson(new ErrorMessage("Error: bad request")))};


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}


