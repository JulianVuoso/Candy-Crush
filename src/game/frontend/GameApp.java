package game.frontend;

import game.backend.CandyGame;
import game.backend.cell.Cell;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameApp extends Application {

	public static void main(String[] args) { launch(args); }

	public static Stage stage = new Stage();
	public static Stage getStage(){ return stage; }

	@Override
	public void start(Stage primaryStage) {

		final Image homeScreen = new Image( "images/burblebackground.png" );
		final ImageView flashScreen_node = new ImageView();
		flashScreen_node.setImage(homeScreen);
		primaryStage.getIcons().add(homeScreen);
		Pane root = new Pane();
		root.getChildren().addAll(flashScreen_node);
        DropShadow bShadow = new DropShadow();

        List<Class> levels = new ArrayList<>();
		List<Button> bList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Button b = new Button();
			b.setGraphic(new ImageView(String.format("images/lvl%d#y.png", i+1)));
			b.setLayoutY(460 + i * 80);
			b.setLayoutX(130);
			b.setStyle("-fx-background-color: transparent");
			b.setEffect(bShadow);
			b.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> b.setEffect(null));
			b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setEffect(bShadow));
			bList.add(b);
			root.getChildren().add(b);
		}

		Scene scene = new Scene(root, CandyFrame.CELL_SIZE * 11,CandyFrame.CELL_SIZE * (11 + 0.5 + 0.4)); // 65*11 = 715
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
				Scene scene2 = new Scene(frame);
				primaryStage.setTitle(title);
				primaryStage.setResizable(false);
				primaryStage.setScene(scene2);
				lvl = lvlAux + 1;
			});
		}
	}

	public static int lvl;

	public static void level (Stage primaryStage, int level_in){
		lvl = level_in;
		CandyGame game = new CandyGame();
		switch (lvl){
			case 1: game.setLevelClass(Level1.class); break;
			case 2: game.setLevelClass(Level2.class); break;
			case 3: game.setLevelClass(Level3.class); break;
			default: break;
		}
		CandyFrame frame = new CandyFrame(game);
		Scene scene2 = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene2);
	}

}
