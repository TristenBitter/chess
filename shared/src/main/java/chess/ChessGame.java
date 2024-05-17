package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();

    private ChessPiece.PieceType type;
    private ChessPiece piece = new ChessPiece(getTeamTurn(), type);

    //ChessMove

    // used to tell whose turn it is
    //private boolean isWhiteTurn = true;

    private TeamColor isWhiteTurn = TeamColor.WHITE;
    public ChessGame() {


    }



    /**
     * @return Which team's turn it is
     */

    /***********************************************************************************************
     *                               GET WHICH TEAM'S TURN IT IS
     ***********************************************************************************************/
    public TeamColor getTeamTurn() {
        return isWhiteTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */

    /***********************************************************************************************
     *                               SET THE TEAM'S TURN
     ***********************************************************************************************/
    public void setTeamTurn(TeamColor team) {
        this.isWhiteTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */

    /***********************************************************************************************
     *                                    VALID MOVES
     ***********************************************************************************************/
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);

        // check to see if there is a move that put the king in to check.
        // check to see if the king is being moved into check

        //if you are in check you must get yourself out of check
        // make a copy and call makeMoves to test out a possible solution to get you out of check.

       return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */

    /***********************************************************************************************
     *                                       MAKE MOVE
     ***********************************************************************************************/
    public void makeMove(ChessMove move){
        // make a copy of the chess board
        // do the turn switches when calling set turn
        ChessBoard copyOfBoard=new ChessBoard(board);


            //throws InvalidMoveException {
        //throw new RuntimeException("Move not valid");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    /***********************************************************************************************
     *                              THE OLD IS IN CHECK CHECK
     ***********************************************************************************************/
    public boolean isInCheck(TeamColor teamColor) {
        //find current position of king and see if the other team has possible end possition on that position
        // if a future move in the valid moves ends with the space of the king being taken
        // then its in check
        ChessPosition kingPos = findKing(teamColor);
        ArrayList<ChessMove> possibleMoves = possibleMovesCollector(teamColor);

        //if kingPos is in possible moves then return true, else false
        if(possibleMoves.contains(kingPos)){
            return true;
        }
        else{
            return false;
        }
    }

    /***********************************************************************************************
     *                               POSSIBLE MOVES COLLECTOR
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleMovesCollector(TeamColor teamColor){
        ArrayList<ChessMove>allPossibleMoves = new ArrayList<>();
        int i = 0;
        int j = 0;
        ChessPosition position = new ChessPosition(i,j);
        while(i < 7) {
            j = 0;
            while(j < 7){
                position=new ChessPosition(i, j);
                if(board.getPiece(position).getTeamColor() != teamColor) {
                    allPossibleMoves.addAll(board.getPiece(position).pieceMoves(board, position));
                }
                j++;
            }
            i++;
        }

        //allPossibleMoves = piece.pieceMoves(board, position);
        return allPossibleMoves;
    }

    /***********************************************************************************************
     *                               WHERE'D THAT KING GO?
     ***********************************************************************************************/
    public ChessPosition findKing(TeamColor teamColor){
        ChessPosition kingPosition = new ChessPosition(0,0);
        int i = 0;
        while(i < 7) {
            int j = 0;
            while(j < 7){
                kingPosition=new ChessPosition(i, j);
                if (board.getPiece(kingPosition).getPieceType() == ChessPiece.PieceType.KING) {
                    i += 7;
                    break;
                }
                j++;
            }
            i++;
        }

       return kingPosition;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */

    /***********************************************************************************************
     *                                     ARE WE DONE YET
     ***********************************************************************************************/
    public boolean isInCheckmate(TeamColor teamColor) {
        //if it is in Check and there are no valid moves to save the king
        ArrayList<ChessMove> possibleMoves;
        if(teamColor == TeamColor.WHITE) {
            possibleMoves=possibleMovesCollector(TeamColor.BLACK);
        }
        else{
            possibleMoves=possibleMovesCollector(TeamColor.WHITE);
        }

        if(isInCheck(teamColor)){
            // and if there are no valid moves to get it out of check
            // && validMoves(findKing(teamColor))
        }
        // if there is a validMove for the kings team to make then its not in check mate.

        // by moving the king
        // by capturing the troublesome piece
        // or by blocking

        // will have to reuse isInCheck to see if after each possible move if it is still in check

        //throw new RuntimeException("Not implemented");
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */

    /***********************************************************************************************
     *                               I'M NOT SURE HOW I GOT HERE
     ***********************************************************************************************/
    public boolean isInStalemate(TeamColor teamColor) {
        // not currently in check
        // and the number of possible moves is zero.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */


    /***********************************************************************************************
     *                                       SET BOARD
     ***********************************************************************************************/
    public void setBoard(ChessBoard board) {
        this.board=board;

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */

    /***********************************************************************************************
     *                                       GET BOARD
     ***********************************************************************************************/
    public ChessBoard getBoard() {
        return board;
    }
}


