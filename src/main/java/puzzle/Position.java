package puzzle;

import java.util.Objects;

/**
 * Represents a 2D position by a row and a column value.
 */
public class Position implements Cloneable {
    /**
     * The column value of the position.
     */
    private int col;
    /**
     * The row value of the position.
     */
    private int row;

    /**
     * @return The row coordinate of the position.
     */
    public int getRow() {
        return row;
    }
    /**
     * @return The column coordinate of the position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Set the row coordinate of the position.
     * @param rowP the value we want to replace the row coordinate.
     */
    public void setRow(final int rowP) {
        row = rowP;
    }
    /**
     * Set the column coordinate of the position.
     * @param colP the value we want to replace the row coordinate.
     */
    public void setCol(final int colP) {
        col = colP;
    }

    /**
     * Creates a {@link Position} object.
     *
     * @param rowP the row coordinate of the position.
     * @param colP the column coordinate of the position.
     */
    public Position(final int rowP, final int colP) {
        row = rowP;
        col = colP;
    }

    public void setPosition(final int rowP, final int colP) {
        row = rowP;
        col = colP;
    }

    /**
     * Changes the position by one toward the given direction.
     *
     * @param direction {@link Direction} that specifies a
     *                                  change in the coordinates.
     *
     * @return The new {@link Position} after the row and the column changes.
     */
    public Position stepOneToDirection(final Direction direction) {
        return new Position(row + direction.getRowChange(),
                            col + direction.getColChange());
    }

    /**
     *
     *
     * @return If the given Position is On the board.
     */
    public boolean onBoard() {
        return row < PuzzleState.BOARD_SIZE
                && col < PuzzleState.BOARD_SIZE
                && row > -1
                && col > -1;
    }

    /**
     * The function compares a Position row and column value.
     * @return If the Position is equal.
     */
    @Override
    public boolean equals(final Object o) {
        return (o instanceof Position p) && p.row == row && p.col == col;
    }

    /**
     * Use object hash to the row and column value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Deep clone a Position object.
     */
    @Override
    public Position clone() {
        Position copy;
        try {
            copy = (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        return copy;
    }

    /**
     * Make a String out of the Position.
     * The format of the String will be like this (rowValue,columnValue).
     */

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}
