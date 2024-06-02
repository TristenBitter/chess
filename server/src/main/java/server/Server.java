package server;


import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import handlers.*;
import spark.*;

public class Server {
    private static final DatabaseManager databaseManager = new DatabaseManager();
    private static final MySqlUserDAO  mySqlUserDAO = new MySqlUserDAO();
    private static final MySqlAuthDAO mySqlAuthDAO = new MySqlAuthDAO();
    private static final MySqlGameDAO mySqlGameDAO = new MySqlGameDAO();

    public int run(int desiredPort) {
        //call a method to create a database
        try {
            databaseManager.createDatabase();
            //call a method to create the tables for all 3 DAO's
            mySqlUserDAO.createUserDBTable();
            mySqlAuthDAO.createAuthDBTable();
            mySqlGameDAO.createGameDBTable();

        }catch(DataAccessException e){

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

        //Spark.exception(alreadytakenException.class, exceptionHandler or lambda (AlreadyTaken, request, response) -> {code to be exectued })

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}


