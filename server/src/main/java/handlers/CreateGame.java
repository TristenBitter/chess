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
    CreateGameService createGameService = new CreateGameService();
    int result = createGameService.createGame(newGame, newGamesAuthToken);

    if(result == 401){
      ErrorMessage error = new ErrorMessage("Username or Password is Incorrect");
      response.status(401);
      return new Gson().toJson(error);
    }
    if(result == 400){
      ErrorMessage error = new ErrorMessage("Username or Password is Incorrect");
      response.status(400);
      return new Gson().toJson(error);
    }
    response.status(200);

    //TODO: I might have to change result to be a CreatGameRequest type instead of an int.

    return new Gson().toJson(result);

  }
}
