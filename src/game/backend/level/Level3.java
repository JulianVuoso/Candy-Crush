package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.Jelly;

public class Level3 extends Grid {

    private static int REQUIRED_SCORE = 10000;
    private static int MAX_MOVES = 30;

    @Override
    protected GameState newState() {
        return new Level3.Level3State(REQUIRED_SCORE, MAX_MOVES);
    }

    @Override
    protected void fillCells() {
        //central cells jelly
        for (int i = 0; i < (SIZE-1)/2; i++) {
            for (int j = i; j < SIZE-i; j++) {
                g()[i][j].setExtra(new Jelly());
                g()[SIZE-1-i][j].setExtra(new Jelly());
            }
        }
        g()[(SIZE-1)/2][0].setExtra(new Jelly());
        g()[(SIZE-1)/2][SIZE-1].setExtra(new Jelly());
        state().setJellyCount(72);
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

        public Level3State(long requiredScore, int maxMoves) {
            this.requiredScore = requiredScore;
            this.maxMoves = maxMoves;
            jellyCount = 0;
        }

        public boolean gameOver() {
            return playerWon() || getMoves() >= maxMoves;
        }

        public boolean playerWon() {
            return super.getScore() > requiredScore && jellyCount == 0;
        }

        @Override
        public String getStatus(){
            return super.getStatus() + "\tJellyCount: " + jellyCount;
        }
    }
}