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
    public TeamColor getTeamTurn() {
        return isWhiteTurn;
//        if(isWhiteTurn){
//            TeamColor team = TeamColor.WHITE;
//        }
//        TeamColor team;
//        if(isWhiteTurn) {
//            team = TeamColor.WHITE;
//        }
//        else{
//            team = TeamColor.BLACK;
//        }
//        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.isWhiteTurn = team;
        //throw new RuntimeException("Not implemented");
        //team = getTeamTurn();
//        if(team != null && team == TeamColor.WHITE){
//            isWhiteTurn = false;
//        }
//        else{
//            isWhiteTurn = true;
//        }
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
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> MovesCollection = piece.pieceMoves(board, startPosition);
//        if(board.getPiece(startPosition) == null){
//            return null;
//        }
//        return MovesCollection;


        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
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
    public boolean isInCheck(TeamColor teamColor) {
        // if a future move in the valid moves ends with the space of the king being taken
        // then its in check
        ChessPosition kingPos = findKing(teamColor);
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves = possibleMovesCollector(teamColor);

        //find current possition of king and see if the other team has possible end possition on that position
        throw new RuntimeException("Not implemented");
    }

    public ArrayList<ChessMove> possibleMovesCollector(TeamColor teamColor){
        ArrayList<ChessMove>allPossibleMoves = new ArrayList<>();
        int i = 0;
        int j = 0;
        ChessPosition position = new ChessPosition(i,j);
        while(i < 7) {
            j = 0;
            while(j < 7){
                position=new ChessPosition(i, j);
                allPossibleMoves.addAll(board.getPiece(position).pieceMoves(board, position));
                j++;
            }
            i++;
        }

        //allPossibleMoves = piece.pieceMoves(board, position);
        return allPossibleMoves;
    }

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
    public boolean isInCheckmate(TeamColor teamColor) {
        //if it is in Check and there are no valid moves to save the king

        // if there is a validMove for the kings team to make then its not in check mate.

        // by moving the king
        // by capturing the troublesome piece
        // or by blocking

        // will have to reuse isInCheck to see if after each possible move if it is still in check

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
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
    public void setBoard(ChessBoard board) {
        this.board=board;

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}


