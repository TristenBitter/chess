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
        throw new RuntimeException("Not implemented");
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
                possibleKingMoves(board, myPosition);
                break;
            case QUEEN:
                possibleQueenMoves(board, myPosition);
                break;
            case KNIGHT:
                possibleKnightMoves(board, myPosition);
                break;
            case BISHOP:
                possibleBishopMoves(board, myPosition);
                break;
            case ROOK:
                possibleRookMoves(board, myPosition);
                break;
            case PAWN:
                possiblePawnMoves(board, myPosition);
                break;

        }

        return MovesCollection;
    }
    public ChessPiece possibleKingMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public ChessPiece possibleQueenMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public ChessPiece possibleKnightMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public ChessPiece possibleBishopMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public ChessPiece possibleRookMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public ChessPiece possiblePawnMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
