package game.frontend;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class AppMenu extends MenuBar {

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

        MenuItem level1item = new MenuItem("Nivel 1");
        Stage stage = GameApp.getStage();
        level1item.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nivel 1");
            alert.setHeaderText("Nueva partida: Nivel 1");
            alert.setContentText("¿Está seguro que desea abandonar su partida?\nComenzara una nueva en el Nivel 1.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    GameApp.level(stage, 1);
                }
            }
        });
        MenuItem level2Item = new MenuItem("Nivel 2");
        level2Item.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nivel 2");
            alert.setHeaderText("Nueva partida: Nivel 2");
            alert.setContentText("¿Está seguro que desea abandonar su partida?\nComenzara una nueva en el Nivel 2.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    GameApp.level(stage, 2);
                }
            }
        });
        MenuItem level3Item = new MenuItem("Nivel 3");
        level3Item.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nivel 3");
            alert.setHeaderText("Nueva partida: Nivel 3");
            alert.setContentText("¿Está seguro que desea abandonar su partida?\nComenzara una nueva en el Nivel 3.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    GameApp.level(stage, 3);
                }
            }
        });
        file.getItems().addAll(level1item,level2Item,level3Item,exitMenuItem);

        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Candy TPE");
            alert.setContentText("Cátedra POO 2018.\n" +
                    "Implementación Original: Laura Zabaleta (POO 2013).");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);

        getMenus().addAll(file, help);
    }

}
