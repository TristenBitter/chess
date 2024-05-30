package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import service.ClearService;

public class ClearApp implements Route {
  public ClearApp(){
//    ClearService clearDB = new ClearService(userData, authData, gameData);
//    clearDB.ClearDB(userData, authData, gameData);

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
      //ClearService req = new Gson().fromJson(request.body(), ClearService.class);
    ClearService clearDB = new ClearService();
    clearDB.clearDB();
    return "{}";
  }
}
