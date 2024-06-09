package ui;

import com.google.gson.Gson;
import model.AuthData;
import model.LoginRequest;
import model.RegisterRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;

public class ClientCommunicator {
  /********************************************************************************
                                      HTTP stuff
   *******************************************************************************/
    public AuthData postMethod(String port, String path, RegisterRequest request) throws IOException, URISyntaxException {

      // Specify the desired endpoint
      URI uri=new URI(port + path);
      HttpURLConnection http=(HttpURLConnection) uri.toURL().openConnection();
      http.setRequestMethod("POST");

      /*********************************************************/
      // Specify that we are going to write out data
      http.setDoOutput(true);

      // Write out a header
      http.addRequestProperty("Content-Type", "application/json");


      // Write out the body
      Map<String, String> body;
      if (request.email().equals("null")) {
        body=Map.of("username", request.username(), "password", request.password());
      } else body = Map.of("username", request.username(), "password", request.password(), "email", request.email());;
      try (var outputStream=http.getOutputStream()) {
        var jsonBody=new Gson().toJson(body);
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
}
