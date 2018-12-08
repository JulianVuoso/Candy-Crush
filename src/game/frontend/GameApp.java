package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApp extends Application {

	public static String[] argu;
	public static void main(String[] args) {
		argu = args;
		launch(args);
	}

	public static Stage stage = new Stage();
	public static Stage getStage(){
		return stage;
	}


	@Override
	public void start(Stage primaryStage) {

		final Image homeScreen = new Image( "images/homebackground.png" );
		final ImageView flashScreen_node = new ImageView();
		flashScreen_node.setImage(homeScreen);
		primaryStage.getIcons().add(homeScreen);
		Group root = new Group();
		root.getChildren().addAll(flashScreen_node);

		Pane root1 = new Pane();
		root1.getChildren().addAll(flashScreen_node);

		Button button1 = new Button();
		Image lvl1 = new Image("images/lvl1.png");
		button1.setGraphic(new ImageView(lvl1));
		button1.setLayoutX(380);
		button1.setLayoutY(440);
		Button button2 = new Button();
		Image lvl2 = new Image("images/lvl2.png");
		button2.setGraphic(new ImageView(lvl2));
		button2.setLayoutX(380);
		button2.setLayoutY(500);
		Button button3 = new Button();
		Image lvl3 = new Image("images/lvl3.png");
		button3.setGraphic(new ImageView(lvl3));
		button3.setLayoutX(380);
		button3.setLayoutY(560);
		root1.getChildren().addAll(button1, button2, button3);
		DropShadow shadow = new DropShadow();

		button1.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button1.setEffect(shadow);
					}
				});

		button1.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button1.setEffect(null);
					}
				});
		button2.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button2.setEffect(shadow);
					}
				});

		button2.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button2.setEffect(null);
					}
				});
		button3.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button3.setEffect(shadow);
					}
				});

		button3.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button3.setEffect(null);
					}
				});

		Scene scene = new Scene(root1, 65*11,774);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Candy Crush !");
		stage = primaryStage;
		primaryStage.show();

		button1.setOnAction(e->{
			CandyGame game = new CandyGame(Level1.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
		button2.setOnAction(e->{
			CandyGame game = new CandyGame(Level2.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
		button3.setOnAction(e->{
			CandyGame game = new CandyGame(Level3.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
	}

	public static void level (Stage primaryStage, int level){
		CandyGame game = new CandyGame();
		switch (level){
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
