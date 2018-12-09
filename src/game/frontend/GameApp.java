package game.frontend;

import game.backend.CandyGame;
import game.backend.Grid;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameApp extends Application {

	private static final double MENU_CELL = 0.5;
	private static final double LABEL_CELL = 0.4;
	private static final int BUTTON_X = 130;
	private static final int BUTTON_Y = 460;
	private static final int BUTTON_OFFSET = 80;

	public static void main(String[] args) { launch(args); }

	public static Stage stage = new Stage();
	private static MediaPlayer mediaPlayer;

	public static Stage getStage(){ return stage; }

	@Override
	public void start(Stage primaryStage) {

		final ImageView background = new ImageView("images/burblebackground.png");
		primaryStage.getIcons().add(background.getImage());
		Pane root = new Pane();
		root.getChildren().add(background);

		DropShadow bShadow = new DropShadow();
        List<Class> levels = new ArrayList<>();
		List<Button> bList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Button b = new Button();
			b.setGraphic(new ImageView(String.format("images/lvl%d#y.png", i+1)));
			b.setLayoutY(BUTTON_Y + i * BUTTON_OFFSET);
			b.setLayoutX(BUTTON_X);
			b.setStyle("-fx-background-color: transparent");
			b.setEffect(bShadow);
			b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(null));
			b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setEffect(bShadow));
			bList.add(b);
			root.getChildren().add(b);
		}

		Scene scene = new Scene(root, CandyFrame.CELL_SIZE * Grid.SIZE,CandyFrame.CELL_SIZE * (Grid.SIZE + MENU_CELL + LABEL_CELL)); // 715 * 773.5
		primaryStage.setScene(scene);
		primaryStage.setTitle("Candy Crush Saga");
		stage = primaryStage;
		primaryStage.show();

		levels.addAll(Arrays.asList(Level1.class, Level2.class, Level3.class));

		for (int i = 0; i < bList.size(); i++) {
			String title = String.format("Candy Crush Saga - Level %d", i+1);
			int lvlAux = i;

			bList.get(i).setOnAction(e->{
				CandyGame game = new CandyGame(levels.get(lvlAux));
				CandyFrame frame = new CandyFrame(game);
				frame.setMediaPlayer(mediaPlayer);
				Scene scene2 = new Scene(frame);
				primaryStage.setTitle(title);
				primaryStage.setResizable(false);
				primaryStage.setScene(scene2);
				lvl = lvlAux + 1;
			});
		}
		final String TONE_PATH = "/tones/";
		String musicFile = "intro.mp3";     // For example
		Media sound = null;
		try {
			sound = new Media(getClass().getResource(TONE_PATH + musicFile).toURI().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		try {
			mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setVolume(0.4);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();
		} catch (MediaException e) {
			System.out.println(e.getMessage());
		}
	}

	public static int lvl;

	public static void level (Stage primaryStage, int level_in){
		lvl = level_in;
		CandyGame game = new CandyGame();

		List<Class> levels = new ArrayList<>();
		levels.addAll(Arrays.asList(Level1.class, Level2.class, Level3.class));
		game.setLevelClass(levels.get(lvl-1));

		CandyFrame frame = new CandyFrame(game);
		Scene scene2 = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene2);
	}

}
