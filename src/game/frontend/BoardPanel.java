package game.frontend;

import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class BoardPanel extends TilePane {

	private HBox[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		this.cells = new HBox[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new HBox();
				getChildren().add(cells[i][j]);
			}
		}
	}
	
	public void setImage(int row, int column, Image image) {
		cells[row][column].getChildren().addAll(new ImageView(image));
	}

	public void setImage(int row, int column, Image image1, Image image2) {
		ImageView imageAux = new ImageView(image2);
		imageAux.setBlendMode(BlendMode.ADD);
		Group to = new Group(new ImageView(image1), imageAux);
		cells[row][column].getChildren().addAll(to);
	}


}