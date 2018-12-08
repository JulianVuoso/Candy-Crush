package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	protected int jellyCount = 0;
	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}

	public String getStatus(){
		return String.valueOf(getScore());
	}

	public void setJellyCount(int jellyCount){
		this.jellyCount = jellyCount;
	}

	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public abstract boolean gameOver();
	
	public abstract boolean playerWon();

	public void removeJelly(){
		if (jellyCount != 0){
			jellyCount--;
		}
	}
}
