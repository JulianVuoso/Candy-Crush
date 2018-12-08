package game.backend.move;

import game.backend.Grid;
import game.backend.element.Bomb;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.WrappedCandy;

public class BombWrappedMove extends Move {

	public BombWrappedMove(Grid grid) {
		super(grid);	
	}
	
	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(i1, j1) instanceof Bomb ? get(i2, j2) : get(i1, j1));
		CandyColor color = candy.getColor();
		clearContent(i1, j1);
		clearContent(i2, j2);
		for(int i = 0; i < Grid.SIZE; i++) {
			for(int j = 0; j < Grid.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					setContent(i, j, createWrapped(color));
				}
			}
		}
		wasUpdated();
		for(int i = 0; i < Grid.SIZE; i++) {
			for(int j = 0; j < Grid.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					clearContent(i, j);
				}
			}
		}
	}

	private Candy createWrapped(CandyColor color) {
		Candy c = new WrappedCandy();
		c.setColor(color);
		return c;
	}

}
