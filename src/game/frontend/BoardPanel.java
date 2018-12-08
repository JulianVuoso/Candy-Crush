package game.frontend;

import javafx.scene.Group;
import javafx.scene.effect.Blend;
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
	
	public void setImage(int row, int column, Image image1, Image image2) {
		ImageView top = new ImageView(image2);
		ImageView bottom = new ImageView(image1);
		top.setBlendMode(BlendMode.ADD);
		Group blend = new Group(bottom, top);
		cells[row][column].getChildren().clear();
		cells[row][column].getChildren().addAll(blend);
	}

	public void setImage(int row, int column, Group blend){
		cells[row][column].getChildren().clear();
		cells[row][column].getChildren().addAll(blend);
	}
}