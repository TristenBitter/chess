package ui;

import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ServerFacade {
  private ClientCommunicator clientCommunicator = new ClientCommunicator();
  private String port = "http://localhost:8080";
    public AuthData register(RegisterRequest request) throws IOException, URISyntaxException {
//      String path = "/user";
//      AuthData data = clientCommunicator.postMethod(port, path, request);
//      return data;

      // Specify the desired endpoint
      URI uri=new URI(port + "/user");
      HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
      http.setRequestMethod("POST");

      /*********************************************************/
      // Specify that we are going to write out data
      http.setDoOutput(true);

      // Write out a header
      http.addRequestProperty("Content-Type", "application/json");


      // Write out the body
      var body = request;
      try (var outputStream = http.getOutputStream()) {
        var jsonBody = new Gson().toJson(body);
        outputStream.write(jsonBody.getBytes());
      }
      /***************************************************************/

      // Make the request
      http.connect();

      // Output the response body
      try (InputStream respBody=http.getInputStream()) {
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);

        AuthData authData=new Gson().fromJson(inputStreamReader, AuthData.class);
        System.out.println(authData);
        return authData;
      }
    }
    public AuthData login(LoginRequest loginRequest) throws IOException, URISyntaxException {
//        RegisterRequest request = new RegisterRequest(loginRequest.username(), loginRequest.password(), "null");
//        String path = "/session";
//        AuthData data = clientCommunicator.postMethod(port, path, request);
//        return data;

      URI uri=new URI(port + "/session");
      HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
      http.setRequestMethod("POST");

      /*********************************************************/
      // Specify that we are going to write out data
      http.setDoOutput(true);

      // Write out a header
      http.addRequestProperty("Content-Type", "application/json");


      // Write out the body
      var body = loginRequest;
      try (var outputStream = http.getOutputStream()) {
        var jsonBody = new Gson().toJson(body);
        outputStream.write(jsonBody.getBytes());
      }
      /***************************************************************/

      // Make the request
      http.connect();

      // Output the response body
      try (InputStream respBody=http.getInputStream()) {
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);

        AuthData authData=new Gson().fromJson(inputStreamReader, AuthData.class);
        System.out.println(authData);
        return authData;
      }

    }
    public CreateGameRequest create(GameNameRequest gameNameRequest, String authToken)throws IOException, URISyntaxException  {

      URI uri=new URI(port + "/game");
      HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
      http.setRequestMethod("POST");

      /*********************************************************/
      // Specify that we are going to write out data
      http.setDoOutput(true);

      // Write out a header
      http.addRequestProperty("Authorization", authToken);


      // Write out the body
      var body = gameNameRequest;
      try (var outputStream = http.getOutputStream()) {
        var jsonBody = new Gson().toJson(body);
        outputStream.write(jsonBody.getBytes());
      }

      /***************************************************************/

      // Make the request
      http.connect();

      // Output the response body
      try (InputStream respBody=http.getInputStream()) {
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);

       CreateGameRequest id =new Gson().fromJson(inputStreamReader, CreateGameRequest.class);
        System.out.println(id);
        return id;
      }
  }

  public ArrayList<ListGamesRequest> list(String authToken){


      return null;
  }

  public void join(JoinGameRequest joinGameRequest, String authToken){

  }

  public void observe(CreateGameRequest gameID, String authToken){
      // call draw board to print the board


  }

  public void logout(LogoutRequest logoutRequest, String authToken) throws IOException, URISyntaxException{

    URI uri=new URI(port + "/session");
    HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
    http.setRequestMethod("DELETE");

    http.setDoOutput(true);

    // Write out a header
    http.addRequestProperty("Authorization", authToken);


    // Write out the body
    var body = logoutRequest;
    try (var outputStream = http.getOutputStream()) {
      var jsonBody = new Gson().toJson(body);
      outputStream.write(jsonBody.getBytes());
    }
    /***************************************************************/

    // Make the request
    http.connect();

    // Output the response body
    try (InputStream respBody=http.getInputStream()) {
      InputStreamReader inputStreamReader=new InputStreamReader(respBody);

//      AuthData authData=new Gson().fromJson(inputStreamReader, AuthData.class);
//      System.out.println(authData);
    }

  }


}
