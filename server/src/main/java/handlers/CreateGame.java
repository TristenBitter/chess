package handlers;

import Service.CreateGameService;
import com.google.gson.Gson;
import model.CreateGameRequest;
import model.ErrorMessage;
import model.LoginRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGame implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String newGamesAuthToken = request.headers("NewGames");
    String newGame= new Gson().fromJson(request.body(), String.class);

    if(newGame.isEmpty()){
      ErrorMessage error = new ErrorMessage("Bad Request");
      response.status(400);
      return new Gson().toJson(error);
    }

    CreateGameService createGameService = new CreateGameService();
    CreateGameRequest result = createGameService.createGame(newGame, newGamesAuthToken);

    if(result == null){
      ErrorMessage error = new ErrorMessage("Unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }
//    if(result == 400){
//      ErrorMessage error = new ErrorMessage("Bad Request");
//      response.status(400);
//      return new Gson().toJson(error);
//    }

    response.status(200);

    return new Gson().toJson(result);

  }
}
