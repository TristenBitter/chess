package handlers;


import service.LogoutService;
import com.google.gson.Gson;
import model.ErrorMessage;
import model.LogoutRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class Logout implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String logout = request.headers("Authorization");
    LogoutRequest logoutRequest = new LogoutRequest(logout);
    LogoutService logoutService = new LogoutService(logoutRequest);
    boolean result = logoutService.logoutUser(logoutRequest);

    if(result == false){
      //ErrorMessage error = new Gson().fromJson("Error: unauthorized", ErrorMessage.class);
      ErrorMessage error = new ErrorMessage("Error: unauthorized");
      response.status(401);
      return new Gson().toJson(error);
    }
    response.status(200);
    return "{}";
  }
}
