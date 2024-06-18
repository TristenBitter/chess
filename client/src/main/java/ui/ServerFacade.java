package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ServerFacade {
  private ClientCommunicator clientCommunicator = new ClientCommunicator();
  private String port = "http://localhost:8080";

  public ServerFacade(int port) {
    String host = "http://localhost:";
      this.port = host + (Integer.toString(port));
  }

  public AuthData register(RegisterRequest request) throws IOException, URISyntaxException {

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
      }catch (Exception e){
        System.out.println("oops that's not a valid registration, please try again");
        return null;
      }

    }
    public AuthData login(LoginRequest loginRequest) throws IOException, URISyntaxException {

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
      }catch (Exception e){
        System.out.println("oops that's not a valid login, please try again");
        return null;
      }

    }
    public CreateGameRequest create(GameNameRequest gameNameRequest, String authToken)throws IOException, URISyntaxException  {

      URI uri=new URI(port + "/game");
      HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
      http.setRequestMethod("POST");

      http.setDoOutput(true);

      http.addRequestProperty("Authorization", authToken);

      var body = gameNameRequest;
      try (var outputStream = http.getOutputStream()) {
        var jsonBody = new Gson().toJson(body);
        outputStream.write(jsonBody.getBytes());
      }

      http.connect();

      try (InputStream respBody=http.getInputStream()) {
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);

       CreateGameRequest id =new Gson().fromJson(inputStreamReader, CreateGameRequest.class);
        System.out.println(id);
        return id;
      }catch (Exception e){
        System.out.println("oops that's not a valid creation request, please try again");
        return null;
      }
  }

  public ArrayList<ListGamesRequest> list(String authToken)throws IOException, URISyntaxException {

    URI uri=new URI(port + "/game");
    HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
    http.setRequestMethod("GET");

    // Specify that we are going to write out data
    http.setDoOutput(true);

    // Write out a header
    http.addRequestProperty("Authorization", authToken);

    // Make the request
    http.connect();

    // Output the response body
    try (InputStream respBody=http.getInputStream()) {
      InputStreamReader inputStreamReader=new InputStreamReader(respBody);

      // Read the raw JSON response
      JsonObject jsonResponse = JsonParser.parseReader(inputStreamReader).getAsJsonObject();

      if (jsonResponse.has("games")) {
        ArrayList<ListGamesRequest> games = new Gson().fromJson(jsonResponse.get("games"), new TypeToken<ArrayList<ListGamesRequest>>(){}.getType());

        return games;
      } else {
        System.out.println("Unexpected JSON format");
        return null;
      }

    } catch (JsonSyntaxException e) {
      e.printStackTrace();
      System.out.println("Error: Invalid JSON format");
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("oops that's not a valid list games request, please try again");
      return null;
    }

  }

  public boolean join(JoinGameRequest joinGameRequest, String authToken) throws IOException, URISyntaxException {
    URI uri=new URI(port + "/game");
    HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
    http.setRequestMethod("PUT");

    // Specify that we are going to write out data
    http.setDoOutput(true);

    // Write out a header
    http.addRequestProperty("Authorization", authToken);

    //Write the body
    var body = joinGameRequest;
    try (var outputStream = http.getOutputStream()) {
      var jsonBody = new Gson().toJson(body);
      outputStream.write(jsonBody.getBytes());
    }

    // Make the request
    http.connect();

    // Output the response body
    try (InputStream respBody=http.getInputStream()) {
      InputStreamReader inputStreamReader=new InputStreamReader(respBody);
      System.out.println("JOIN WAS SUCCESSFUL");
      return true;
    }catch (Exception e){
      System.out.println("oops that's not a valid join, please try again");
      return false;
    }



  }

  public void observe(CreateGameRequest gameID, String authToken){
      // call draw board to print the board
    ChessBoardDrawer draw = new ChessBoardDrawer();


  }

  public boolean logout(LogoutRequest logoutRequest, String authToken) throws IOException, URISyntaxException{

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
      return true;
    }catch (Exception e){
      System.out.println("oops something went wrong, please try again");
      return false;
    }

  }


}
