package ui;

import model.*;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ServerFacade {
    public AuthData register(RegisterRequest request) throws Exception{

      return null;
    }
    public AuthData login(LoginRequest loginRequest) throws Exception{

      return  null;
    }
    public int create(GameNameRequest gameNameRequest, String authToken) {

      return 0;
  }

  public ListGamesRequest list(String authToken){


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
