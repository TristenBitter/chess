package chess;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition implements Collection<ChessPosition> {

    private final int row;
    private final int col;

//    public ChessPosition() {
//
//    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<ChessPosition> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(ChessPosition chessPosition) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ChessPosition> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition that)) return false;
        return getRow() == that.getRow() && col == that.col;
    }

    @Override
    public String toString() {
        return "CP{" + row + "," + col + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), col);
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
