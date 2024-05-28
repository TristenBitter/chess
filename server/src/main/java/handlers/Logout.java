package handlers;


import Service.LogoutService;
import com.google.gson.Gson;
import model.AuthData;
import model.LogoutRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class Logout implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    LogoutRequest logout = new Gson().fromJson(request.body(), LogoutRequest.class);
    LogoutService logoutService = new LogoutService(logout);
    AuthData result = logoutService.logoutUser(logout);

    return "{}";
  }
}
