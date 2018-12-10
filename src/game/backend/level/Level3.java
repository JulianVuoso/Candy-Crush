package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.*;

public class Level3 extends Grid {

    private static final int REQUIRED_SCORE = 10000;
    private static final int MAX_MOVES = 35;
    private static final int JELLY_COUNT = 72;

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
        state().setExtraCount(JELLY_COUNT);
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
        public Level3State(long requiredScore, int maxMoves) {
            super(requiredScore, maxMoves);
        }

        public boolean gameOver() {
            return playerWon() || getMoves() <= 0;
        }

        public boolean playerWon() {
            return getScore() > getRequiredScore() && extraCount == 0;
        }

        @Override
        public String getStatus(){
            return super.getStatus() + "\t\tJellys: " + extraCount;
        }
    }
}