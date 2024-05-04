package passoff.chess;

import chess.ChessPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChessPositionTests {
    private ChessPosition original;
    private ChessPosition equal;
    private ChessPosition different;
    @BeforeEach
    public void setUp() {
        original = new ChessPosition(3, 7);
        equal = new ChessPosition(3, 7);
        different = new ChessPosition(7, 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPositionTests that)) return false;
        return Objects.equals(original, that.original) && Objects.equals(equal, that.equal) && Objects.equals(different, that.different);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, equal, different);
    }

    @Test
    @DisplayName("Equals Testing")
    public void equalsTest() {
        Assertions.assertEquals(original, equal, "equals returned false for equal positions");
        Assertions.assertNotEquals(original, different, "equals returned true for different positions");
    }

    @Test
    @DisplayName("HashCode Testing")
    public void hashTest() {
        Assertions.assertEquals(original.hashCode(), equal.hashCode(),
                "hashCode returned different values for equal positions");
        Assertions.assertNotEquals(original.hashCode(), different.hashCode(),
                "hashCode returned the same value for different positions");
    }

    @Test
    @DisplayName("Combined Testing")
    public void hashSetTest() {
        Set<ChessPosition> set = new HashSet<>();
        set.add(original);

        Assertions.assertTrue(set.contains(original));
        Assertions.assertTrue(set.contains(equal));
        Assertions.assertEquals(1, set.size());
        set.add(equal);
        Assertions.assertEquals(1, set.size());

        Assertions.assertFalse(set.contains(different));
        set.add(different);
        Assertions.assertEquals(2, set.size());


    }

}
