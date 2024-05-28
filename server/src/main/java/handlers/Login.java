package handlers;

import Service.LoginService;
import com.google.gson.Gson;
import model.AuthData;
import model.ErrorMessage;
import model.LoginRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class Login implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    LoginRequest login = new Gson().fromJson(request.body(), LoginRequest.class);
    LoginService loginService = new LoginService(login);
    AuthData result = loginService.loginUser(login);

    if(result == null){
      ErrorMessage error = new ErrorMessage("Username or Password is Incorrect");
      response.status(401);
      return new Gson().toJson(error);
    }
    response.status(200);
    return new Gson().toJson(result);
  }
}
