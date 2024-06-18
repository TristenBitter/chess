package ui;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessBoardDrawer {

  private static final int BOARD_SIZE_IN_SQUARES = 3;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final String EMPTY = "   ";
  private static ChessBoard board;


  public static void main(String[] args) {

    // delete this later
    board.resetBoard();

    PrintStream out = setUpScreen();

    out.println();
//    whiteBoardDrawer(out);
//    blackBoardDrawer(out);
    highlightWhiteBoard(out, 1,2);
    highlightBlackBoard(out, 1,2);


  }

  public void printWhiteBoard(ChessBoard board){
    this.board = board;
    PrintStream out = setUpScreen();
    out.println();
    whiteBoardDrawer(out);
  }
  public void printBlackBoard(ChessBoard board){
    this.board = board;
    PrintStream out = setUpScreen();
    out.println();
    blackBoardDrawer(out);
  }
  public void printHighlightedWhiteBoard( ChessBoard board ,int row, int col){
    this.board = board;
    PrintStream out = setUpScreen();
    out.println();
    highlightWhiteBoard(out,row, col);
  }
  public void printHighlightedBlackBoard(ChessBoard board, int row, int col){
    this.board = board;
    PrintStream out = setUpScreen();
    out.println();
    highlightBlackBoard(out,row, col);
  }

  public static PrintStream setUpScreen(){
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    out.print(ERASE_SCREEN);
    setBlack(out);

    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {

      int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

      out.print(EMPTY.repeat(prefixLength));
      out.print(EMPTY.repeat(suffixLength));

      if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
      }
    }
    return out;
  }

  public static void whiteBoardDrawer(PrintStream out){
    whiteBoardHeader(out);

    // print white board


    for(int i = 8; i > 0; i--){
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      for(int j = 1; j < 9; j++){
        setTileColor(i, j, out);
        setPiece(i, j, out);
      }
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      setBlack(out);
      out.println();
    }

    whiteBoardHeader(out);
    setBlack(out);
    out.println();

  }

  public static void blackBoardDrawer(PrintStream out){
    blackBoardHeader(out);

    for(int i = 1; i < 9; i++){
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      for(int j = 8; j > 0; j--){
        setTileColor(i, j, out);
        setPiece(i, j, out);

      }
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      setBlack(out);
      out.println();
    }

    blackBoardHeader(out);
    setBlack(out);
    out.println();

  }

  public static void setTileColor(int row, int col, PrintStream out){
    if((row + col)%2 == 0){
      out.print(SET_BG_COLOR_BLACK);
    }
    else {
      out.print(SET_BG_COLOR_WHITE);
    }
  }

  public static void setPiece(int i, int j, PrintStream out){
    ChessPiece piece = board.getPiece(new ChessPosition(i,j));
    if(piece != null) {
      if(piece.getTeamColor().toString().equals("WHITE")) {
        out.print(SET_TEXT_COLOR_RED);
      }
      else{
        out.print(SET_TEXT_COLOR_BLUE);
      }
      out.print(piece.toString());
    }
    else{
      out.print("   ");
    }
  }

  public static void whiteBoardHeader(PrintStream out){
    //White players board
    out.print(SET_BG_COLOR_DARK_GREY);
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    out.print(EMPTY.repeat(prefixLength));
    out.print(SET_TEXT_COLOR_WHITE);
    out.print(" a ");
    out.print(" b ");
    out.print(" c ");
    out.print(" d ");
    out.print(" e ");
    out.print(" f ");
    out.print(" g ");
    out.print(" h    ");
    setBlack(out);
    out.println();
  }

  public static void blackBoardHeader(PrintStream out){
    //Black players board
    out.print(SET_BG_COLOR_DARK_GREY);
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    out.print(EMPTY.repeat(prefixLength));
    out.print(SET_TEXT_COLOR_WHITE);
    out.print(" h ");
    out.print(" g ");
    out.print(" f ");
    out.print(" e ");
    out.print(" d ");
    out.print(" c ");
    out.print(" b ");
    out.print(" a    ");
    setBlack(out);
    out.println();
  }

  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
  }



  private static void highlightWhiteBoard(PrintStream out, int row, int col){
    whiteBoardHeader(out);

    // print white board

    for(int i = 8; i > 0; i--){
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      for(int j = 1; j < 9; j++){
        highlightHandler(out,row,col,i,j);
      }
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      setBlack(out);
      out.println();
    }

    whiteBoardHeader(out);
    setBlack(out);
    out.println();
  }

  public static void setHighlightTileColor(int row, int col, PrintStream out){
    if((row + col)%2 == 0){
      out.print(SET_BG_COLOR_DARK_GREEN);
    }
    else {
      out.print(SET_BG_COLOR_GREEN);
    }
  }

  public static void setHighlightedPiece(PrintStream out, int row, int col){
    ChessPiece piece = board.getPiece(new ChessPosition(row,col));
    if(piece != null) {
      out.print(SET_TEXT_COLOR_BLACK);
      out.print(piece.toString());
    }else{
      out.print("   ");
    }
  }

  public static boolean setHighlightedSpots(PrintStream out, int i, int j, int row, int col){
    ChessPiece piece = board.getPiece(new ChessPosition(row,col));
    if(piece != null) {
      ArrayList<ChessMove> listOfMoves=piece.pieceMoves(board, new ChessPosition(row, col));
      for (ChessMove move : listOfMoves) {
        if (move.getEndPosition().equals(new ChessPosition(i, j))) {
          setHighlightTileColor(i, j, out);
          return true;
        }
      }
    }
    return false;
  }

  private static void highlightBlackBoard(PrintStream out, int row, int col){
    blackBoardHeader(out);

    // print white board

    for(int i = 1; i < 9; i++){
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      for(int j = 8; j > 0; j--){
        highlightHandler(out, row, col, i, j);
      }
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      setBlack(out);
      out.println();
    }

    blackBoardHeader(out);
    setBlack(out);
    out.println();
  }

  public static void highlightHandler(PrintStream out, int row, int col, int i, int j){
    if(row == i && col == j) {
      out.print(SET_BG_COLOR_YELLOW);
      setHighlightedPiece(out,row,col);
    }else if(setHighlightedSpots(out, i, j, row, col)){
      setHighlightedPiece(out,i,j);
    }
    else{
      setTileColor(i, j, out);
      setPiece(i, j, out);
    }
  }


}
