package chess;
import java.util.ArrayList;
import java.util.Objects;
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
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return type;
    }
    /***********************************************************************************************
     *                               PIECE MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        switch(type){
            case KING:
                movesCollection = possibleKingMoves(board, myPosition);
                break;
            case QUEEN:
                movesCollection = possibleQueenMoves(board, myPosition);
                break;
            case KNIGHT:
                movesCollection = possibleKnightMoves(board, myPosition);
                break;
            case BISHOP:
                movesCollection = possibleBishopMoves(board, myPosition);
                break;
            case ROOK:
                movesCollection = possibleRookMoves(board, myPosition);
                break;
            case PAWN:
                movesCollection = possiblePawnMoves(board, myPosition);
                break;
        }

        return movesCollection;
    }
    /***********************************************************************************************
     *                               POSSIBLE KING MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleKingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ChessPosition newPos = new ChessPosition(row + 1, col);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row, col + 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row + 1, col + 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row, col - 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row - 1, col - 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row -1, col);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row - 1, col + 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));

        newPos = new ChessPosition(row + 1, col - 1);
        movesCollection.addAll(kingMoveCollector(board, myPosition, newPos));
        
        return movesCollection;
    }
    /***********************************************************************************************
     *                               KING MOVES HELPER
     ***********************************************************************************************/
    public ArrayList<ChessMove> kingMoveCollector(ChessBoard board, ChessPosition myPosition,ChessPosition newPos){
        ArrayList<ChessMove> movesCollection = new ArrayList<>();

        if(!(isOutOfBounds(newPos))){
            if(moveValidator( board,newPos)){
                movesCollection.add(new ChessMove(myPosition, newPos, null));
            }
        }

        return movesCollection;
    }
    /***********************************************************************************************
     *                               OUT OF BOUNDS CHECKER
     ***********************************************************************************************/
    public boolean isOutOfBounds(ChessPosition newPos){
        if(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8){
            return true;
        }
        return false;
    }
    /***********************************************************************************************
     *                               POSSIBLE QUEEN MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleQueenMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        movesCollection = possibleBishopMoves(board, myPosition);
        movesCollection.addAll(possibleRookMoves(board,myPosition));
        return movesCollection;
    }
    /***********************************************************************************************
     *                               POSSIBLE KNIGHT MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleKnightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition = new ChessPosition(row, col);

        if(((row - 2) > 0) && ((col + 1) < 9)) {
            newPosition = new ChessPosition(row - 2, col + 1);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row - 2) > 0) && ((col - 1) > 0)) {
            newPosition = new ChessPosition(row - 2, col - 1);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 2) < 9) && ((col + 1) < 9)) {
            newPosition = new ChessPosition(row + 2, col + 1);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 2) < 9) && ((col - 1) > 0)) {
            newPosition = new ChessPosition(row + 2, col - 1);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row - 1) > 0) && ((col + 2) < 9)) {
            newPosition = new ChessPosition(row - 1, col + 2);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row - 1) > 0) && ((col - 2) > 0)) {
            newPosition = new ChessPosition(row - 1, col - 2);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 1) < 9) && ((col + 2) < 9)) {
            newPosition = new ChessPosition(row + 1, col + 2);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        if(((row + 1) < 9) && ((col - 2) > 0)) {
            newPosition = new ChessPosition(row + 1, col - 2);
            if(moveValidator( board,newPosition)){
                movesCollection.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return movesCollection;
    }
    /***********************************************************************************************
     *                               MOVE VALIDATOR HELPER METHOD
     ***********************************************************************************************/
    public boolean moveValidator(ChessBoard board, ChessPosition newPosition){
        if ((board.getPiece(newPosition) == null)) {
            return true;
        }
        if ((board.getPiece(newPosition)) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
            return true;
        }
        return false;
    }
    /***********************************************************************************************
     *                               POSSIBLE BISHOP MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleBishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int i = 1;
        while(true){
            ChessPosition downLeftPos = new ChessPosition(row - i, col - i);
            if(downLeftPos.getRow() <= 0 || downLeftPos.getColumn() <= 0){
                break;
            }
            if(downLeftPos.getRow() > 8 || downLeftPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(downLeftPos) == null )){
                movesCollection.add(new ChessMove(myPosition, downLeftPos, null));
                System.out.println("bishop check === " + downLeftPos);
            }
            if ((board.getPiece(downLeftPos)) != null && board.getPiece(downLeftPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, downLeftPos, null));
                System.out.println("2added to movesCollection === " + downLeftPos);
                break;
            }
            if ((board.getPiece(downLeftPos)) != null && board.getPiece(downLeftPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }
        i = 1;
        while(true){
            ChessPosition downRightPos = new ChessPosition(row + i, col - i);
            if(downRightPos.getRow() <= 0 || downRightPos.getColumn() <= 0){
                break;
            }
            if(downRightPos.getRow() > 8 || downRightPos.getColumn() > 8){
                break;
            }
            if((board.getPiece(downRightPos) == null )){
                movesCollection.add(new ChessMove(myPosition, downRightPos, null));
                System.out.println("3added to movesCollection === " + downRightPos);
            }
            if ((board.getPiece(downRightPos)) != null && board.getPiece(downRightPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, downRightPos, null));
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
                movesCollection.add(new ChessMove(myPosition, upLeftPos, null));
                System.out.println("5added to movesCollection === " + upLeftPos);
            }
            if ((board.getPiece(upLeftPos)) != null && board.getPiece(upLeftPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, upLeftPos, null));
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
                movesCollection.add(new ChessMove(myPosition, upRightPos, null));
                System.out.println("7added to movesCollection === " + upRightPos);
            }
            if ((board.getPiece(upRightPos)) != null && board.getPiece(upRightPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, upRightPos, null));
                System.out.println("8added to movesCollection === " + upRightPos);
                break;
            }
            if ((board.getPiece(upRightPos)) != null && board.getPiece(upRightPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }
        return movesCollection;
    }
    /***********************************************************************************************
     *                               POSSIBLE ROOK MOVES
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleRookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> movesCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int i = 1;
        while((row - i) > 0){

            ChessPosition downPos = new ChessPosition(row - i, col);
            //if the space is empty its good to go.
            if((board.getPiece(downPos) == null )){
                movesCollection.add(new ChessMove(myPosition, downPos, null));
                System.out.println("1added to movesCollection === " + downPos);
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() != pieceColor){
                // if the piece to the left is not your own its a possible move
                movesCollection.add(new ChessMove(myPosition, downPos, null));
                System.out.println("2added to movesCollection === " + downPos);
                // add it to our collection of possible moves.
                break;
            }
            if ((board.getPiece(downPos)) != null && board.getPiece(downPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }

        i = 1;
        while((row + i) < 9){
            ChessPosition upPos = new ChessPosition(row + i, col);
            if((board.getPiece(upPos) == null )){
                movesCollection.add(new ChessMove(myPosition, upPos, null));
                System.out.println("3added to movesCollection === " + upPos);
            }
            if ((board.getPiece(upPos)) != null && board.getPiece(upPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, upPos, null));
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
                movesCollection.add(new ChessMove(myPosition, leftPos, null));
                System.out.println("5added to movesCollection === " + leftPos);
            }
            if ((board.getPiece(leftPos)) != null && board.getPiece(leftPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, leftPos, null));
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
                movesCollection.add(new ChessMove(myPosition, rightPos, null));
                System.out.println("7added to movesCollection === " + rightPos);
            }
            if ((board.getPiece(rightPos)) != null && board.getPiece(rightPos).getTeamColor() != pieceColor){
                movesCollection.add(new ChessMove(myPosition, rightPos, null));
                System.out.println("8added to movesCollection === " + rightPos);
                break;
            }
            if ((board.getPiece(rightPos)) != null && board.getPiece(rightPos).getTeamColor() == pieceColor){
                break;
            }
            i++;
        }
        return movesCollection;
    }
    /***********************************************************************************************
     *                               POSSIBLE PAWN MOVES
     ***********************************************************************************************/
    public void promotePawnCheck(ArrayList<ChessMove> movesCollection, ChessPosition myPosition, ChessPosition newPos, int row){
        if(myPosition.getRow() == row){
            movesCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
            movesCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
            movesCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
            movesCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
        }
        else {
            movesCollection.add(new ChessMove(myPosition, newPos, null));
        }
    }
    public ArrayList<ChessMove> possiblePawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moveCollection = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ChessPosition newPos = new ChessPosition(row + 1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) == null && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                promotePawnCheck(moveCollection, myPosition, newPos, 7);
            }
        }
        newPos = new ChessPosition(row - 1, col);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) == null && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                promotePawnCheck(moveCollection, myPosition, newPos, 2);
            }
        }
        newPos = new ChessPosition(row +1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.WHITE && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                promotePawnCheck(moveCollection, myPosition, newPos, 7);
            }
        }
        newPos = new ChessPosition(row +1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.WHITE && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                promotePawnCheck(moveCollection, myPosition, newPos, 7);
            }
        }
        // and black ones capture down diagonally
        newPos = new ChessPosition(row -1, col + 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.BLACK && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                promotePawnCheck(moveCollection, myPosition, newPos, 2);
            }
        }
        newPos = new ChessPosition(row -1, col - 1);
        if(!(newPos.getRow() <= 0 || newPos.getColumn() <= 0 || newPos.getRow() > 8 || newPos.getColumn() > 8)) {
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != ChessGame.TeamColor.BLACK && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                promotePawnCheck(moveCollection, myPosition, newPos, 2);
            }
        }
        if(myPosition.getRow() == 2 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            newPos=new ChessPosition(row + 1, col);
            // and if there is no other piece in the way
            if(board.getPiece(newPos) == null) {
                newPos=new ChessPosition(row + 2, col);
                if(board.getPiece(newPos) == null) {
                    moveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        if(myPosition.getRow() == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            newPos=new ChessPosition(row - 1, col);
            if(board.getPiece(newPos) == null) {
                newPos=new ChessPosition(row - 2, col);
                if(board.getPiece(newPos) == null) {
                    moveCollection.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        // if a pawn reaches respectively row 1 or row 8
        if(myPosition.getRow() == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            newPos=new ChessPosition(row + 1, col);
            moveCollection.add(new ChessMove(myPosition, newPos, PieceType.QUEEN));
            moveCollection.add(new ChessMove(myPosition, newPos, PieceType.ROOK));
            moveCollection.add(new ChessMove(myPosition, newPos, PieceType.BISHOP));
            moveCollection.add(new ChessMove(myPosition, newPos, PieceType.KNIGHT));
        }
        return moveCollection;
    }
}

