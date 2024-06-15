package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();

    private ChessPiece.PieceType type = ChessPiece.PieceType.PAWN;
    private TeamColor teamTurn;
    private ChessPiece piece = new ChessPiece(getTeamTurn(), type);

    private boolean isGameOver = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return Objects.equals(getBoard(), chessGame.getBoard()) && type == chessGame.type && getTeamTurn() == chessGame.getTeamTurn() && Objects.equals(piece, chessGame.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoard(), type, getTeamTurn(), piece);
    }

    @Override
    public String toString() {
        return "ChessGame{" + "board=" + board + ", type=" + type + ", teamTurn=" + teamTurn + '}';
    }

    public ChessGame() {
        this.board.resetBoard();
        setTeamTurn(TeamColor.WHITE);

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver() {
        isGameOver=false;
    }
    /**
     * @return Which team's turn it is
     */

    /***********************************************************************************************
     *                               GET WHICH TEAM'S TURN IT IS
     ***********************************************************************************************/
    public TeamColor getTeamTurn() {
        return teamTurn;
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
        this.teamTurn = team;
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
        ArrayList<ChessMove> plausiblePieceMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        //check if piece is not null

        if(board.getPiece(startPosition) == null){
            return null;
        }
        Iterator it = plausiblePieceMoves.iterator();
        while(it.hasNext()){
            ChessMove move = (ChessMove)it.next();
            if(tryMove(board.getPiece(startPosition).getTeamColor(), move)){
                //if the move doesn't put us in check it is a valid move so add it to the list
                validMoves.add(move);
            }
        }
        TeamColor otherColor = getOpponentsTeamColor(board.getPiece(startPosition).getTeamColor());
        setTeamTurn(otherColor);

       return validMoves;
    }


    /***********************************************************************************************
     *                                       TRY MOVE
     ***********************************************************************************************/
    public boolean tryMove(TeamColor teamColor, ChessMove move){
        //ArrayList<ChessMove> allTheirPossibleMoves = possibleMovesCollector(teamColor);
        // make a copy
        ChessBoard copy=new ChessBoard(board);
        //make the move on the copy of the Board
        copy.addPiece( move.getEndPosition(),copy.getPiece(move.getStartPosition()));

        // remove the piece from the old spot by setting it equal to null
        copy.addPiece(move.getStartPosition(), null);

       // check if it is no longer in check....if so then return true...else
        if(!isInCheckSpecial(teamColor, copy)){
            //we did not put our selves in check... let's add it to valid moves
            return true;
        }
         //that is not a valid move
        return false;
    }

    public TeamColor getOpponentsTeamColor(TeamColor teamColor){
        if(teamColor == TeamColor.WHITE){
            return TeamColor.BLACK;
        }
        else{
            return TeamColor.WHITE;
        }
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
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // make a copy of the chess board
        // do the turn switches when calling set turn
        ChessBoard copyOfBoard=new ChessBoard(board);

        ChessPosition endPos = move.getEndPosition();
        ChessPosition startPos = move.getStartPosition();
        if(board.getPiece(startPos) == null){
            throw new InvalidMoveException("Move not valid");
        }
        if(board.getPiece(startPos).getTeamColor() != teamTurn){
            throw new InvalidMoveException("Move not valid");
        }

        // make the move
        copyOfBoard.addPiece(endPos,copyOfBoard.getPiece(startPos));

        // remove the piece from the old spot by setting it equal to null
        copyOfBoard.addPiece(startPos, null);


        ChessPiece.PieceType pieceType = board.getPiece(startPos).getPieceType();

        Collection<ChessMove> validMoves = new ArrayList<>();
        //check if the move is valid
        validMoves = validMoves(startPos);
        Iterator<ChessMove> itr = validMoves.iterator();
        boolean moveFound = false;
        while(itr.hasNext()){
            ChessMove moveTry = (ChessMove)itr.next();
            if(moveTry.equals(move)){
                this.board= copyOfBoard;
                moveFound = true;
                break;
            }
        }
        if(move.getPromotionPiece() != null){
            pieceType = move.getPromotionPiece();
            ChessPiece promotedPiece = new ChessPiece(copyOfBoard.getPiece(endPos).getTeamColor(), pieceType);
            copyOfBoard.addPiece(endPos, null);
            copyOfBoard.addPiece(endPos, promotedPiece);
            moveFound = true;
            this.board= copyOfBoard;
        }

        // if isValid ... does not put us in check && if it is found in one of the possible moves of that piece
        //set the board to the new board with this.board= copyOfBoard;
        //otherwise throw this

        //throws InvalidMoveException
        if(moveFound == false) {
            throw new InvalidMoveException("Move not valid");
        }
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
        boolean answer = isInCheckSpecial(teamColor, board);

        return answer;
    }
    public boolean isInCheckSpecial(TeamColor color, ChessBoard newBoard) {
        //find current position of king and see if the other team has possible end position on that position
        // if a future move in the valid moves ends with the space of the king being taken
        // then its in check
        ChessPosition kingPos = findKing(color, newBoard);
        ArrayList<ChessMove> possibleMoves = possibleMovesCollector(color,newBoard);
        //if kingPos is in possible moves then return true, else false
        return possibleMoves.stream().anyMatch(chessMove -> chessMove.getEndPosition().equals(kingPos));
    }

    /***********************************************************************************************
     *                               POSSIBLE MOVES COLLECTOR
     ***********************************************************************************************/
    public ArrayList<ChessMove> possibleMovesCollector(TeamColor teamColor, ChessBoard board){
        ArrayList<ChessMove>allPossibleMoves = new ArrayList<>();
        int i = 1;
        int j = 1;
        ChessPosition position = new ChessPosition(i,j);
        while(i < 9) {
            j = 1;
            while(j < 9){
                position=new ChessPosition(i, j);
                if(board.getPiece(position) != null && board.getPiece(position).getTeamColor() != teamColor ) {
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
    public ChessPosition findKing(TeamColor teamColor, ChessBoard board){
        ChessPosition kingPosition = new ChessPosition(0,0);
        int i = 1;
        while(i < 9) {
            int j = 1;
            while(j < 9){
                kingPosition=new ChessPosition(i, j);
                if (board.getPiece(kingPosition) != null && board.getPiece(kingPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(kingPosition).getTeamColor() == teamColor) {
                    i += 9;
                    break;
                }
                j++;
            }
            i++;
        }

       return kingPosition;
    }

    /***********************************************************************************************
     *                             TRY MOVE TO GET OUT OF CHECK
     ***********************************************************************************************/
    public boolean tryMoveToGetOutOfCheck(TeamColor teamColor){
        // is there any move that can get me out of check
        // make a copy of the board
        //ChessBoard copyOfBoard=new ChessBoard(board);
        // let's try all of our possible Moves to see there is one to get me out of check
        ArrayList<ChessMove>listOfPossibleMoves;
        if(teamColor == TeamColor.WHITE) {
            listOfPossibleMoves=possibleMovesCollector(TeamColor.BLACK, board);
        }
        else{
            listOfPossibleMoves=possibleMovesCollector(TeamColor.WHITE, board);
        }
        //possibleMovesCollector(teamColor);
        //allPossibleMoves.forEach(chessMove -> if(tryMove(chessMove)));
        Iterator itr = listOfPossibleMoves.iterator();
        while(itr.hasNext()){
            // make a copy of the board
            ChessBoard copyOfBoard=new ChessBoard(board);
            //make the move on the copy
            ChessMove move =(ChessMove) itr.next();
            copyOfBoard.addPiece( move.getEndPosition(),copyOfBoard.getPiece(move.getStartPosition()));
            // remove the piece from the old spot by setting it equal to null
            copyOfBoard.addPiece(move.getStartPosition(), null);
            // trash the board copy if needed... clean up


            //check if it is no longer in check....if so then return true...else
            if(!isInCheckSpecial(teamColor, copyOfBoard)){
                //we are no longer in check... woo hoo!
                return true;
            }
        }
        // there is no way out of check, that's checkMate... Game Over
        return false;
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
        // change the start position to the new end position and the old start position to null
        if(isInCheck(teamColor)){

            if(tryMoveToGetOutOfCheck(teamColor) == false){
                // That's Check Mate... Game Over!
                return true;
            }
        }
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
        if (!isInCheck(teamColor)) {
            //make a list of the team pieces that are still on the board
            ArrayList<ChessPosition> allTeamPieces=findAllPiece(teamColor);
            // turn them into start positions
            ChessPosition startPosition;
            Iterator<ChessPosition> it=allTeamPieces.iterator();
            while (it.hasNext()) {
                ChessPosition piece1=(ChessPosition) it.next();
                ArrayList<ChessMove> possibleMoves;
                possibleMoves=(ArrayList<ChessMove>) validMoves(piece1);
                if (!possibleMoves.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ArrayList<ChessPosition> findAllPiece(TeamColor teamColor){
        ArrayList<ChessPosition> allTeamPieces = new ArrayList<>();
        ChessPosition position;
        int i = 1;
        while(i < 9) {
            int j = 1;
            while(j < 9){
                position=new ChessPosition(i, j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == teamColor) {
                    allTeamPieces.add(position);
                }
                j++;
            }
            i++;
        }

        return allTeamPieces;
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


