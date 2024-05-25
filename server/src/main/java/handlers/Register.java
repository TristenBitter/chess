package handlers;

import Service.RegisterService;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import model.RegisterRequest;
import model.UserData;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;

public class Register implements Route {
  private MemoryUserDAO userDataInstance= new MemoryUserDAO();
  public Register(MemoryUserDAO userData) {
    //RegisterService registerUser = new RegisterService(userData);
    //userDataInstance.insertUserData(userData);
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    RegisterRequest req = new Gson().fromJson(request.body(), RegisterRequest.class);
    RegisterService registerUser = new RegisterService(req);

    // make an error object with a field called message of type string
    // "Error Bad Request

    // create new record

    //response.status(400);

    //if it is good return this
    return new Gson().toJson(req);
    //else return an error object

  }
}
