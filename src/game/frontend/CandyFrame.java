package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.Element;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.Optional;


public class CandyFrame extends VBox {

	static final int CELL_SIZE = 65;
	private static final int FRAME_GAP = 100;

	private static MediaPlayer mediaPlayer;

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
		game.initGame();
		scorePanel = new ScorePanel(game.getStatus());
		getChildren().add(scorePanel);
		GameListener listener;
		game.addGameListener(listener = new GameListener() {

			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(FRAME_GAP);
				Duration frameTime = Duration.ZERO;
				for (int i = game().getSize() - 1; i >= 0; i--) {
					for (int j = game().getSize() - 1; j >= 0; j--) {
						int finalI = i;
						int finalJ = j;
						Cell cell = CandyFrame.this.game.get(i, j);
						Group blend = images.getImage(cell);
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, blend)));
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
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("¡ERROR!");
				alert.setHeaderText("MOVIMIENTO EQUIVOCADO.");
				alert.setContentText("Para realizar un movimiento, \ndebe arrastrar de un punto a otro. ");
				alert.showAndWait();
				return;
			}

			clickPoint = translateCoords(event.getX(), event.getY());
			System.out.println("Get end = " +  clickPoint);

			if (clickPoint != null) {
				game().tryMove((int)dragPoint.getX(), (int)dragPoint.getY(), (int)clickPoint.getX(), (int)clickPoint.getY());
				String message = game().getStatus();
				soundMaker(game().getLastScore());
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

	private void soundMaker(long score) {
		final String TONE_PATH = "/tones/";
		String musicFile;
		if (score < 500) {
			return;
		} else if (score < 850) {
			musicFile = "tasty.mp3";
		} else if (score < 1200) {
			musicFile = "delicious.mp3";
		} else {
			musicFile = "divine.mp3";
		}

		Media sound = null;
		try {
			sound = new Media(getClass().getResource(TONE_PATH + musicFile).toURI().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();

	}

	public void setMediaPlayer(MediaPlayer mp) {
		mediaPlayer = mp;
	}
}
