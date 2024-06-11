package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
  private static ServerFacade facade = new ServerFacade(8080);
  public static void main(String[] args) throws Exception {
    while (true) {
      System.out.printf("Welcome to my CS240 Chess app! Type help to get started%n[LOGGED_OUT]>>> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();
      if(line.equals("help") || line.equals("Help") || line.equals("HELP")){
        //Enter preLogin section of UI
        preLoginUI();
        break;
      }
      else{
        System.out.println("I'm sorry I didn't recognize that, please type HELP to get started%n[LOGGED_OUT]>>> ");
      }
    }
  }

  public static void preLoginHelp() throws Exception {
    System.out.println("I am here to help you... do not fear...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_OUT]>>> register BobDiddleBob frogs123 frogYeeter27@gmail.com");
    System.out.println("now you try");
    preLoginUI();
  }
  public static void preLoginUI() throws Exception {
    while (true) {
      System.out.printf("Please type in one of these commands%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("register <USERNAME> <PASSWORD> <EMAIL> ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to create an account.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("login <USERNAME> <PASSWORD> ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to login to your account.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("quit ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to exit.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("help ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> for help with commands.%n%n");

      System.out.printf("[LOGGED_OUT]>>> ");
      Scanner scanner=new Scanner(System.in);
      String line=scanner.nextLine();
      String[] userInput = line.split(" ");

      if(line.equals("quit")){
        break;
      }
      if(line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        // display help stuff
        preLoginHelp();
      }

        String command = userInput[0];

        if(command.equals("register")){
          //call register to put in the credentials
          // somehow extract the credentials from the string
          try {
            RegisterRequest registerRequest=new RegisterRequest(userInput[1], userInput[2], userInput[3]);
            AuthData data=facade.register(registerRequest);
            if(data == null){
              preLoginUI();
            }
            System.out.println("REGISTER SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            postLoginUI(data);
            break;
          }catch (Exception e){
            System.out.println("oops that's not a valid registration, please enter all valid fields");
            preLoginUI();
          }
        }
        else if(command.equals("login")){
          //login the user with the credentials
          try {
            LoginRequest loginRequest=new LoginRequest(userInput[1], userInput[2]);
            AuthData data=facade.login(loginRequest);
            if(data == null){
              preLoginUI();
            }
            System.out.println("LOGIN SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            postLoginUI(data);
            break;
          }catch (Exception e){
            System.out.println("oops that's not a valid login, please try again");
            preLoginUI();
          }
        }
        else{
          System.out.println("I'm sorry, I did not recognize that command. Please try again or if you need help type help.");
        }
    }
  }

  public static void postLoginHelp(AuthData data) throws Exception {
    System.out.println("Do not worry...I am here to help you...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters for the command and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_IN]>>> create BobbyVsTimmy");
    System.out.println("now you try");
    postLoginUI(data);
  }
  public static void postLoginUI(AuthData data) throws Exception{
    while (true) {
      System.out.printf("please type in a command from the list provided below%n");
      System.out.print("\u001b[36;100m");
      System.out.printf("create <NAME> ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to create a new game and name it.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("list ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to list games.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("join <ID> [WHITE | BLACK] ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to join a game.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("observe <ID> ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to see a game.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("logout ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to logout of this account.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("quit ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to exit this page.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("help ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> for help with commands.%n%n");

      System.out.println("[LOGGED_IN]>>> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();
      var command = line.split(" ");
      if (line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        postLoginHelp(data);
        break;
      }
      String com = command[0];
        if (com.equals("create")) {
          try {
            GameNameRequest gameNameRequest=new GameNameRequest(command[1]);
            CreateGameRequest gameID=facade.create(gameNameRequest, data.authToken());
            if(data == null){
              preLoginUI();
            }
            System.out.printf("The GameID for your new game named %s is: %d%n", command[1], gameID.gameID());
          }catch (Exception e){
            System.out.println("oops, please try again. make sure to enter one name after the game");
            postLoginUI(data);
          }
        } else if (com.equals("list")) {
          try {
            ArrayList<ListGamesRequest> listOfGames=facade.list(data.authToken());
            int i=0;
            for (ListGamesRequest game : listOfGames) {
              i++;
              System.out.printf("%d. GameName: %s, GameID: %d, WhitePlayer: %s, BlackPlayer: %s", i, game.gameName(), game.gameID(), game.whiteUsername(), game.blackUsername());
              System.out.printf("%n");
            }
          }catch (Exception e) {
            System.out.println("oops, please try again");
            postLoginUI(data);
          }
        } else if (com.equals("join")) {
          try {
            JoinGameRequest joinGameRequest=new JoinGameRequest(command[2], Integer.parseInt(command[1]));
            boolean join = facade.join(joinGameRequest, data.authToken());
            if(join == false){
              postLoginUI(data);
            }else {
              // call our gameMoves UI
              postLoginUI(data);
            }
            break;
          }catch (Exception e) {
            System.out.println("oops that's not a valid join request, please try again. make sure to enter all the required fields");
            postLoginUI(data);
          }
        }else if (com.equals("observe")) {
            CreateGameRequest id=new CreateGameRequest(Integer.parseInt(command[1]));
            facade.observe(id, data.authToken());
        }else if (com.equals("logout")) {
          facade.logout(new LogoutRequest(data.authToken()), data.authToken());
          preLoginUI();
        } else {
          System.out.println("I'm sorry I didn't recognize that, please type HELP for help with commands%n[LOGGED_IN]>>> ");
        }
    }
  }
}
