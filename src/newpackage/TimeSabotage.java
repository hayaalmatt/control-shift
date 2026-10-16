package newpackage;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;

public class TimeSabotage {
    private int attempts = 3;
    private audios audios = new audios();
    private Warnings w = new Warnings();

    private boolean completed = false;
    private StackPane popup = null;
    private Button clockBtn = null;
    private StackPane gamerootRef = null;
    private LivesManager livesManagerRef = null;

    public void show(StackPane gameroot, int num, LivesManager livesManager){
        this.gamerootRef = gameroot;
        this.livesManagerRef = livesManager;
        this.completed = false;
        this.attempts = 3;

        String correctTime = num + ":00";

        Font VCR_OSD_MONO3 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 24);
        ImageView clock2tip = new ImageView(new Image(getClass().getResource("images/clockTIP2.png").toExternalForm()));

        clockBtn = new Button();
        clockBtn.setPrefSize(100, 50);
        clockBtn.setTranslateX(-260);
        clockBtn.setTranslateY(150);
        clockBtn.setCursor(Cursor.HAND);
        clockBtn.setDisable(false);
        clockBtn.setOpacity(0);
        gameroot.getChildren().add(clockBtn);

        ImageView popupBG = new ImageView(getClass().getResource("images/timeerror.png").toExternalForm());
        popupBG.setFitWidth(1400);
        popupBG.setFitHeight(700);

        ImageView clockWarning = new ImageView(new Image(getClass().getResource("images/clocksab.PNG").toExternalForm()));

        BackgroundImage officeDie = new BackgroundImage(
                new Image(getClass().getResource("images/officedie.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        BackgroundImage officeRestore = loadOfficeBackground(num);

        gameroot.setBackground(new Background(officeDie));
        w.showBanner(gameroot, "The robot has sabotaged the clock! Fix it fast!");
        w.showWarning(gameroot, clockWarning);

        TextField input = new TextField();
        input.setStyle("-fx-background-color: transparent; -fx-font-size: 20px; -fx-text-fill: white; -fx-prompt-text-fill: white;");
        input.setFont(VCR_OSD_MONO3);
        input.setMaxWidth(320);
        input.setPrefHeight(50);
        input.setTranslateY(125); input.setTranslateX(20);

        Label triesLabel = new Label("Attempts left: " + attempts);
        triesLabel.setFont(VCR_OSD_MONO3);
        triesLabel.setTranslateY(126);
        triesLabel.setTextFill(Color.BLACK);

        javafx.scene.control.Button submit = new javafx.scene.control.Button("SUBMIT");
        submit.setStyle("-fx-background-color: black;\n" +
                "    -fx-border-color: #ff0000;\n" +
                "    -fx-border-width: 3px;\n" +
                "    -fx-text-fill: #ff0000;"
                + "     -fx-cursor: hand;"
                + "-fx-text-fill: #ff0000;"
                + "-fx-font-size: 16px;");
        submit.setTranslateY(140);

        VBox vbox = new VBox(18, triesLabel, input, submit);
        vbox.setAlignment(Pos.CENTER);

        popup = new StackPane(popupBG, vbox);

        clockBtn.setOnAction(e -> {
            if (popup != null && !gameroot.getChildren().contains(popup)) {
                gameroot.getChildren().add(popup);
            }
        });

        submit.setOnAction(ev -> {
            if (completed) return;

            String txt = (input.getText() == null) ? "" : input.getText().trim();

            if (correctTime.equals(txt)) {
                completed = true;
                if (gameroot.getChildren().contains(popup)) gameroot.getChildren().remove(popup);
                clockBtn.setDisable(true);
                clockBtn.setVisible(false);
                if (officeRestore != null) gameroot.setBackground(new Background(officeRestore));
                w.showTip(gameroot, clock2tip);
                audios.getsuccessMP().play();
                audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());

            } else {
                attempts--;
                triesLabel.setText("Attempts left: " + attempts);
                input.clear();
                audios.getfailMP().play();
                audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());

                if (attempts <= 0) {
                    completed = true;
                    if (gameroot.getChildren().contains(popup)) gameroot.getChildren().remove(popup);
                    clockBtn.setDisable(true);
                    clockBtn.setVisible(false);
                    if (officeRestore != null) gameroot.setBackground(new Background(officeRestore));
                    w.showTip(gameroot, clock2tip);
                    audios.getfailMP().play();
                    audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                    if (livesManager != null) livesManager.loseLife();
                }
            }
        });

    }

    public void forceFail() {
        if (completed) return;
        completed = true;

        try {
            if (gamerootRef != null && popup != null && gamerootRef.getChildren().contains(popup)) {
                gamerootRef.getChildren().remove(popup);
            }
            try {
                if (clockBtn != null) {
                    clockBtn.setDisable(true);
                    clockBtn.setVisible(false);
                    if (gamerootRef != null && gamerootRef.getChildren().contains(clockBtn)) {
                        gamerootRef.getChildren().remove(clockBtn);
                    }
                }
            } catch (Exception ignored) {}

            if (gamerootRef != null) {
                BackgroundImage officeRestore = loadOfficeBackground(2); 
                if (officeRestore != null) gamerootRef.setBackground(new Background(officeRestore));
            }
        } catch (Exception ignored) {}

        if (livesManagerRef != null) livesManagerRef.loseLife();
        audios.getfailMP().play();
        audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
    }

    public boolean isCompleted() {
        return completed;
    }

    private BackgroundImage loadOfficeBackground(int num) {
        String[] exts = { ".jpeg", ".JPG", ".jpg", ".png" };
        for (String ext : exts) {
            String path = "/newpackage/images/office" + num + ext;
            URL res = getClass().getResource(path);
            if (res != null) {
                return new BackgroundImage(new Image(res.toExternalForm()),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, false));
            }
        }
        
        URL fallback = getClass().getResource("images/office2.jpeg");
        if (fallback != null) {
            return new BackgroundImage(new Image(fallback.toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, false));
        }
        return null;
    }
}