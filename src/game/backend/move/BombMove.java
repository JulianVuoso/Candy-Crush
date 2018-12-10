package game.backend.move;

import game.backend.Grid;
import game.backend.element.Bomb;
import game.backend.element.Candy;

public class BombMove extends Move {
	
	public BombMove(Grid grid) {
		super(grid);
	}

	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(i1, j1) instanceof Bomb ? get(i2, j2) : get(i1, j1));
		clearContent(i1, j1);
		clearContent(i2, j2);
		clearEquals(candy);
	}

}
