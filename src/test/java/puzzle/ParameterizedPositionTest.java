package puzzle;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterizedPositionTest {

    void assertPosition(int expectedRow, int expectedCol, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.getRow()),
                () -> assertEquals(expectedCol, position.getCol())
        );
    }

    static Stream<Position> positionProvider() {
        return Stream.of(new Position(0, 0),
                new Position(0, 2),
                new Position(2, 0),
                new Position(2, 2));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void stepOneToDirection(Position position) {
        assertPosition(position.getRow()-1, position.getCol()+0, position.stepOneToDirection(Direction.UP));
        assertPosition(position.getRow()+0, position.getCol()+1, position.stepOneToDirection(Direction.RIGHT));
        assertPosition(position.getRow()+1, position.getCol()+0, position.stepOneToDirection(Direction.DOWN));
        assertPosition(position.getRow()+0, position.getCol()-1, position.stepOneToDirection(Direction.LEFT));
    }


    @ParameterizedTest
    @MethodSource("positionProvider")
    void testEquals(Position position) {
        assertTrue(position.equals(position));
        assertTrue(position.equals(new Position(position.getRow(), position.getCol())));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, position.getCol())));
        assertFalse(position.equals(new Position(position.getRow(), Integer.MAX_VALUE)));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        assertFalse(position.equals(null));
        assertFalse(position.equals("STRING!"));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testHashCode(Position position) {
        assertTrue(position.hashCode() == position.hashCode());
        assertTrue(position.hashCode() == new Position(position.getRow(), position.getCol()).hashCode());
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testClone(Position position) {
        var clone = position.clone();
        assertEquals(clone, position);
        assertNotSame(position, clone);
    }


    @ParameterizedTest
    @MethodSource("positionProvider")
    void testToString(Position position) {
        assertEquals(String.format("(%d,%d)", position.getRow(), position.getCol()), position.toString());
    }

}
