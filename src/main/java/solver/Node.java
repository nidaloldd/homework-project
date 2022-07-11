package solver;

import puzzle.Direction;
import puzzle.PuzzleState;


import java.util.EnumSet;

public class Node {
    /**
     * A state of the puzzle which is describes
     * the Positions of the balls, walls and holes.
     */
    private final PuzzleState state;
    /**
     * A set for the possible directions where the player can move.
     */
    private final EnumSet<Direction> operators;

    /**
     * Store the parent {@code Node}.
     * It is necessary for searching back a Solved puzzle state.
     */
    private Node parent;

    /**
     * {@link Direction}.
     */
    private Direction direction;

    /**
     * Creates a {@code Node} object from a given PuzzleState.
     * @param stateP {@link PuzzleState}
     */
    public Node(final PuzzleState stateP) {
        state = stateP;
        operators = state.getLegalMoves();
    }

    /**
     * Creates a {@code Node} object from a given values like PuzzleSate,
     * ParentNode and Direction.
     * @param stateP {@link PuzzleState}
     * @param parentP {@link Node}
     * @param directionP {@link Direction}
     */
    public Node(final PuzzleState stateP, final Node parentP,
                final Direction directionP) {
        this(stateP);
        parent = parentP;
        direction = directionP;
    }

    /**
     *
     * @return the puzzle state of the Node.
     */
    public PuzzleState getState() {
        return state;
    }

    /**
     *
     * @return the parent node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     *
     * Check if the set of the operators is empty.
     * @return If the node has next child.
     */
    public boolean hasNextChild() {
        return !operators.isEmpty();
    }

    /**
     * Calculating the next child of the node.
     * @return A new Node with a new puzzle state.
     */
    public Node nextChild() {
        if (!hasNextChild()) {
            return null;
        }
        var iterator = operators.iterator();
        var directionP = iterator.next();
        iterator.remove();
        PuzzleState newState = state.clone();
        newState.move(directionP);
        return new Node(newState, this, directionP);
    }

    /**
     * @return If the given node is equal.
     */
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return false;
        }
        return (o instanceof Node n) && state.equals(n.getState());
    }

    /**
     *
     * @return !
     */
    @Override
    public int hashCode() {
        return state.hashC();
    }

    /**
     *
     * @return the string format of the node.
     */
    @Override
    public String toString() {
        return parent != null
                ? String.format("%s %s", direction, state)
                : state.toString();
    }

}
