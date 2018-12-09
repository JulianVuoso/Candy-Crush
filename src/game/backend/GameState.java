package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	protected int extraCount = 0;

	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}

	public String getStatus(){
		return String.format("Moves: %d\t\t%d", moves, score);
	}

	public void setExtraCount(int extraCount){
		this.extraCount = extraCount;
	}

	protected void setMoves(int moves){ this.moves = moves; }

	public void addMove() {
		moves--;
	}
	
	protected int getMoves() {
		return moves;
	}
	
	public abstract boolean gameOver();
	
	public abstract boolean playerWon();

	public void removeExtra(){
		if (extraCount != 0){
			extraCount--;
		}
	}
}
