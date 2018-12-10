package game.frontend;

import game.backend.CandyGame;
import game.backend.Grid;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameApp extends Application {

	private static final double LABEL_CELL = 0.4; // Relacion entre tamaño de label y tamaño de cell
	private static final int BUTTON_X = 130;
	private static final int BUTTON_Y = 460;
	private static final int BUTTON_OFFSET = 80;
	static final int LEVELS = 3;

	private Pane root = new Pane();
	public static Stage stage = new Stage();
	private TonesManager tones = new TonesManager();
	private  Stage primaryStage;
	public static int lvl;

	public static void main(String[] args) { launch(args); }

	public static Stage getStage(){ return stage; }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		setScene();
		List<Class> levels = new ArrayList<>(Arrays.asList(Level1.class, Level2.class, Level3.class));
		setButtons(levels);
		tones.playTone("intro", 0.4, MediaPlayer.INDEFINITE);
	}

	public static void level (Stage primaryStage, int level_in){
		CandyGame game = new CandyGame();
		List<Class> levels = new ArrayList<>(Arrays.asList(Level1.class, Level2.class, Level3.class));
		lvl = level_in;

		String title = String.format("Candy Crush Saga - Level %d", levels.indexOf(levels.get(lvl-1)) + 1);

		game.setLevelClass(levels.get(lvl-1));

		CandyFrame frame = new CandyFrame(game);
		Scene scene2 = new Scene(frame);
		primaryStage.setTitle(title);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene2);
	}

	private void setScene() {
		final ImageView background = new ImageView("images/burblebackground.png");
		primaryStage.getIcons().add(background.getImage());
		root.getChildren().add(background);
		Scene scene = new Scene(root, CandyFrame.CELL_SIZE * Grid.SIZE,CandyFrame.CELL_SIZE * (Grid.SIZE + CandyFrame.MENU_CELL + LABEL_CELL)); // 715 * 773.5
		primaryStage.setScene(scene);
		primaryStage.setTitle("Candy Crush Saga");
		stage = primaryStage;
		scene.setCursor(new ImageCursor(new Image("images/cursor.png"), 100,100));
		primaryStage.show();
	}

	private void setButtons(List<Class> levels) {
		DropShadow bShadow = new DropShadow();
		List<Button> bList = new ArrayList<>();

		for (int i = 0; i < LEVELS; i++) {
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

		for (int i = 0; i < bList.size(); i++) {
			String title = String.format("Candy Crush Saga - Level %d", i+1);
			int lvlAux = i;
			bList.get(i).setOnAction(e->{
				CandyGame game = new CandyGame(levels.get(lvlAux));
				CandyFrame frame = new CandyFrame(game);
				Scene scene2 = new Scene(frame);
				primaryStage.setTitle(title);
				primaryStage.setResizable(false);
				primaryStage.setScene(scene2);
				lvl = lvlAux + 1;
			});
		}
	}

}
