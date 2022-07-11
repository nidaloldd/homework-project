package puzzle;
//package-info.java


/**
 * Represents the four directions.
 */
public enum Direction {

    /** Represent the Up direction.*/
    UP(-1, 0),
    /** Represent the RIGHT direction.*/
    RIGHT(0, 1),
    /** Represent the DOWN direction.*/
    DOWN(1, 0),
    /** Represent the LEFT direction.*/
    LEFT(0, -1);

    /**
     * Create a {@link Direction}.
     *
     * @param rowChangeP the change in the row coordinate.
     * @param colChangeP the change in the column coordinate.
     */
    Direction(final int rowChangeP, final int colChangeP) {
        this.rowChange = rowChangeP;
        this.colChange = colChangeP;
    }

    /**
     * the change in the row coordinate.
     */
    private final int rowChange;
    /**
     * the change in the column coordinate.
     */
    private final int colChange;

    /**
     * @return get the row change coordinate.
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * @return get the column change coordinate.
     */
    public int getColChange() {
        return colChange;
    }


    /**
     * Helps Represent the directions as a string.
     */
    @Override
    public String toString() {
        if (getRowChange() == -1 && getColChange() == 0) {
            return "UP";
        }
        if (getRowChange() == 0 && getColChange() == 1) {
            return "RIGHT";
        }
        if (getRowChange() == 1 && getColChange() == 0) {
            return "DOWN";
        }
        if (getRowChange() == 0 && getColChange() == -1) {
            return "LEFT";
        }
        throw new IllegalArgumentException();
    }
}
