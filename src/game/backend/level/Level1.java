package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;

public class Level1 extends Grid {
	
	private static final int REQUIRED_SCORE = 12000;
	private static final int MAX_MOVES = 20;

	@Override
	protected GameState newState() {
		return new Level1State(REQUIRED_SCORE, MAX_MOVES);
	}

	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
		}
		return ret;
	}
	
	private class Level1State extends GameState {
		public Level1State(long requiredScore, int maxMoves) {
			super(requiredScore, maxMoves);
		}
		
		public boolean gameOver() {
			return playerWon() || getMoves() <= 0;
		}
		
		public boolean playerWon() {
			return getScore() > getRequiredScore();
		}
	}

}
