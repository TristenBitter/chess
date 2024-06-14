package ui;

import chess.ChessBoard;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
  private static ServerFacade facade=new ServerFacade(8080);
  private static Map<Integer, Integer> gameIDs=new HashMap<>();

  private static ChessBoardDrawer drawer = new ChessBoardDrawer();

  private static ChessBoard board = new ChessBoard();

  public static void main(String[] args) throws Exception {
    while (true) {
      System.out.printf("Welcome to my CS240 Chess app! Type help to get started%n[LOGGED_OUT]>>> ");
      Scanner scanner=new Scanner(System.in);
      String line=scanner.nextLine();
      if (line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        //Enter preLogin section of UI
        preLoginUI();
        break;
      } else {
        System.out.println("I'm sorry I didn't recognize that, please type HELP to get started%n[LOGGED_OUT]>>> ");
      }
    }
  }

  public static void preLoginHelp() throws Exception {
    System.out.println("/*****************************************************************************************/");
    System.out.println("I am here to help you... do not fear...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_OUT]>>> register BobDiddleBob frogs123 frogYeeter27@gmail.com");
    System.out.println("now you try");
    System.out.println("/*****************************************************************************************/");
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
      String[] userInput=line.split(" ");

      if (line.equals("quit")) {
        break;
      }
      if (line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        // display help stuff
        preLoginHelp();
      }

      String command=userInput[0];

      if (command.equals("register")) {
        //call register to put in the credentials
        // somehow extract the credentials from the string
        try {
          RegisterRequest registerRequest=new RegisterRequest(userInput[1], userInput[2], userInput[3]);
          AuthData data=facade.register(registerRequest);
          if (data == null) {
            preLoginUI();
          }
          System.out.println("REGISTER SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

          postLoginUI(data);
          break;
        } catch (Exception e) {
          System.out.println("oops that's not a valid registration, please enter all valid fields");
          preLoginUI();
        }
      } else if (command.equals("login")) {
        //login the user with the credentials
        try {
          LoginRequest loginRequest=new LoginRequest(userInput[1], userInput[2]);
          AuthData data=facade.login(loginRequest);
          if (data == null) {
            preLoginUI();
          }
          System.out.println("LOGIN SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          postLoginUI(data);
          break;
        } catch (Exception e) {
          System.out.println("oops that's not a valid login, please try again");
          preLoginUI();
        }
      } else {
        System.out.println("I'm sorry, I did not recognize that command. Please try again or if you need help type help.");
      }
    }
  }

  public static void postLoginHelp(AuthData data) throws Exception {
    System.out.println("/*****************************************************************************************/");
    System.out.println("Do not worry...I am here to help you...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters for the command and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_IN]>>> create BobbyVsTimmy");
    System.out.println("now you try");
    System.out.println("/*****************************************************************************************/");
    postLoginUI(data);
  }

  public static void postLoginUI(AuthData data) throws Exception {
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
      System.out.printf("help ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> for help with commands.%n%n");

      System.out.println("[LOGGED_IN]>>> ");
      var command = scanner(data, 0);
      String com=command[0];
      if (command[0].equals("help") || command[0].equals("Help") || command[0].equals("HELP")) {
        postLoginHelp(data);
      }
      if (com.equals("create")) {
        try {
          GameNameRequest gameNameRequest=new GameNameRequest(command[1]);
          CreateGameRequest gameID=facade.create(gameNameRequest, data.authToken());

          if (data == null) {
            preLoginUI();
          }
          System.out.printf("The GameID for your new game named %s is: %d%n", command[1], gameID.gameID());
        } catch (Exception e) {
          System.out.println("oops, please try again. make sure to enter one name after the game");
          postLoginUI(data);
        }
      } else if (com.equals("list")) {
        try {
          ArrayList<ListGamesRequest> listOfGames=facade.list(data.authToken());
          int i=0;

          for (ListGamesRequest game : listOfGames) {
            i++;
            gameIDs.put(i, game.gameID());
            System.out.printf("%d. GameName: %s, WhitePlayer: %s, BlackPlayer: %s", i, game.gameName(), game.whiteUsername(), game.blackUsername());
            System.out.printf("%n");
          }
        } catch (Exception e) {
          System.out.println("oops, please try again");
          postLoginUI(data);
        }
      } else if (com.equals("join")) {
        try {
          JoinGameRequest joinGameRequest=new JoinGameRequest(command[2], gameIDs.get(Integer.parseInt(command[1])));
          boolean join=facade.join(joinGameRequest, data.authToken());
          if (join == false) {
            postLoginUI(data);
          } else {
            // call our gameMoves UI
            //connect
            new WebSocketClient(8080);
            gamePlayUI(data, gameIDs.get(Integer.parseInt(command[1])), command[2]);
          }
          break;
        } catch (Exception e) {
          System.out.println("oops that's not a valid join request, please try again. make sure to enter all the required fields");
          postLoginUI(data);
        }
      } else if (com.equals("observe")) {

        CreateGameRequest id=new CreateGameRequest(Integer.parseInt(command[1]));
        facade.observe(id, data.authToken());
      } else if (com.equals("logout")) {
        facade.logout(new LogoutRequest(data.authToken()), data.authToken());
        preLoginUI();
      } else {
        System.out.println("I'm sorry I didn't recognize that, please type HELP for help with commands%n[LOGGED_IN]>>> ");
      }
    }
  }

  public static void gamePlayUI(AuthData data, int gameID, String color) throws Exception {
    while (true) {
      System.out.printf("please type in a command from the list below%n");
      System.out.print("\u001b[36;100m");
      System.out.printf("display ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to display the game board.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("makeMove <coordinates of piece you want moved> <destination coordinates>");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to make a move on the board.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("highlight <coordinates of piece>");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to highlight legal moves of a certain piece.%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("leave");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to leave a game (so you or someone else can finish it later).%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("resign ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> to forfeit the match .%n");

      System.out.print("\u001b[36;100m");
      System.out.printf("help ");
      System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
      System.out.printf("--> for help with these commands.%n%n");

      System.out.println("[JOINED_GAME]>>> ");
      String[] command = scanner(data, gameID);
      if (command[0].equals("help") || command[0].equals("Help") || command[0].equals("HELP")) {
        gamePlayHelp(data, gameID, color);
      }

      if(command[0].equals("display")){
        // print out the board for the color this player is
        if(color.equals("WHITE")){
          // print white chess board
          drawer.printWhiteBoard();
        }else{
          // print black chess board
          drawer.printBlackBoard();
        }

      }
      if(command[0].equals("makeMove")){
        try {

         // MoveRequest move=new MoveRequest(command[1]);
          //CreateGameRequest gameID=facade.create(gameNameRequest, data.authToken());

          //

          //System.out.printf("The GameID for your new game named %s is: %d%n", command[1], gameID.gameID());
        } catch (Exception e) {
          System.out.println("oops, doesn't look like that's a valid move, please try again.");
          gamePlayUI(data, gameID, color);
        }

      }
      if(command[0].equals("highlight")){
        //check the possible moves of the given piece
        //this should be fun

        //

      }
      if(command[0].equals("leave")){
        // take the player out of the game
        // leave this page


      }
      if(command[0].equals("resign")){
        //end the game
        //prevent future moves
        // delete the game from the list of games in the database
        //leave this page

      }

      /// handle the notification
      // when we get a load game message print it out and update the local chess board

    }

  }
  public static String[] scanner(AuthData data, int gameID) throws Exception {
    Scanner scanner=new Scanner(System.in);
    String line=scanner.nextLine();
    var command=line.split(" ");
    return command;
  }
  public static void gamePlayHelp(AuthData data, int gameID, String color) throws Exception {
    System.out.println("/*****************************************************************************************/");
    System.out.println("Do not worry...I am here to help...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to check for proper casing and fill in all the required information");
    System.out.println("        Here's an example:%n[JOINED_GAME]>>> makeMove a2 a4");
    System.out.println("now you try");
    System.out.println("/*****************************************************************************************/");
    gamePlayUI(data, gameID, color);

  }
}
