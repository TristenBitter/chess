package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
        ArrayList<ChessMove> plausiblePieceMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        //check if piece is not null

        if(board.getPiece(startPosition) == null){
            return null;
        }
        Iterator it = plausiblePieceMoves.iterator();
        while(it.hasNext()){
            ChessMove move = (ChessMove)it.next();
            if(tryMove(getTeamTurn(), move)){
                //if the move doesn't put us in check it is a valid move so add it to the list
                validMoves.add(move);
            }
        }

        //** check to see if there is a move that put the king in to check.
        //** check to see if the king is being moved into check
        // try allPossibleMoves from your own team
        // if king is in not in check after (makeMove()) is called then add it to valid moves

        //** if you are in check you must get yourself out of check
        //if in check, check to see if you're still in check after makeMove if not add it to valid moves

        // make a copy and call makeMoves to test out a possible solution to get you out of check.

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
            ChessPiece oldSpot = copy.getPiece(move.getStartPosition());
            oldSpot = null;
            // trash the board copy if needed... clean up


            //check if it is no longer in check....if so then return true...else
            if(!isInCheck(teamColor)){
                //we did not put our selves in check... let's add it to valid moves
                return true;
            }
        // that is not a valid move
        return false;


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

        ChessPosition endPos = move.getEndPosition();
        ChessPosition startPos = move.getStartPosition();
        ChessPiece.PieceType pieceType = board.getPiece(startPos).getPieceType();
        if(move.getPromotionPiece() != null){
            pieceType = move.getPromotionPiece();
        }

        Collection<ChessMove> validMoves = new ArrayList<>();
        //check if the move is valid
        validMoves = validMoves(startPos);
        Iterator itr = validMoves.iterator();
        while(itr.hasNext()){
            ChessMove moveTry = (ChessMove)itr.next();
            if(moveTry == move){
                this.board= copyOfBoard;
                break;
            }
        }
        // if isValid ... does not put us in check && if it is found in one of the possible moves of that piece
        //set the board to the new board with this.board= copyOfBoard;
        //otherwise throw this

        //throws InvalidMoveException
        throw new RuntimeException("Move not valid");
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
            listOfPossibleMoves=possibleMovesCollector(TeamColor.BLACK);
        }
        else{
            listOfPossibleMoves=possibleMovesCollector(TeamColor.WHITE);
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
            ChessPiece oldSpot = copyOfBoard.getPiece(move.getStartPosition());
            oldSpot = null;
            // trash the board copy if needed... clean up


            //check if it is no longer in check....if so then return true...else
            if(!isInCheck(teamColor)){
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
            // is there any move that can get me out of check
            if(tryMoveToGetOutOfCheck(teamColor) == false){
                // That's Check Mate... Game Over!
                return true;
            }
        }
        // if there is a validMove for the kings team to make then it's not in check mate.
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
        if(!isInCheck(teamColor)) {
            ArrayList<ChessMove> possibleMoves;
            if(teamColor == TeamColor.WHITE) {
                possibleMoves=possibleMovesCollector(TeamColor.BLACK);
            }
            else{
                possibleMoves=possibleMovesCollector(TeamColor.WHITE);
            }
            // and the number of possible moves is zero.
            if(possibleMoves.isEmpty()){
                return true;
            }
        }
        return false;
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


