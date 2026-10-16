package newpackage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SettingsWindow {

    public static void show(Stage stage, HomepageWindow hmp, StackPane root, MediaPlayer mp, VBox buttons) {
        
        Rectangle r = new Rectangle(1950, 980); r.setFill(Color.BLACK);  r.setOpacity(0.5);
        root.getChildren().add(r);
        buttons.setMouseTransparent(true);

        StackPane overlay = new StackPane();
        overlay.setStyle(
            "-fx-background-color: rgba(0,0,0,0.75);" +
            "-fx-border-color: red;" +
            "-fx-border-width: 3;" +
            "-fx-border-radius: 15;" +
            "-fx-background-radius: 15;"
        );
        overlay.setPadding(new Insets(20));
        overlay.setMaxWidth(900);
        overlay.setMaxHeight(300);

        Label volumeLabel = new Label("VOLUME");
        volumeLabel.setStyle("-fx-text-fill: red; -fx-font-size: 22px; -fx-font-weight: bold;");

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(800);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal)->mp.setVolume(newVal.doubleValue() / 100.0));
        volumeSlider.setCursor(Cursor.HAND);

        Button logout = new Button("LOG OUT"); logout.setCursor(Cursor.HAND);
        logout.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 18px;" +
                "-fx-border-color: red;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 20 8 20;"
        );

        logout.setOnAction(e -> {
            CurrentUser.logout();
            hmp.updateTopBar(stage);
            root.getChildren().remove(r);
            root.getChildren().remove(overlay);
            buttons.setMouseTransparent(false);
        });

        Button close = new Button("CLOSE"); close.setCursor(Cursor.HAND);
        close.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 18px;" +
                "-fx-border-color: red;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 20 8 20;"
        );

        close.setOnAction(e -> {
            root.getChildren().remove(r);
            root.getChildren().remove(overlay);
            buttons.setMouseTransparent(false);
        });

        VBox container = new VBox(20, volumeLabel, volumeSlider, logout, close);
        container.setAlignment(Pos.CENTER);

        overlay.getChildren().add(container);

        root.getChildren().add(overlay);
    }
}


