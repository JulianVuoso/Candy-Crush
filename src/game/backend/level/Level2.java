package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.*;

public class Level2 extends Grid {
    private static int REQUIRED_SCORE = 50000;
    private static int MAX_MOVES = 20;
    private static int GAP_ROW = (SIZE-1)/2;

    @Override
    protected GameState newState() {
        return new Level2.Level2State(REQUIRED_SCORE, MAX_MOVES);
    }

    @Override
    protected void fillCells() {
        //gaps
        for (int j = 1; j < SIZE-1; j++) {
            g()[GAP_ROW-1][j].setContent(new Gap());
            g()[GAP_ROW+1][j].setContent(new Gap());
        }
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

    private class Level2State extends GameState {
        private long requiredScore;
        private long maxMoves;

        public Level2State(long requiredScore, int maxMoves) {
            this.requiredScore = requiredScore;
            setMoves(maxMoves);
        }

        public boolean gameOver() {
            return playerWon() || getMoves() <= 0;
        }

        public boolean playerWon() {
            return getScore() > requiredScore;
        }
    }
}
