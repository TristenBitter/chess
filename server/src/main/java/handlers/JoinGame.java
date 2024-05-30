package handlers;

import service.JoinGameService;
import com.google.gson.Gson;
import model.ErrorMessage;
import model.JoinGameRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGame implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String joinGameAuthToken = request.headers("Authorization");
    JoinGameRequest joinGameRequest = new Gson().fromJson(request.body(), JoinGameRequest.class);
    JoinGameService joinGameService = new JoinGameService();
    int result = joinGameService.joinGame(joinGameRequest, joinGameAuthToken);

    if(result == 401){
      ErrorMessage error = new ErrorMessage("Unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }
    if(result == 400){
      ErrorMessage error = new ErrorMessage("Bad Request");
      response.status(400);
      return new Gson().toJson(error);
    }
    if(result == 403){
      ErrorMessage error = new ErrorMessage("Already Taken");
      response.status(403);
      return new Gson().toJson(error);
    }

    response.status(200);
    return "{}";

  }
}
