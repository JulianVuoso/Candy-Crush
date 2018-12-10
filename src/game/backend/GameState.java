package game.backend;

public abstract class GameState {
	
	private long score;
	private long requiredScore;
	private int moves;
	protected int extraCount;
	protected long lastScore;

	protected GameState(long requiredScore, int maxMoves){
		this.requiredScore = requiredScore;
		this.moves = maxMoves;
	}

	public void addScore(long value) {
		lastScore += value;
		this.score = this.score + value;
	}
	
	protected long getScore(){
		return score;
	}

	protected long getRequiredScore(){
		return requiredScore;
	}

	public String getStatus() {
		return String.format("Moves: %d\t\t%d/%d", moves, score, requiredScore);
	}

	public long getLastScore() {
		return lastScore;
	}

	public void resetLastScore() {
		lastScore = 0;
	}

	public void setExtraCount(int extraCount){
		this.extraCount = extraCount;
	}

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
