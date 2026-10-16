package newpackage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TutorialWindow {

    private Scene scene;

    public TutorialWindow(Stage stage, HomepageWindow homepage) {

        Font StrangePath = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 70);
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 30);

        BackgroundImage tutorialBG = new BackgroundImage(
            new Image(getClass().getResource("images/DarkMetalPlate.png").toExternalForm()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        VBox content = new VBox(25);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("HOW TO PLAY:");
        title.setFont(StrangePath);
        title.setTextFill(Color.rgb(200, 40, 40));

        Label body = new Label(
                "You have 3 lives each level.\n" +
                "Every mistake moves the robot closer to full control.\n" +
                "Three mistakes = game over!.\n\n" +

                "Tasks You’ll Face:\n" +
                " • Camera Locks: Find the password in the office and unscramble a word to unlock cameras.\n" +
                " • Code Sabotage: Compare the code on your screen with the original on the copybook and fix the corrupted line.\n" +
                " • Surveillance Shutdown: Reboot the system by choosing the correct sequence of actions.\n" +
                " • System Overheat: Rapidly press the space bar to cool the system before it shuts down.\n" +
                " • Lights Out: Reconnect the colored wires to restore the lights.\n" +
                " • Time Distortion: Enter the correct current time when the robot corrupts the clock.\n\n" +

                "Stay aware of your cameras, the systems, and the time.\n" +
                "React quickly [the robot adapts with every mistake].\n\n" +
                "Good luck, Operator."
        );
        body.setFont(VCR_OSD_MONO);
        body.setTextFill(Color.WHITE);
        body.setWrapText(true);

        Button backBtn = new Button("BACK");
        backBtn.setFont(VCR_OSD_MONO);
        backBtn.setStyle("-fx-background-color: black;\n" +
                        "    -fx-border-color: #ff0000;\n" +
                        "    -fx-border-width: 3px;\n" +
                        "    -fx-text-fill: #ff0000;"
                        + "     -fx-cursor: hand;"
                + "-fx-text-fill: #ff0000;");
        backBtn.setOnAction(e -> stage.setScene(homepage.getScene()));

        content.getChildren().addAll(title, body, backBtn);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:black; -fx-border-color:black;");

        StackPane root = new StackPane(scrollPane);
        root.setBackground(new Background(tutorialBG));

        scene = new Scene(root, 1950, 980);
        scene.setFill(Color.BLACK);
    }

    public Scene getScene() {
        return scene;
    }
}