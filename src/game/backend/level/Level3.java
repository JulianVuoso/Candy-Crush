package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Gap;
import game.backend.element.Wall;

public class Level3 extends Grid {

    private static int REQUIRED_SCORE = 5000;
    private static int MAX_MOVES = 20;
    private static int INITIAL_JELLY_COUNT = 25;

    @Override
    protected GameState newState() {
        return new Level3.Level3State(REQUIRED_SCORE, MAX_MOVES, INITIAL_JELLY_COUNT);
    }

    @Override
    protected void fillCells() {
        //central cells jelly

        for (int i = 0; i < (SIZE-1)/2; i++) {
            for (int j = i; j < SIZE-i; j++) {
                g()[i][j].shiftJelly();
                g()[SIZE-1-i][j].shiftJelly();
            }
        }
        g()[(SIZE-1)/2][0].shiftJelly();
        g()[(SIZE-1)/2][SIZE-1].shiftJelly();
       super.fillCells();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            state().addMove();
        }
        return ret;
    }

    private class Level3State extends GameState {
        private long requiredScore;
        private long maxMoves;

        public Level3State(long requiredScore, int maxMoves, int jellyCount) {
            this.requiredScore = requiredScore;
            this.maxMoves = maxMoves;
            this.jellyCount = jellyCount;
        }

        public boolean gameOver() {
            return playerWon() || getMoves() >= maxMoves;
        }

        public boolean playerWon() {
            return getScore() > requiredScore && jellyCount == 0;
        }
    }
}