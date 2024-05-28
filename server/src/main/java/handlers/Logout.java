package handlers;


import Service.LogoutService;
import com.google.gson.Gson;
import model.AuthData;
import model.ErrorMessage;
import model.LogoutRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class Logout implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    LogoutRequest logout = new Gson().fromJson(request.body(), LogoutRequest.class);
    LogoutService logoutService = new LogoutService(logout);
    boolean result = logoutService.logoutUser(logout);

    if(result == true){
      ErrorMessage error = new Gson().fromJson("Error: unauthorized", ErrorMessage.class);
      response.status(400);
      return new Gson().toJson(error);
    }
    response.status(200);
    return "{}";
  }
}
