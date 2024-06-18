import chess.*;
import ui.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ 240 Chess Client: ");
        UserInterface userInterface = new UserInterface();
        try {
            while (true) {
                System.out.printf("Welcome to my CS240 Chess app! Type help to get started%n[LOGGED_OUT]>>> ");
                Scanner scanner=new Scanner(System.in);
                String line=scanner.nextLine();
                if (line.equals("help") || line.equals("Help") || line.equals("HELP")) {
                    //Enter preLogin section of UI
                    userInterface.preLoginUI();
                    break;
                } else {
                    System.out.println("I'm sorry I didn't recognize that, please type HELP to get started%n[LOGGED_OUT]>>> ");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}