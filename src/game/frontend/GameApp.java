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
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
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

		final Image homeScreen = new Image( "images/burblebackground.png" );
		final ImageView flashScreen_node = new ImageView();
		flashScreen_node.setImage(homeScreen);
		primaryStage.getIcons().add(homeScreen);
		Group root = new Group();
		root.getChildren().addAll(flashScreen_node);

		Pane root1 = new Pane();
		root1.getChildren().addAll(flashScreen_node);

        DropShadow bshadow = new DropShadow();

		Button button1 = new Button();
		Image lvl1 = new Image("images/lvl1#y.png");
		button1.setGraphic(new ImageView(lvl1));
		button1.setLayoutX(130);
		button1.setLayoutY(460);
        button1.setStyle("-fx-background-color: transparent");
        button1.setEffect(bshadow);

        Button button2 = new Button();
		Image lvl2 = new Image("images/lvl2#y.png");
		button2.setGraphic(new ImageView(lvl2));
		button2.setLayoutX(130);
		button2.setLayoutY(540);
        button2.setStyle("-fx-background-color: transparent");
        button2.setEffect(bshadow);

        Button button3 = new Button();
		Image lvl3 = new Image("images/lvl3#y.png");
		button3.setGraphic(new ImageView(lvl3));
		button3.setLayoutX(130);
		button3.setLayoutY(620);
        button3.setStyle("-fx-background-color: transparent");
        button3.setEffect(bshadow);

        root1.getChildren().addAll(button1, button2, button3);

		button1.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button1.setEffect(null);
					}
				});

		button1.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button1.setEffect(bshadow);
					}
				});
		button2.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button2.setEffect(null);
					}
				});

		button2.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button2.setEffect(bshadow);
					}
				});
		button3.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button3.setEffect(null);
					}
				});

		button3.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						button3.setEffect(bshadow);
					}
				});

		Scene scene = new Scene(root1, 65*11,774); // 65*11 = 715
		primaryStage.setScene(scene);
		primaryStage.setTitle("Candy Crush Saga");
		stage = primaryStage;
		primaryStage.show();

		final String CANDY = "Candy Crush Saga - Level ";

		button1.setOnAction(e->{
			CandyGame game = new CandyGame(Level1.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setTitle( CANDY + "1" );
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
		button2.setOnAction(e->{
			CandyGame game = new CandyGame(Level2.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setTitle( CANDY + "2" );
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
		button3.setOnAction(e->{
			CandyGame game = new CandyGame(Level3.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene2 = new Scene(frame);
			primaryStage.setTitle( CANDY + "3" );
			primaryStage.setResizable(false);
			primaryStage.setScene(scene2);
		});
	}

	public static int lvl=1;

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
