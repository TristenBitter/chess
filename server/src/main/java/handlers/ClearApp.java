package handlers;

import dataaccess.memory.MemoryUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import spark.Request;
import spark.Response;
import spark.Route;
import Service.ClearService;

public class ClearApp implements Route {
  public ClearApp(MemoryUserDAO userData, MemoryAuthDAO authData, MemoryGameDAO gameData){
    ClearService clearDB = new ClearService(userData, authData, gameData);
    clearDB.ClearDB(userData, authData, gameData);

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {

//    ClearService clearDB = new ClearService(userData, authData, gameData);
//    clearDB.ClearDB(userData, authData, gameData);

    return null;
  }
}
