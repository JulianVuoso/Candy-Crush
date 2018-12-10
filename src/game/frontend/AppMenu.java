package game.frontend;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class AppMenu extends MenuBar {

    static final int LEVELS = 3;

    public AppMenu() {

        Menu file = new Menu("Juego");
        MenuItem exitMenuItem = new MenuItem("Salir");

        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir del juego");
            alert.setContentText("¿Está seguro que desea salir del juego?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        });

        Stage stage = GameApp.getStage();
        for (int i = 1; i <= LEVELS; i++) {
            String levelTitle = String.format("Nivel %d", i);
            MenuItem levelItem = new MenuItem(levelTitle);
            int level = i;

            levelItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(levelTitle);
                alert.setHeaderText(String.format("Nueva partida: Nivel %d", level));
                alert.setContentText(String.format("¿Está seguro que desea abandonar su partida?\nComenzara una nueva en el Nivel %d.", level));
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()) {
                    if (result.get() == ButtonType.OK) {
                        GameApp.level(stage, level);
                    }
                }
            });
            file.getItems().add(levelItem);
        }
        file.getItems().add(exitMenuItem);

        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Candy TPE");
            alert.setContentText("Cátedra POO 2018.\n" + "Implementación Original: Laura Zabaleta (POO 2013).");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);
        getMenus().addAll(file, help);
    }

}
