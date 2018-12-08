package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.Element;

import game.backend.element.Jelly;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Optional;

import static game.frontend.GameApp.lvl;

public class CandyFrame extends VBox {

	private static final int CELL_SIZE = 65;

	private BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private ImageManager images;
	private Point2D dragPoint = null;

	private Point2D clickPoint = null;
	private CandyGame game;

	public CandyFrame(CandyGame game) {
		this.game = game;
		getChildren().add(new AppMenu());
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);
		game.initGame();
		GameListener listener;
		game.addGameListener(listener = new GameListener() {

			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(250);
				Duration frameTime = Duration.ZERO;
				for (int i = game().getSize() - 1; i >= 0; i--) {
					for (int j = game().getSize() - 1; j >= 0; j--) {
						int finalI = i;
						int finalJ = j;
						Cell cell = CandyFrame.this.game.get(i, j);
						Element element = cell.getContent();
						Image image = images.getImage(element);
						// corregir esto

						if(!cell.hasJelly()) {
							timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image, null)));
						} else {
							timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image, images.getImage(new Jelly()))));
						}
					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}

			@Override
			public void cellExplosion(Element e) {
				//
			}
		});

		addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
			dragPoint = translateCoords(event.getX(), event.getY());
			if (dragPoint != null && clickPoint == null) {
				System.out.println("Get start = " + dragPoint);
			}
		});

		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (dragPoint == null) {
				System.out.println("You have to drag and drop");
				return;
			}

			clickPoint = translateCoords(event.getX(), event.getY());
			System.out.println("Get end = " +  clickPoint);

			if (clickPoint != null) {
				game().tryMove((int)dragPoint.getX(), (int)dragPoint.getY(), (int)clickPoint.getX(), (int)clickPoint.getY());
					String message = ((Long)game().getScore()).toString() + " " + game().getJellys();
				if (game().isFinished()) {
					if (game().playerWon()) {
						message = message + " Puntaje obtenido. ¡Ganaste! ";
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Nivel ganado");
						alert.setHeaderText("¡FELICITACIONES! Ha ganado el Nivel");
						alert.setContentText("¿Desea continuar?");
						Optional<ButtonType> result = alert.showAndWait();
						if(result.isPresent()) {
							if (result.get() == ButtonType.OK) {
								if (GameApp.lvl < 3){
									GameApp.level(GameApp.getStage(), GameApp.lvl+1);
								} else Platform.exit();
							}
							if(result.get() == ButtonType.CANCEL){
								Platform.exit();
							}
						}
					} else {
						message = message + " El Nivel no ha sido superado. ¡Perdiste!";
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Nivel perdido");
						alert.setHeaderText("No ha cumplido el objetivo de este Nivel");
						alert.setContentText("¿Desea volver a intentarlo?\n Presione Cancelar para salir del juego");
						Optional<ButtonType> result = alert.showAndWait();
						if(result.isPresent()) {
							if (result.get() == ButtonType.OK) {
								GameApp.level(GameApp.getStage(), GameApp.lvl);
							}
							if(result.get() == ButtonType.CANCEL){
								Platform.exit();
							}
						}
					}
				}
				scorePanel.updateScore(message);
			}
			clickPoint = null;
			dragPoint = null;
		});
		listener.gridUpdated();
	}

	private CandyGame game() {
		return game;
	}

	private Point2D translateCoords(double x, double y) {
		double i = x / CELL_SIZE;
		double j = y / CELL_SIZE - 0.5;
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize() && !game.get((int)j, (int)i).getContent().isHole()) ? new Point2D(j, i) : null;
	}
}
