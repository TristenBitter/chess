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
  private String port = "http://localhost:8080";
    public AuthData register(RegisterRequest request) throws IOException, URISyntaxException {

      // Specify the desired endpoint
      URI uri = new URI(port + "/user");
      HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
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
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader inputStreamReader = new InputStreamReader(respBody);

        AuthData authData = new Gson().fromJson(inputStreamReader, AuthData.class);
        System.out.println(new Gson().fromJson(inputStreamReader, AuthData.class));
        return authData;
      }

    }
    public AuthData login(LoginRequest loginRequest) throws Exception{
      AuthData authData = new AuthData("token", loginRequest.username());
      return authData;
    }
    public int create(GameNameRequest gameNameRequest, String authToken) {

      return 0;
  }

  public ArrayList<ListGamesRequest> list(String authToken){


      return null;
  }

  public void join(JoinGameRequest joinGameRequest, String authToken){

  }

  public void observe(CreateGameRequest gameID, String authToken){
      // call draw board to print the board

  }

  public void logout(LogoutRequest logoutRequest){

  }


}
