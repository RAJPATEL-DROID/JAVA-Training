package Server.gameboard;

public class Cell {  // save as "Cell.java"
        // Define properties (package-visible)
        /** Content of this cell (CROSS, NOUGHT, NO_SEED) */
        public  Seed content;
        /** Row and column of this cell, not used in this program */
        int row, col;

        /** Constructor to initialize this cell */
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.content = Seed.NO_SEED;
        }

        /** Reset the cell content to EMPTY, ready for a new game. */
        public void newGame() {
            this.content = Seed.NO_SEED;
        }
}

