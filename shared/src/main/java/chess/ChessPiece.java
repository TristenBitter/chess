package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;



    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor=pieceColor;
        this.type=type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {

        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        switch(type){
            case KING:
                MovesCollection = possibleKingMoves(board, myPosition);
                break;
            case QUEEN:
                MovesCollection = possibleQueenMoves(board, myPosition);
                break;
            case KNIGHT:
                MovesCollection = possibleKnightMoves(board, myPosition);
                break;
            case BISHOP:
                MovesCollection = possibleBishopMoves(board, myPosition);
                break;
            case ROOK:
                MovesCollection = possibleRookMoves(board, myPosition);
                break;
            case PAWN:
                MovesCollection = possiblePawnMoves(board, myPosition);
                break;
        }

        return MovesCollection;
    }
    public ArrayList<ChessMove> possibleKingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
//        while(true){
//
//
//            break;
//        }


        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleQueenMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleKnightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleBishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();


        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleRookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int i = 1;
        // below the rook
        while(row - i > 0){   // make this while true

            if((row - i) < 0){
                break;
            }
            if((col - i) < 0){
                break;
            }
            ChessPosition newPos = new ChessPosition(row - i, col);
            //if the space is empty its good to go.
            if((board.getPiece(newPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
            if ((board.getPiece(newPos)) != null && board.getPiece(newPos).getTeamColor() != pieceColor){
                // if the piece to the left is not your own its a possible move
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
                // add it to our collection of possible moves.
                break;
            }
            if ((board.getPiece(newPos)) != null && board.getPiece(newPos).getTeamColor() == pieceColor){
                // our team mate's piece is blocking this way
                break;
            }
            i++;
        }

        return MovesCollection;
    }

    public ArrayList<ChessMove> possiblePawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        return MovesCollection;
    }
}

//      if((row - i) < 0){
//        break;
//        }
//        if((col - i) < 0){
//        break;
//        }
