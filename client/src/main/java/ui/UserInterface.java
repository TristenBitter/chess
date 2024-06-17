package ui;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.*;
import websocket.commands.Connect;
import websocket.commands.Leave;
import websocket.commands.MakeMove;
import websocket.commands.Resign;

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

  public static void postLoginPrintStatements(){
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
    System.out.printf("join <ID> [ W|B ] ");
    System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    System.out.printf("--> to join a specific game, as a WHITE or BLACK player.%n");

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
  }

  public static void postLoginUI(AuthData data) throws Exception {
    while (true) {
      postLoginPrintStatements();
      System.out.printf("[LOGGED_IN]>>> ");
      var command = scanner(data, 0);
      String com=command[0];
      if (command[0].equals("help") || command[0].equals("Help") || command[0].equals("HELP")) {
        postLoginHelp(data);
      }
      if (com.equals("create")) {
        try {
          GameNameRequest gameNameRequest=new GameNameRequest(command[1]);
          CreateGameRequest gameID=facade.create(gameNameRequest, data.authToken());
          board.resetBoard();

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
          String playerColor ="";
          if(command[2].equals("w") || command[2].equals("white") || command[2].equals("W") || command[2].equals("WHITE")) {
            playerColor="WHITE";
          }
          else{
            playerColor="BLACK";
          }
          JoinGameRequest joinGameRequest=new JoinGameRequest(playerColor, gameIDs.get(Integer.parseInt(command[1])));
          boolean join=facade.join(joinGameRequest, data.authToken());
          if (join == false) {
            postLoginUI(data);
          } else {
            // call our gameMoves UI
            //connect
            WebSocketClient webSocketClient = new WebSocketClient();
            Connect connect = new Connect(data.authToken(), gameIDs.get(Integer.parseInt(command[1])));
            webSocketClient.send(new Gson().toJson(connect));

            gamePlayUI(data, gameIDs.get(Integer.parseInt(command[1])), playerColor);
          }
        } catch (Exception e) {
          System.out.println("oops that's not a valid join request, please try again. make sure to enter all the required fields");
          postLoginUI(data);
        }
      } else if (com.equals("observe")) {

        CreateGameRequest id=new CreateGameRequest(Integer.parseInt(command[1]));
        facade.observe(id, data.authToken());

        // join the game as an observer

        // print the board from both perspectives


      } else if (com.equals("logout")) {
        facade.logout(new LogoutRequest(data.authToken()), data.authToken());
        preLoginUI();
      } else {
        System.out.println("I'm sorry I didn't recognize that, please type HELP for help with commands%n[LOGGED_IN]>>> ");
      }
    }
  }

  public static void gamePlayPrintStatements(){
    System.out.printf("please type in a command from the list below%n");
    System.out.print("\u001b[36;100m");
    System.out.printf("display ");
    System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    System.out.printf("--> to display the game board.%n");

    System.out.print("\u001b[36;100m");
    System.out.printf("makeMove ");
    System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    System.out.printf("--> to make a move on the board.%n");

    System.out.print("\u001b[36;100m");
    System.out.printf("highlight ");
    System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    System.out.printf("--> to highlight legal moves of a certain piece.%n");

    System.out.print("\u001b[36;100m");
    System.out.printf("leave ");
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

  }

  public static void gamePlayUI(AuthData data, int gameID, String color) throws Exception {
    while (true) {
      gamePlayPrintStatements();
      System.out.printf("[JOINED_GAME]>>> ");
      String[] command = scanner(data, gameID);
      if (command[0].equals("help") || command[0].equals("Help") || command[0].equals("HELP")) {
        gamePlayHelp(data, gameID, color);
      }

      if(command[0].equals("display")){
        // print out the board for the color this player is
        if(color.equals("WHITE")){
          // print white chess board
          drawer.printWhiteBoard(board);
        }else{
          // print black chess board
          drawer.printBlackBoard(board);
        }
      }
      if(command[0].equals("makeMove")){

       ChessMove move = getMove(data,gameID, color);
        makeMove(data, gameID, color, move);
      }

      if(command[0].equals("highlight")){
        // delete later possibly
        WebSocketClient client = new WebSocketClient();
        client.webSocketClient();
          highlightMoveHandler(data, gameID, color);
      }
      if(command[0].equals("leave")){
        // take the player out of the game
        // leave this page
        try {
          WebSocketClient webSocketClient=new WebSocketClient();
          Leave leave=new Leave(data.authToken(), gameID);
          webSocketClient.send(new Gson().toJson(leave));

          postLoginUI(data);
        }catch (Exception e){
          System.out.println("oops, something went wrong when disconnection");
          postLoginUI(data);
        }
      }
      if(command[0].equals("resign")){
        //end the game
        try {
          WebSocketClient webSocketClient=new WebSocketClient();
          Resign resign=new Resign(data.authToken(), gameID);
          webSocketClient.send(new Gson().toJson(resign));

          postLoginUI(data);
        }catch (Exception e){
          System.out.println("oops, something went wrong while resigning");
          postLoginUI(data);
        }
      }
    }
  }

  public static int columChecker(String[] coordinate, String color) {
    int col = 0;
    if(color.equals("WHITE")){
      switch (coordinate[1]) {
        case "a": col = 1;
          break;
        case "b": col = 2;
          break;
        case "c": col = 3;
          break;
        case "d": col = 4;
          break;
        case "e": col = 5;
          break;
        case "f": col = 6;
          break;
        case "g": col = 7;
          break;
        case "h": col = 8;
          break;
        default: break;
      }
    }else{
      switch (coordinate[1]) {
        case "h": col = 1;
          break;
        case "g": col = 2;
          break;
        case "f": col = 3;
          break;
        case "e": col = 4;
          break;
        case "d": col = 5;
          break;
        case "c": col = 6;
          break;
        case "b": col = 7;
          break;
        case "a": col = 8;
          break;
        default: break;
      }
    }
    return col;
  }

  public static void highlightMoveHandler(AuthData data, int gameID, String color) throws Exception {
    // where would they like to see
    System.out.printf("What piece would you like to highlight?%n ");
    System.out.printf("[enter a coordinate, (example) >>> 2 a]  >>> ");
    String[] coordinate = scanner(data, gameID);

    try {
      int row=Integer.parseInt(coordinate[0]);
      int col=columChecker(coordinate, color);

      if(row <= 0 || col <= 0 || row >= 9 || col >= 9){
        System.out.println("oops, that doesn't look like a valid coordinate. please try again");
        gamePlayUI(data, gameID, color);
      }

      // call the proper function
      if (color.equals("WHITE")) {
        drawer.printHighlightedWhiteBoard(board, row, col);
      } else {
        drawer.printHighlightedBlackBoard(board, row, col);
      }
    }catch(Exception e){
      System.out.println("oops, please try again");
    }
  }

  public static ChessMove getMove(AuthData data, int gameID, String color) throws Exception {
    try {
      System.out.printf("What piece would you like to move?%n ");
      System.out.printf("[enter a coordinate, (example) >>> 2 a]  >>> ");
      String[] coordinate = scanner(data, gameID);


      int row=Integer.parseInt(coordinate[0]);
      int col=columChecker(coordinate, color);

      if(row <= 0 || col <= 0 || row >= 9 || col >= 9){
        System.out.println("oops, that doesn't look like a valid entry. please try again");
        gamePlayUI(data, gameID, color);
      }
      ChessPosition startPosition = new ChessPosition(row,col);

      System.out.printf("Where would you like to move it?%n ");
      System.out.printf("[enter a coordinate, (example) >>> 2 a]  >>> ");
      String[] coordinate2 = scanner(data, gameID);

      row=Integer.parseInt(coordinate[0]);
      col=columChecker(coordinate, color);

      if(row <= 0 || col <= 0 || row >= 9 || col >= 9) {
        System.out.println("oops, that doesn't look like a valid place to move. please try again");
        gamePlayUI(data, gameID, color);
      }
      ChessPosition endPosition = new ChessPosition(row,col);

      ChessMove move = new ChessMove(startPosition,endPosition, null);
      return move;

    }catch (Exception e) {
      System.out.println("oops, that doesn't look like a valid move. please try again");
    }
    return null;
  }

  public static void makeMove(AuthData data, int gameID, String color, ChessMove move) throws Exception {
    try {
      // need to figure out how to know what promotion piece when that becomes a possibility

      WebSocketClient webSocketClient = new WebSocketClient(8080);
      MakeMove makeMove = new MakeMove(data.authToken(), move,gameID);
      webSocketClient.send(new Gson().toJson(makeMove));

      //System.out.printf("The GameID for your new game named %s is: %d%n", command[1], gameID.gameID());
    } catch (Exception e) {
      System.out.println("oops, doesn't look like that's a valid move, please try again.");
      gamePlayUI(data, gameID, color);
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
    System.out.println("        Here's an example:%n[JOINED_GAME]>>> makeMove ");
    System.out.println("now you try");
    System.out.println("/*****************************************************************************************/");
    gamePlayUI(data, gameID, color);

  }
}
