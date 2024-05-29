package handlers;

import Service.ListGamesService;
import Service.LogoutService;
import com.google.gson.Gson;
import model.ErrorMessage;
import model.ListGamesRequest;
import model.LogoutRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class ListGames implements Route {

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String listGamesAuthTok = request.headers("ListGames");
    ListGamesService listGamesService = new ListGamesService();
    ArrayList<ListGamesRequest> result = listGamesService.listGames(listGamesAuthTok);

    if(result == null){
      //ErrorMessage error = new Gson().fromJson("Error: unauthorized", ErrorMessage.class);
      ErrorMessage error = new ErrorMessage("Error: unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }
    response.status(200);
    return new Gson().toJson(result);
  }
}
