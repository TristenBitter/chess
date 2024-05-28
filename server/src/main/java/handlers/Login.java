package handlers;

import Service.LoginService;
import com.google.gson.Gson;
import model.AuthData;
import model.ErrorMessage;
import model.LoginRequest;
import model.RegisterRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class Login implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    LoginRequest login = new Gson().fromJson(request.body(), LoginRequest.class);
    LoginService loginService = new LoginService();
    AuthData result = loginService.loginUser(login);

    if(result == null){
      ErrorMessage error = new Gson().fromJson("Username or Password is Incorrect", ErrorMessage.class);
      response.status(400);
      return new Gson().toJson(error);
    }
    response.status(200);
    return new Gson().toJson(result);
  }
}
