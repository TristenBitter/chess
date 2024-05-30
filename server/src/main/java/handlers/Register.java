package handlers;

import Service.RegisterService;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import model.AuthData;
import model.ErrorMessage;
import model.RegisterRequest;
import model.UserData;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;

public class Register implements Route {
  private MemoryUserDAO userDataInstance= new MemoryUserDAO();
  public Register() {
    //RegisterService registerUser = new RegisterService(userData);
    //userDataInstance.insertUserData(userData);
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    RegisterRequest req = new Gson().fromJson(request.body(), RegisterRequest.class);
    RegisterService registerUser = new RegisterService(req);

    int flag = 200;
    if(!registerUser.hasUsernameBeenTaken(req)){
      flag = 403;
    }
    if(req.password() == null){

      flag = 400;
    }

    if(flag == 403){
      ErrorMessage error = new ErrorMessage("Error: already exists");
      response.status(403);
      return new Gson().toJson(error);
    }
    if(flag == 400){
      ErrorMessage error = new ErrorMessage("Error: bad request");
      response.status(400);
      return new Gson().toJson(error);
    }

    AuthData result = registerUser.registerUser(req);

    response.status(200);
    return new Gson().toJson(result);
  }
}
