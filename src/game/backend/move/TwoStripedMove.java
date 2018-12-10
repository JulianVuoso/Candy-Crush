package game.backend.move;

import game.backend.Grid;

public class TwoStripedMove extends Move {

	public TwoStripedMove(Grid grid) {
		super(grid);
	}

	/* Se unificaron ambos ciclos en uno solo para eliminar los
	 * caramelos
	 * */
	@Override
	public void removeElements() {
		for(int i = 0; i < Grid.SIZE; i++) {
			clearContent(i, j2);
			clearContent(i2, i);
		}
	}

}
