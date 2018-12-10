package game.backend.cell;

import game.backend.Grid;
import game.backend.element.*;
import game.backend.move.BombMove;
import game.backend.move.Direction;

public class Cell {
	
	private Grid grid;
	private Cell[] around = new Cell[Direction.values().length];
	private Element content;
	private Element extra;
	
	public Cell(Grid grid) {
		this.grid = grid;
		this.content = new Nothing();
	}
	
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	public boolean hasExtra() {
		return extra != null;
	}

	public void setExtra(Element e) {
		extra = e;
	}

	public void breakExtra() {
		extra = null;
	}

	public Element getExtra() { return extra; }

	public boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}
	
	public boolean isMovable(){
		return content.isMovable();
	}
	
	public boolean isEmpty() {
		return !content.isSolid();
	}

	public boolean isHole() {
		return content.isHole();
	}

	public Element getContent() {
		return content;
	}

	public void clearContent() {
		if (content.isMovable()) {
			Direction[] explosionCascade = content.explode();
			grid.cellExplosion(content);
			// Se remueve el extra si corresponde hacerlo
			if (hasExtra() && grid.isStarted()) {
				breakExtra();
                grid.removeExtra();
			}
			this.content = new Nothing();
			if (explosionCascade != null) {
				expandExplosion(explosionCascade); 
			}
			this.content = new Nothing();
		}
	}
	
	private void expandExplosion(Direction[] explosion) {
		for(Direction d: explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}
	
	private void explode(Direction d) {
		// Si una bomba es explotada en una explosion, debe explotar caramelos de un color
		// Como el vector de explosion es null, sino no hace reaccion en cadena
		if (content instanceof Bomb) {
			clearBomb();
		}
		clearContent();
		if (this.around[d.ordinal()] != null) {
			this.around[d.ordinal()].explode(d);
		}
	}

	private void clearBomb() {
		CandyColor[] vector = CandyColor.values();
		Candy candy = new Candy(vector[(int)(Math.random() * CandyColor.values().length)]);
		new BombMove(grid).clearEquals(candy);
	}

	public Element getAndClearContent() {
		if (content.isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}

	public boolean fallUpperContent() {
		if (!this.isEmpty()) {
			return false;
		}
		// Se busca la primera celda que no sea un agujero
		Cell up = around[Direction.UP.ordinal()];
		while (up.isHole()) {
			up = up.around[Direction.UP.ordinal()];
		}

		if (!up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			grid.wasUpdated();
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		}
		return false;
	}

	public void setContent(Element content) {
		this.content = content;
	}

}
