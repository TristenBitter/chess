package ui;

import java.util.Scanner;

public class UserInterface {
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

  public static void preLoginHelp(){
    System.out.println("I am here to help you... do not fear...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_OUT]>>> register BobDiddleBob frogs123 frogYeeter27@gmail.com");
    System.out.println("now you try");
    preLoginUI();
  }
  public static void preLoginUI(){
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
      String[] numbers = line.split(" ");

      if(line.equals("quit")){
        break;
      }
      if(line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        // display help stuff
        preLoginHelp();
      }

      for (var number : numbers) {
        if(number.equals("register")){
          //call register to put in the credentials
          // somehow extract the credentials from the string
          System.out.println("REGISTER SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          postLoginUI();
          break;
        }
        else if(number.equals("login")){
          //login the user with the credentials
          System.out.println("LOGIN SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          postLoginUI();
          break;
        }
        else{
          System.out.println("I'm sorry, I did not recognize that command. Please try again or if you need help type help.");
        }
      }
    }
  }

  public static void postLoginHelp(){
    System.out.println("Do not worry...I am here to help you...");
    System.out.println("try typing one of the commands that are in blue");
    System.out.println("make sure to use all lowercase letters for the command and fill in all the required information");
    System.out.println("        Here's an example:%n[LOGGED_IN]>>> create BobbyVsTimmy");
    System.out.println("now you try");
    postLoginUI();
  }
  public static void postLoginUI(){
    while (true) {
      System.out.printf("please type in a command%n[LOGGED_IN]>>> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();
      var command = line.split(" ");

      if (line.equals("help") || line.equals("Help") || line.equals("HELP")) {
        //Enter preLogin section of UI
        postLoginHelp();
        break;
      }

      for (var com : command) {

        if (com.equals("create")) {
          //call register to put in the credentials
          // somehow extract the credentials from the string
          System.out.println("CREATION SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          postLoginUI();
          break;
        } else if (com.equals("join")) {
          //login the user with the credentials
          System.out.println("LOGIN SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          postLoginUI();
          break;
        } else {
          System.out.println("I'm sorry I didn't recognize that, please type HELP for help with commands%n[LOGGED_IN]>>> ");
        }
      }
    }
  }
}
