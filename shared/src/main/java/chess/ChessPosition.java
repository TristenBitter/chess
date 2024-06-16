package chess;


import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ChessPosition that)) return false;
//        return getRow() == that.getRow() && col == that.col;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition position)) return false;
      return getRow() == position.getRow() && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), col);
    }

    @Override
    public String toString() {
        return "CP{" + row + "," + col + '}';
    }



    /***********************************************************************************************
     *                               CHESS POSITION
     ***********************************************************************************************/
    public ChessPosition(int row, int col) {
        this.row=row;
        this.col=col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */

    /***********************************************************************************************
     *                               GET ROW
     ***********************************************************************************************/
    public int getRow() {
        //throw new RuntimeException("Not implemented");
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */

    /***********************************************************************************************
     *                               GET COLUMN
     ***********************************************************************************************/
    public int getColumn() {
        //throw new RuntimeException("Not implemented");
        return col;
    }
}
