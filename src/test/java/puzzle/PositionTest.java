package puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position position;

    void assertPosition(int expectedRow, int expectedCol, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.getRow()),
                () -> assertEquals(expectedCol, position.getCol())
        );
    }

    @BeforeEach
    void init() {
        position = new Position(0, 0);
    }

    @Test
    void setPosTo_up() {
        position.setRow(Direction.UP.getRowChange());
        position.setCol(Direction.UP.getColChange());
        assertPosition(-1, 0, position);
    }
    @Test
    void setPosTo_down() {
        position.setRow(Direction.DOWN.getRowChange());
        position.setCol(Direction.DOWN.getColChange());
        assertPosition(1, 0, position);
    }
    @Test
    void setPosTo_right() {
        position.setRow(Direction.RIGHT.getRowChange());
        position.setCol(Direction.RIGHT.getColChange());
        assertPosition(0, 1, position);
    }
    @Test
    void setPosTo_left() {
        position.setRow(Direction.LEFT.getRowChange());
        position.setCol(Direction.LEFT.getColChange());
        assertPosition(0, -1, position);
    }
    @Test
    void stepOneToDirection() {
        assertPosition(position.getRow()-1, position.getCol()+0, position.stepOneToDirection(Direction.UP));
        assertPosition(position.getRow()+0, position.getCol()+1, position.stepOneToDirection(Direction.RIGHT));
        assertPosition(position.getRow()+1, position.getCol()+0, position.stepOneToDirection(Direction.DOWN));
        assertPosition(position.getRow()+0, position.getCol()-1, position.stepOneToDirection(Direction.LEFT));
    }

    @Test
    void testOnBoard() {

        // if the Board Size is 5*5
        for(int i=0; i<5 ;i++){
            for(int j=0; j<5 ;j++){
                assertTrue((new Position(i,j).onBoard()));
            }
        }
        for(int i=-10; i<0 ;i++){
            for(int j=0; j<5 ;j++){
                assertFalse((new Position(i,j).onBoard()));
                assertFalse((new Position(j,i).onBoard()));
            }
        }
        for(int i=6; i<=10 ;i++){
            for(int j=0; j<5 ;j++){
                assertFalse((new Position(i,j).onBoard()));
                assertFalse((new Position(j,i).onBoard()));
            }
        }

    }

    @Test
    void testEquals() {
        assertTrue(position.equals(position));
        assertTrue(position.equals(new Position(position.getRow(), position.getCol())));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, position.getCol())));
        assertFalse(position.equals(new Position(position.getRow(), Integer.MAX_VALUE)));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        assertFalse(position.equals(null));
        assertFalse(position.equals("STRING!"));
    }

    @Test
    void testHashCode() {
        assertTrue(position.hashCode() == position.hashCode());
        assertTrue(position.hashCode() == new Position(position.getRow(), position.getCol()).hashCode());
    }

    @Test
    void testClone() {
        var clone = position.clone();
        assertTrue(clone.equals(position));
        assertNotSame(position, clone);
    }


    @Test
    void testToString() {
        assertEquals("(0,0)", position.toString());
    }



}
