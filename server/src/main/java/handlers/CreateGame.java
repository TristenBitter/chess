package handlers;

import Service.CreateGameService;
import com.google.gson.Gson;
import model.CreateGameRequest;
import model.ErrorMessage;
import model.GameNameRequest;
import model.LoginRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGame implements Route {

  private CreateGameRequest answer;
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String newGamesAuthToken = request.headers("Authorization");

    if(newGamesAuthToken == null || newGamesAuthToken.isEmpty()){
      ErrorMessage error = new ErrorMessage("Unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }

    //GameNameRequest newGame = new GameNameRequest(new Gson().fromJson(request.body(), GameNameRequest.class));
    try {
      GameNameRequest newGame= new Gson().fromJson(request.body(), GameNameRequest.class);
      CreateGameService createGameService = new CreateGameService();
      CreateGameRequest result = createGameService.createGame(newGame.gameName(), newGamesAuthToken);
      answer = result;
      if(result == null){
        ErrorMessage error = new ErrorMessage("Unauthorized");
        response.status(401);
        return new Gson().toJson(error);
      }
    }catch (Exception exception){
      ErrorMessage error = new ErrorMessage("Bad Request");
      response.status(400);
      return new Gson().toJson(error);
    }

//    CreateGameService createGameService = new CreateGameService();
//    CreateGameRequest result = createGameService.createGame(newGame, newGamesAuthToken);

    response.status(200);

    return new Gson().toJson(answer);

  }
}
