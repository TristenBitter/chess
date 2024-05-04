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
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
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
        while(row - i > 0){
            ChessPosition newPos = new ChessPosition(row - i, col);
            if ((board.getPiece(newPos) != null) && board.getPiece(newPos).getTeamColor() =! pieceColor){
                // if the piece to the left is not your own   its a possible move
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
                // add it to our collection of possible moves.
            }
        }

        return MovesCollection;
    }

    public ArrayList<ChessMove> possiblePawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        return MovesCollection;
    }
}
