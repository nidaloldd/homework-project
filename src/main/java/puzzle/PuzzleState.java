package puzzle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableObjectValue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.StringJoiner;

/**
 * Represent the current State of the puzzle.
 */
public final class PuzzleState implements Cloneable {

    /**
     * This is the size of the board.
     */
    public static final int BOARD_SIZE = 5;


    /**
     * The default position of the red ball.
     */
    public static final Position START_RED_POS = new Position(1, 4);
    /**
     * The default position of the blue ball.
     */
    public static final Position START_BLUE_POS = new Position(2, 4);


    /**
     * The position of the red hole.
     */
    //public int redHole = 2;
    private final Position redHole = new Position(0, 4);

    /**
     * The position of the blue hole.
     */
    private final Position blueHole = new Position(3, 4);
    //public int blueHole = 3;

    /**
     * The current position of the red ball.
     */
    //public Position redBall;
    public int redBall = 2;
    /**
     * The current position of the blue ball.
     */
    //public Position blueBall;
    public int blueBall = 3;

    /**
     * this array contains the walls of the board.
     */
    private final Wall[] walls = new Wall[] {

            new Wall(new Position(0, 1), Direction.RIGHT),
            new Wall(new Position(0, 2), Direction.LEFT),
            new Wall(new Position(0, 2), Direction.DOWN),
            new Wall(new Position(1, 2), Direction.UP),
            new Wall(new Position(1, 4), Direction.DOWN),
            new Wall(new Position(2, 4), Direction.UP),
            new Wall(new Position(2, 0), Direction.DOWN),
            new Wall(new Position(3, 0), Direction.UP),
            new Wall(new Position(3, 2), Direction.DOWN),
            new Wall(new Position(4, 2), Direction.UP),
            new Wall(new Position(4, 2), Direction.RIGHT),
            new Wall(new Position(4, 3), Direction.LEFT)


    };


    private ReadOnlyObjectWrapper<Position>[] positions = new ReadOnlyObjectWrapper[4];

    private ReadOnlyObjectWrapper<PuzzleState> solveState = new ReadOnlyObjectWrapper();
    private ReadOnlyObjectWrapper<PuzzleState> corState = new ReadOnlyObjectWrapper();
    private ReadOnlyBooleanWrapper solved = new ReadOnlyBooleanWrapper();

    /**
     * Creates a {@code PuzzleState} object and set the Red and Blue balls
     * position to the given values.
     *
     * @param redBallP  The position of the red ball.
     * @param blueBallP The position of the blue ball.
     */
    public PuzzleState(final Position redBallP, final Position blueBallP) {

        if(!redBallP.onBoard() || !blueBallP.onBoard()){
            throw new IllegalArgumentException();
        }

        this.positions[0] = new ReadOnlyObjectWrapper<>(redHole);
        this.positions[1] = new ReadOnlyObjectWrapper<>(blueHole);
        this.positions[redBall] = new ReadOnlyObjectWrapper<>(redBallP);
        this.positions[blueBall] = new ReadOnlyObjectWrapper<>(blueBallP);
        this.corState = new ReadOnlyObjectWrapper<>(this);



        //solved.bind();

    }

    public ReadOnlyBooleanProperty solvedProperty() {
       // this.corState = new ReadOnlyObjectWrapper<>(new PuzzleState(positions[redBall].get(),positions[blueBall].get()));

        //solved.bind(this.positions[RED_SHOE].isEqualTo(this.positions[BLUE_SHOE]));

        solved.bind(this.solveState.isEqualTo(corState));
        return solved.getReadOnlyProperty();
    }


    /**
     * Move the balls to a direction until they reach a wall.
     *
     * @param direction The Direction of the move.
     */
    public void move(final Direction direction) {
        if (canMove(direction)) {
            while (!hitWall(positions[redBall].get(), direction)) {
                positions[redBall].set(positions[redBall].get().stepOneToDirection(direction));
               // redBall = redBall.stepOneToDirection(direction);
            }
            while (!hitWall(positions[blueBall].get(), direction)) {
                positions[blueBall].set(positions[blueBall].get().stepOneToDirection(direction));
                //blueBall = blueBall.stepOneToDirection(direction);
            }
        }
    }

    /**
     * @return An EnumSet of the legal directions where the player can move.
     */
    public EnumSet<Direction> getLegalMoves() {
            var legalMoves = EnumSet.noneOf(Direction.class);
            for (var dir : Direction.values()) {
                if (canMove(dir)) {
                    legalMoves.add(dir);
                }
            }
            return legalMoves;
    }

    /**
     *
     * @param direction The {@link Direction} of the move.
     *
     * @return If the balls can move from the current position
     * to a given direction.
     */
    public Boolean canMove(final Direction direction) {
        Position ball1 = positions[redBall].get();
        Position ball2 = positions[blueBall].get();
        int step = 0;

        while (!hitWall(ball1, direction)) {
            step++; ball1 = ball1.stepOneToDirection(direction);
        }
        while (!hitWall(ball2, direction)) {
            step++; ball2 = ball2.stepOneToDirection(direction);
        }

        if (ball1.equals(ball2)) {
            return false;
        } else {
            return step != 0;
        }
    }

    public Boolean hitWall(final Position position,
                            final Direction direction) {
        Position pos = position.stepOneToDirection(direction);

        if (!pos.onBoard()) {
            return true;
        }

        for (Wall wall:walls) {
            if (position.equals(wall.getPosition())
                    && wall.getDirection() == direction) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return True if the puzzle is solved else False.
     */
    public boolean isSolved() {
        return  positions[blueBall].get().equals(blueHole) && positions[redBall].get().equals(redHole);
        //return blueBall.equals(blueHole) && redBall.equals(redHole);
    }


    public Position getPosition(int n) {
        var x = (positions[n].get().getRow()*2)-1;
        var y = (positions[n].get().getCol()*2)-1;

        return positions[n].get();
    }
    public ReadOnlyObjectProperty<Position> positionProperty(int n) {
        /*
        var x = (positions[n].get().getRow()*2)-1;
        var y = (positions[n].get().getCol()*2)-1;

        positions[n].set(new Position(x,y));

         */
        return positions[n];
    }


    /**
     * Makes a string from the position of the red and blue balls.
     * This way it is easier to print.
     */
    @Override
    public String toString() {
        var sj = new StringJoiner(",", "[", "]");

        sj.add(positions[redBall].get().toString());
        sj.add(positions[blueBall].get().toString());

        return sj.toString();
    }

    /**
     * Clone a PuzzleState by deepClone the balls position.
     */

    @Override
    public PuzzleState clone() {
        PuzzleState copy;
        try {
            copy = (PuzzleState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        copy.positions[redBall].set(deepClone(positions[redBall].get()));
        copy.positions[blueBall].set(deepClone(positions[blueBall].get()));
        //copy.blueBall = deepClone(blueBall);

        return new PuzzleState(positions[redBall].get(), positions[blueBall].get());
    }

    private static Position deepClone(final Position a) {
        Position copy;
        copy = a.clone();

        return copy;
    }

    /**
     * @return The hashCode of the balls array.
     */
    public int hashC() {
        Position[] balls = {positions[redBall].get(), positions[blueBall].get()};
        return Arrays.hashCode(balls);
    }


    /*
    public void printState() {

        int bs = PuzzleState.BoardSize+PuzzleState.BoardSize-1;
        String[][] matrix = new String[bs][bs];

        for(int i = 0;i<bs;i++) {
            for(int j = 0;j<bs;j++){
                if(j%2 == 0 && i%2 == 0) {
                    matrix[i][j] = " . ";
                }
                else matrix[i][j] = "   ";
            }
        }

        for(Wall w:this.walls) {

            int indexX = (w.getPosition().getRow()*2)-1+
                        (w.getDirection().getRowChange()-1);

            int indexY = (w.getPosition().getCol()*2)-1+
                        (w.getDirection().getColChange()-1);

            if(w.getDirection() == Direction.DOWN ||
               w.getDirection() == Direction.UP)
            {
                matrix[indexX]
                        [indexY] = " - ";
            }
            else {
                matrix[indexX]
                        [indexY] = " | ";
            }

        }
        matrix[(this.RedHole.getRow()-1)*2]
              [(this.RedHole.getCol()-1)*2] = " O ";

        matrix[(this.BlueHole.getRow()-1)*2]
              [(this.BlueHole.getCol()-1)*2] = " U ";
        matrix[(this.RedBall.getRow()-1)*2]
              [(this.RedBall.getCol()-1)*2] = " * ";
        matrix[(this.BlueBall.getRow()-1)*2]
              [(this.BlueBall.getCol()-1)*2] = " # ";

        for (String[] rows : matrix)
        {
            for (String a : rows)
            {
                System.out.print(a);
            }
            System.out.println();
        }
    }

     */

}


/**
 * Represent a wall by a given {@code Position} and a {@code Direction}.
 *
 * @param position {@link Position}
 * @param direction {@link Direction}
 */
record Wall(Position position, Direction direction) {

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
