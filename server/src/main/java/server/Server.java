package server;


import dataaccess.*;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import dataaccess.sql.MySqlUserDAO;
import handlers.*;
import spark.*;

public class Server {
    //private static final DatabaseManager databaseManager = new DatabaseManager();
    private static final MySqlUserDAO MY_SQL_USER_DAO= new MySqlUserDAO();
    private static final MySqlAuthDAO MY_SQL_AUTH_DAO= new MySqlAuthDAO();
    private static final MySqlGameDAO MY_SQL_GAME_DAO= new MySqlGameDAO();

    public int run(int desiredPort) {
        //call a method to create a database
        try {
            DatabaseManager.createDatabase();
            //call a method to create the tables for all 3 DAO's
            MY_SQL_USER_DAO.createUserDBTable();
            MY_SQL_AUTH_DAO.createAuthDBTable();
            MY_SQL_GAME_DAO.createGameDBTable();

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


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}


