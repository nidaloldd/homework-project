package puzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void to() {
        assertSame("UP", Direction.UP.toString());
        assertSame("RIGHT", Direction.RIGHT.toString());
        assertSame("DOWN", Direction.DOWN.toString());
        assertSame("LEFT", Direction.LEFT.toString());
    }


}
