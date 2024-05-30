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
    AuthData result = registerUser.registerUser(req);
    // make an error object with a field called message of type string

    //if req is empty or null return error 400
    // "Error Bad Request


    if(result == null){
      ErrorMessage error = new ErrorMessage("Error: already exists");
      response.status(403);
      return new Gson().toJson(error);
    }
    response.status(200);
    //if it is good return this
    return new Gson().toJson(result);
  }
}
