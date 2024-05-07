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
        while((row - i) > 0){

//            if((row - i) < 0){
//                break;
//            }
//            if((col - i) < 0){
//                break;
//            }
//            if((row + i) > 8){
//                break;
//            }
//            if((col + i) > 8){
//                break;
//            }

            ChessPosition downPos = new ChessPosition(row - i, col);
            //if the space is empty its good to go.
            if((board.getPiece(downPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, downPos, null));
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() != pieceColor){
                // if the piece to the left is not your own its a possible move
                MovesCollection.add(new ChessMove(myPosition, downPos, null));
                // add it to our collection of possible moves.
                break;
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() == pieceColor){
                // our team mate's piece is blocking this way
                break;
            }
            i++;
        }
        // above the Rook
        while((row + i) < 9){
            ChessPosition upPos = new ChessPosition(row + i, col);
            if((board.getPiece(upPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, upPos, null));
            }
            if ((board.getPiece(upPos)) != null && board.getPiece(upPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, upPos, null));
                break;
            }
            if ((board.getPiece(upPos)) != null && board.getPiece(upPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        // left of the Rook
        while((col - i) > 0 ){
            ChessPosition leftPos = new ChessPosition(row, col - i);
            if((board.getPiece(leftPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, leftPos, null));
            }
            if ((board.getPiece(leftPos)) != null && board.getPiece(leftPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, leftPos, null));
                break;
            }
            if ((board.getPiece(leftPos)) != null && board.getPiece(leftPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        // right of the Rook
        while((col + i) < 9){
            ChessPosition rightPos = new ChessPosition(row, col + i);
            if((board.getPiece(rightPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, rightPos, null));
            }
            if ((board.getPiece(rightPos)) != null && board.getPiece(rightPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, rightPos, null));
                break;
            }
            if ((board.getPiece(rightPos)) != null && board.getPiece(rightPos).getTeamColor() == pieceColor){
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
