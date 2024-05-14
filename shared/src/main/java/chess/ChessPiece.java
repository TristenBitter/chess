package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
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
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ChessPosition newPos = new ChessPosition(row + 1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row + 1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row - 1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row -1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row - 1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }
        newPos = new ChessPosition(row + 1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)){
            if(moveValidator( board,newPos)){
                MovesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }

        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleQueenMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        MovesCollection = possibleBishopMoves(board, myPosition);
        MovesCollection.addAll(possibleRookMoves(board,myPosition));
        return MovesCollection;
    }

    public ArrayList<ChessMove> possibleKnightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition = new ChessPosition(row, col);

        if(((row - 2) > 0) && ((col + 1) < 9)) {
            newPosition = new ChessPosition(row - 2, col + 1);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row - 2) > 0) && ((col - 1) > 0)) {
            newPosition = new ChessPosition(row - 2, col - 1);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 2) < 9) && ((col + 1) < 9)) {
            newPosition = new ChessPosition(row + 2, col + 1);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 2) < 9) && ((col - 1) > 0)) {
            newPosition = new ChessPosition(row + 2, col - 1);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        //********************************************************************************
        if(((row - 1) > 0) && ((col + 2) < 9)) {
            newPosition = new ChessPosition(row - 1, col + 2);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row - 1) > 0) && ((col - 2) > 0)) {
            newPosition = new ChessPosition(row - 1, col - 2);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 1) < 9) && ((col + 2) < 9)) {
            newPosition = new ChessPosition(row + 1, col + 2);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 1) < 9) && ((col - 2) > 0)) {
            newPosition = new ChessPosition(row + 1, col - 2);
            if(moveValidator( board,newPosition)){
                MovesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }


        return MovesCollection;
    }

    public boolean moveValidator(ChessBoard board, ChessPosition newPosition){

        if ((board.getPiece(newPosition) == null)) {
            return true;
        }
        if ((board.getPiece(newPosition)) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
            return true;
        }

        return false;
    }
//********************************************************************************************************************//
//                                                  BISHOP
//********************************************************************************************************************//
    public ArrayList<ChessMove> possibleBishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> MovesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int i = 1;
//(row - i) > 0 && ((col + i) < 9) && ((row + i) < 9) && ((col - i) > 0 )
        while(true){
            ChessPosition downLeftPos = new ChessPosition(row - i, col - i);
            if(downLeftPos.getRow() <= 0 || downLeftPos.getColumn() <= 0){
                break;
            }
            if(downLeftPos.getRow() > 8 || downLeftPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(downLeftPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, downLeftPos, null));
                System.out.println("bishop check === " + downLeftPos);
            }
            if ((board.getPiece(downLeftPos)) != null && board.getPiece(downLeftPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, downLeftPos, null));
                System.out.println("2added to movesCollection === " + downLeftPos);
                break;
            }
            if ((board.getPiece(downLeftPos)) != null && board.getPiece(downLeftPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }
        i = 1;
        //(row - i) > 0 && ((col + i) < 9) && ((row + i) < 9) && ((col - i) > 0 )
        while(true){
            ChessPosition downRightPos = new ChessPosition(row + i, col - i);
            if(downRightPos.getRow() <= 0 || downRightPos.getColumn() <= 0){
                break;
            }
            if(downRightPos.getRow() > 8 || downRightPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(downRightPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, downRightPos, null));
                System.out.println("3added to movesCollection === " + downRightPos);
            }
            if ((board.getPiece(downRightPos)) != null && board.getPiece(downRightPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, downRightPos, null));
                System.out.println("4added to movesCollection === " + downRightPos);
                break;
            }
            if ((board.getPiece(downRightPos)) != null && board.getPiece(downRightPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        i = 1;
        while(true){
            ChessPosition upLeftPos = new ChessPosition(row - i, col + i);
            if(upLeftPos.getRow() <= 0 || upLeftPos.getColumn() <= 0){
                break;
            }
            if(upLeftPos.getRow() > 8 || upLeftPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(upLeftPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, upLeftPos, null));
                System.out.println("5added to movesCollection === " + upLeftPos);
            }
            if ((board.getPiece(upLeftPos)) != null && board.getPiece(upLeftPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, upLeftPos, null));
                System.out.println("6added to movesCollection === " + upLeftPos);
                break;
            }
            if ((board.getPiece(upLeftPos)) != null && board.getPiece(upLeftPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        i = 1;

        while(true){
            ChessPosition upRightPos = new ChessPosition(row + i, col + i);
            if(upRightPos.getRow() <= 0 || upRightPos.getColumn() <= 0){
                break;
            }
            if(upRightPos.getRow() > 8 || upRightPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(upRightPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, upRightPos, null));
                System.out.println("7added to movesCollection === " + upRightPos);
            }
            if ((board.getPiece(upRightPos)) != null && board.getPiece(upRightPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, upRightPos, null));
                System.out.println("8added to movesCollection === " + upRightPos);
                break;
            }
            if ((board.getPiece(upRightPos)) != null && board.getPiece(upRightPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }
        return MovesCollection;
    }
//********************************************************************************************************************//
//                                                 ROOK
//********************************************************************************************************************//
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
                System.out.println("1added to movesCollection === " + downPos);
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() != pieceColor){
                // if the piece to the left is not your own its a possible move
                MovesCollection.add(new ChessMove(myPosition, downPos, null));
                System.out.println("2added to movesCollection === " + downPos);
                // add it to our collection of possible moves.
                break;
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() == pieceColor){
                // our team mate's piece is blocking this way
                break;
            }
            i++;
        }
        i = 1;
        // above the Rook
        while((row + i) < 9){
            ChessPosition upPos = new ChessPosition(row + i, col);
            if((board.getPiece(upPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, upPos, null));
                System.out.println("3added to movesCollection === " + upPos);
            }
            if ((board.getPiece(upPos)) != null && board.getPiece(upPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, upPos, null));
                System.out.println("4added to movesCollection === " + upPos);
                break;
            }
            if ((board.getPiece(upPos)) != null && board.getPiece(upPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        i = 1;
        // left of the Rook
        while((col - i) > 0 ){
            ChessPosition leftPos = new ChessPosition(row, col - i);
            if((board.getPiece(leftPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, leftPos, null));
                System.out.println("5added to movesCollection === " + leftPos);
            }
            if ((board.getPiece(leftPos)) != null && board.getPiece(leftPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, leftPos, null));
                System.out.println("6added to movesCollection === " + leftPos);
                break;
            }
            if ((board.getPiece(leftPos)) != null && board.getPiece(leftPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        i = 1;
        // right of the Rook
        while((col + i) < 9){
            ChessPosition rightPos = new ChessPosition(row, col + i);
            if((board.getPiece(rightPos) == null )){
                MovesCollection.add(new ChessMove(myPosition, rightPos, null));
                System.out.println("7added to movesCollection === " + rightPos);
            }
            if ((board.getPiece(rightPos)) != null && board.getPiece(rightPos).getTeamColor() != pieceColor){
                MovesCollection.add(new ChessMove(myPosition, rightPos, null));
                System.out.println("8added to movesCollection === " + rightPos);
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
        ArrayList<ChessMove> MoveCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //if the space above is empty that's a valid move
        // White Move
        //******************************************************************************
        ChessPosition newPos = new ChessPosition(row + 1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) == null && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if(myPosition.getRow() == 7){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        // Black Initial Move
        newPos = new ChessPosition(row - 1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) == null && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if(myPosition.getRow() == 2){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }

        // Pawns can capture diagonally
        //white ones capture up diagonally
        //******************************************************************************
        newPos = new ChessPosition(row +1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.WHITE && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if(myPosition.getRow() == 7){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        newPos = new ChessPosition(row +1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.WHITE && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if(myPosition.getRow() == 7){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        // and black ones capture down diagonally
        //******************************************************************************
        newPos = new ChessPosition(row -1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.BLACK && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if(myPosition.getRow() == 2){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        newPos = new ChessPosition(row -1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.BLACK && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if(myPosition.getRow() == 2){
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
                    MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
                }
                else {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }

        // if it is in row 2 for that pawn we can move forward 2 spaces
        //*******************************************************************************
        if(myPosition.getRow() == 2 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            newPos=new ChessPosition(row + 1, col);
            // and if there is no other piece in the way
            if(board.getPiece(newPos) == null) {
                newPos=new ChessPosition(row + 2, col);
                if(board.getPiece(newPos) == null) {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        if(myPosition.getRow() == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            newPos=new ChessPosition(row - 1, col);
            if(board.getPiece(newPos) == null) {
                newPos=new ChessPosition(row - 2, col);
                if(board.getPiece(newPos) == null) {
                    MoveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }

        // if a pawn reaches respectively row 1 or row 8
        //******************************************************************************
        if(myPosition.getRow() == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            newPos=new ChessPosition(row + 1, col);
            MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
            MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
            MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
            MoveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
        }

        return MoveCollection;
    }
}

