package solver;
//package-info.java

import puzzle.PuzzleState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a class for searching a tree data structure for a {@link Node}
 * that satisfies a given property.
 */
public class BreadthFirstSearch {

    /**
     * This list will contain the PuzzleStates,
     * which are lead to the solution of the puzzle.
     */
    private static final List<PuzzleState> SOL_STATES = new ArrayList<>() { };

    /**
     * This function can search the possible solution of the Puzzle.
     * @param state {@link PuzzleState}.
     * @return The {@link Node} of the solved {@link PuzzleState}.
     * If there is no solution of the puzzle return null.
     */
    public Node search(final PuzzleState state) {
        var open = new LinkedList<Node>();
        var seen = new HashSet<Node>();
        var start = new Node(state);
        open.add(start);
        seen.add(start);
        while (!open.isEmpty()) {
            var selected = open.getFirst();
            if (selected.getState().isSolved()) {
                return selected;
            }
            open.removeFirst();
            while (selected.hasNextChild()) {
                Node nextChild = selected.nextChild();
                if (!seen.contains(nextChild)) {
                    open.addLast(nextChild);
                    seen.add(nextChild);
                }
            }
        }
        return null;
    }

    /**
     * This function is gets the parents of the given Node
     * until the root Node and print the path.
     * Helps print the puzzle solution step by step.
     * @param node {@link Node}
     */
    public void printPath(final Node node) {
        if (node.getParent() != null) {
            printPath(node.getParent());
        }
        System.out.println(node);
        SOL_STATES.add(node.getState());
    }

    /**
     * main function which is doing the Search of the solution and print it.
     * @param args
     */
    public static void main(final String[] args) {
        var bfs = new BreadthFirstSearch();
        var result = bfs.search(
                new PuzzleState(PuzzleState.START_RED_POS,
                                PuzzleState.START_BLUE_POS)
        );
        if (result != null) {
            System.out.println("Solution:");
            bfs.printPath(result);
        } else {
            System.out.println("No solution");
        }

        for (PuzzleState s : BreadthFirstSearch.SOL_STATES) {
            System.out.println("---------------------------------------");
            //s.printState();
            System.out.println("---------------------------------------");
            try {
                final int sleepTime = 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


}


