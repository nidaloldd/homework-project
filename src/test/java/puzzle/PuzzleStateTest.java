package puzzle;


import org.junit.jupiter.api.Test;

import java.util.EnumSet;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PuzzleStateTest {

    //default state
    PuzzleState state1 = new PuzzleState(PuzzleState.START_RED_POS,PuzzleState.START_BLUE_POS);
    //solved
    PuzzleState state2 = new PuzzleState(new Position(1,5),new Position(4,5));
    PuzzleState state3 = new PuzzleState(new Position(1,3),new Position(5,3));
    PuzzleState state4 = new PuzzleState(new Position(2,1),new Position(4,1));


    @Test
    void testConstructor_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new PuzzleState(new Position(-1,3),new Position(3,3)));
        assertThrows(IllegalArgumentException.class, () -> new PuzzleState(new Position(2,6),new Position(3,3)));
    }


    @Test
    void isSolved() {
        assertFalse(state1.isSolved());
        assertTrue(state2.isSolved());
        assertFalse(state3.isSolved());
        assertFalse(state4.isSolved());
    }

    @Test
    void canMove_state1() {
        assertTrue(state1.canMove(Direction.UP));
        assertFalse(state1.canMove(Direction.RIGHT));
        assertTrue(state1.canMove(Direction.DOWN));
        assertTrue(state1.canMove(Direction.LEFT));
    }
    @Test
    void canMove_state2() {
        assertTrue(state2.canMove(Direction.UP));
        assertFalse(state2.canMove(Direction.RIGHT));
        assertTrue(state2.canMove(Direction.DOWN));
        assertTrue(state2.canMove(Direction.LEFT));
    }
    @Test
    void canMove_state3() {
        assertFalse(state3.canMove(Direction.UP));
        assertTrue(state3.canMove(Direction.RIGHT));
        assertFalse(state3.canMove(Direction.DOWN));
        assertTrue(state3.canMove(Direction.LEFT));
    }

    @Test
    void canMove_state4() {
        assertTrue(state4.canMove(Direction.UP));
        assertTrue(state4.canMove(Direction.RIGHT));
        assertTrue(state4.canMove(Direction.DOWN));
        assertFalse(state4.canMove(Direction.LEFT));
    }

    @Test
    void LegalMoves(){
        assertTrue( EnumSet.of(Direction.UP,Direction.DOWN,Direction.LEFT).equals(state1.getLegalMoves()));
        assertTrue( EnumSet.of(Direction.UP,Direction.DOWN,Direction.LEFT).equals(state2.getLegalMoves()));
        assertTrue( EnumSet.of(Direction.RIGHT,Direction.LEFT).equals(state3.getLegalMoves()));
        assertTrue( EnumSet.of(Direction.UP,Direction.RIGHT,Direction.DOWN).equals(state4.getLegalMoves()));
    }

    @Test
    void  hitWall_stage1(){
        var pos1 = state1.getPosition(state1.redBall);
        assertFalse(state1.hitWall(pos1,Direction.UP));
        assertTrue(state1.hitWall(pos1,Direction.RIGHT));
        assertTrue(state1.hitWall(pos1,Direction.DOWN));
        assertFalse(state1.hitWall(pos1,Direction.LEFT));

        var pos2 = state1.getPosition(state1.blueBall);
        assertTrue(state1.hitWall(pos2,Direction.UP));
        assertTrue(state1.hitWall(pos2,Direction.RIGHT));
        assertFalse(state1.hitWall(pos2,Direction.DOWN));
        assertFalse(state1.hitWall(pos2,Direction.LEFT));
    }

    @Test
    void  hitWall_stage3(){
        var pos1 = state1.getPosition(state1.redBall);
        assertTrue(state3.hitWall(pos1,Direction.UP));
        assertFalse(state3.hitWall(pos1,Direction.RIGHT));
        assertTrue(state3.hitWall(pos1,Direction.DOWN));
        assertTrue(state3.hitWall(pos1,Direction.LEFT));

        var pos2 = state1.getPosition(state1.blueBall);
        assertTrue(state3.hitWall(pos2,Direction.UP));
        assertTrue(state3.hitWall(pos2,Direction.RIGHT));
        assertTrue(state3.hitWall(pos2,Direction.DOWN));
        assertFalse(state3.hitWall(pos2,Direction.LEFT));
    }

    @Test
    void  hitWall_stage4(){
        var pos1 = state1.getPosition(state1.redBall);
        assertFalse(state4.hitWall(pos1,Direction.UP));
        assertFalse(state4.hitWall(pos1,Direction.RIGHT));
        assertFalse(state4.hitWall(pos1,Direction.DOWN));
        assertTrue(state4.hitWall(pos1,Direction.LEFT));

        var pos2 = state1.getPosition(state1.blueBall);
        assertTrue(state4.hitWall(pos2,Direction.UP));
        assertFalse(state4.hitWall(pos2,Direction.RIGHT));
        assertFalse(state4.hitWall(pos2,Direction.DOWN));
        assertTrue(state4.hitWall(pos2,Direction.LEFT));
    }

    void assertPosition(Position expectedPos1, Position expectedPos2, PuzzleState state,Direction dir) {
        var copy = state.clone();
        copy.move(dir);
        assertAll("position",
                () -> assertEquals(expectedPos1, copy.redBall),
                () -> assertEquals(expectedPos2, copy.blueBall)
        );
    }

    @Test
    void move_state1(){
        assertPosition(new Position(1,5), new Position(3,5),state1, Direction.UP);
        assertPosition(new Position(2,5), new Position(5,5),state1, Direction.DOWN);
        assertPosition(new Position(2,1), new Position(3,1),state1, Direction.LEFT);
        assertPosition(new Position(2,5), new Position(3,5),state1, Direction.RIGHT);
    }

    @Test
    void move_state3(){
        assertPosition(new Position(1,3), new Position(5,3),state3, Direction.UP);
        assertPosition(new Position(1,3), new Position(5,3),state3, Direction.DOWN);
        assertPosition(new Position(1,3), new Position(5,1),state3, Direction.LEFT);
        assertPosition(new Position(1,5), new Position(5,3),state3, Direction.RIGHT);
    }

    @Test
    void move_state4(){
        assertPosition(new Position(1,1), new Position(4,1),state4, Direction.UP);
        assertPosition(new Position(3,1), new Position(5,1),state4, Direction.DOWN);
        assertPosition(new Position(2,1), new Position(4,1),state4, Direction.LEFT);
        assertPosition(new Position(2,5), new Position(4,5),state4, Direction.RIGHT);
    }


}
