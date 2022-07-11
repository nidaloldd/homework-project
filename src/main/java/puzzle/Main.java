package puzzle;

public class Main {

    private static void main(final String[] args) {
/*

        PuzzleState puzzleState = new PuzzleState();

        int bs = PuzzleState.BoardSize+PuzzleState.BoardSize-1;
        String[][] matrix = new String[bs][bs];

        for(int i = 0;i<bs;i++){
            for(int j = 0;j<bs;j++){
                if(j%2==0 && i%2==0) {
                    matrix[i][j] = " . ";
                }
                else matrix[i][j] = "   ";
            }
        }

        for(Wall w:puzzleState.walls){

            int indexX =(w.getPosition().getRow()*2)-1
                    + (w.getDirection().getRowChange()-1);
            int indexY= (w.getPosition().getCol()*2)-1
                    + (w.getDirection().getColChange()-1);

            if(w.getDirection()==Direction.DOWN
             || w.getDirection()==Direction.UP){
                matrix[indexX]
                        [indexY] = " - ";
            }
            else {
                matrix[indexX]
                        [indexY] = " | ";
            }

        }


        puzzleState.move(Direction.UP);
        puzzleState.move(Direction.LEFT);
        puzzleState.move(Direction.UP);
        puzzleState.move(Direction.RIGHT);
        puzzleState.move(Direction.DOWN);
        puzzleState.move(Direction.RIGHT);
        puzzleState.move(Direction.UP);
        puzzleState.move(Direction.LEFT);
        puzzleState.move(Direction.UP);
        puzzleState.move(Direction.RIGHT);

        var aa = puzzleState.getLegalMoves();

        for (Direction d: aa) {
            System.out.println(d.toString());
        }


        matrix[(puzzleState.RedHole.getRow()-1)*2]
                [(puzzleState.RedHole.getCol()-1)*2] = " O ";
        matrix[(puzzleState.BlueHole.getRow()-1)*2]
                [(puzzleState.BlueHole.getCol()-1)*2]  = " U ";
        matrix[(puzzleState.RedBall.getRow()-1)*2]
                [(puzzleState.RedBall.getCol()-1)*2]  = " * ";
        matrix[(puzzleState.BlueBall.getRow()-1)*2]
                [(puzzleState.BlueBall.getCol()-1)*2]  = " # ";

        for (String[] rows : matrix)
        {
            for (String a : rows)
            {
                System.out.print(a);
            }
            System.out.println();
        }


 */

    }
}
