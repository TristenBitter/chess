package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessBoardDrawer {

  private static final int BOARD_SIZE_IN_SQUARES = 3;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final String EMPTY = "   ";
  private static final String X = " X ";
  private static final String O = " O ";
  private static Random rand = new Random();

  private static ChessBoard board = new ChessBoard();


  public static void main(String[] args) {

    // delete this later
    board.resetBoard();

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

    out.println();

    whiteBoardHeader(out);

    // print white board


    for(int i = 8; i > 0; i--){
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      for(int j = 1; j < 9; j++){
        if((i + j)%2 == 0){
          out.print(SET_BG_COLOR_BLACK);
        }
        else {
          out.print(SET_BG_COLOR_WHITE);
        }

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
      out.print(SET_BG_COLOR_DARK_GREY);
      out.print(SET_TEXT_COLOR_WHITE);
      out.printf(" %d ",i);
      setBlack(out);
      out.println();
    }

    whiteBoardHeader(out);



  }

  public ChessPosition getPosition(int row, int col){
    ChessPosition position = new ChessPosition(row, col);
    return position;
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





//
//    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//
//    out.print(ERASE_SCREEN);
//
//    drawHeaders(out);
//
//    drawChessBoard(out);
//
//    out.print(SET_BG_COLOR_BLACK);
//    out.print(SET_TEXT_COLOR_WHITE);
//  }
//
//  private static void drawHeaders(PrintStream out) {
//
//    setBlack(out);
//
//    String[] headers = { "MY", "CHESS", "APP" };
//    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//      drawHeader(out, headers[boardCol]);
//
//      if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//      }
//    }
//
//    out.println();
//  }
//
//  private static void drawHeader(PrintStream out, String headerText) {
//    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
//    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
//
//    out.print(EMPTY.repeat(prefixLength));
//    printHeaderText(out, headerText);
//    out.print(EMPTY.repeat(suffixLength));
//  }
//
//  private static void printHeaderText(PrintStream out, String player) {
//    out.print(SET_BG_COLOR_BLACK);
//    out.print(SET_TEXT_COLOR_GREEN);
//
//    out.print(player);
//
//    setBlack(out);
//  }
//
//  private static void drawChessBoard(PrintStream out) {
//
//    for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
//
//      drawRowOfSquares(out);
//
//      if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//        drawVerticalLine(out);
//        setBlack(out);
//      }
//    }
//  }
//
//  private static void drawRowOfSquares(PrintStream out) {
//
//    for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
//      for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//        setWhite(out);
//
//        if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
//          int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
//          int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
//
//          out.print(EMPTY.repeat(prefixLength));
//          printPlayer(out, rand.nextBoolean() ? X : O);
//          out.print(EMPTY.repeat(suffixLength));
//        }
//        else {
//          out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//        }
//
//        if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//          // Draw right line
//          setRed(out);
//          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//        }
//
//        setBlack(out);
//      }
//
//      out.println();
//    }
//  }
//
//  private static void drawVerticalLine(PrintStream out) {
//
//    int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
//            (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;
//
//    for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
//      setRed(out);
//      out.print(EMPTY.repeat(boardSizeInSpaces));
//
//      setBlack(out);
//      out.println();
//    }
//  }
//
//  private static void setWhite(PrintStream out) {
//    out.print(SET_BG_COLOR_WHITE);
//    out.print(SET_TEXT_COLOR_WHITE);
//  }
//
//  private static void setRed(PrintStream out) {
//    out.print(SET_BG_COLOR_RED);
//    out.print(SET_TEXT_COLOR_RED);
//  }
//
  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    //out.print(SET_TEXT_COLOR_BLACK);
  }
//
//  private static void printPlayer(PrintStream out, String player) {
//    out.print(SET_BG_COLOR_WHITE);
//    out.print(SET_TEXT_COLOR_BLACK);
//
//    out.print(player);
//
//    setWhite(out);
//  }

}
