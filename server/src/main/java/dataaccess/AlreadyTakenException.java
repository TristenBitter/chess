package dataaccess;

public class AlreadyTakenException extends Exception{
  AlreadyTakenException(String message) {
    super(message);
  }
}
