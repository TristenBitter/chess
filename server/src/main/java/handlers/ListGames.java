package handlers;

import service.ListGamesService;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.ErrorMessage;
import model.ListGamesRequest;
import model.ReturnGamesRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class ListGames implements Route {

  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      String listGamesAuthTok=request.headers("Authorization");
      ListGamesService listGamesService=new ListGamesService();
      ArrayList<ListGamesRequest> result=listGamesService.listGames(listGamesAuthTok);

      ReturnGamesRequest answer=new ReturnGamesRequest(result);

      response.status(200);
      return new Gson().toJson(answer);

    } catch (DataAccessException e) {
      ErrorMessage error=new ErrorMessage("Error: unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }
  }
}
