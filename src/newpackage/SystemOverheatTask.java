package newpackage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class SystemOverheatTask {
    StackPane SOT;
    private boolean spacePressed = false;
    private boolean locked = false;
    private double MAX_WIDTH = 470;

    private Runnable onSuccess = () -> {};

    audios audios = new audios();

    public void startTask(Scene scene){
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 80);
        Font VCR_OSD_MONO4 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 40);

        BackgroundImage overheat = new BackgroundImage(new Image(getClass().getResource("images/system overheat background.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        SOT = new StackPane(); SOT.setBackground(new Background(overheat));

        Label title = new Label("SYSTEM \nOVERHEATING");
        title.setTextFill(Color.rgb(250, 109, 98));
        title.setFont(VCR_OSD_MONO);
        title.setTextAlignment(TextAlignment.CENTER);


        Text subtitle = new Text("spam press SPACEBAR to cool down!");
        subtitle.setFill(Color.WHITE);
        subtitle.setFont(VCR_OSD_MONO4);

        Rectangle bar = new Rectangle(100, 30);
        bar.setFill(Color.web("#FF6663"));

        DropShadow glow = new DropShadow(20, Color.rgb(255, 130, 120));
        bar.setEffect(glow);
        bar.setStroke(Color.rgb(255, 200, 200));
        bar.setStrokeWidth(2);

        DropShadow redglow = new DropShadow(10, Color.rgb(250, 109, 98));

        DropShadow greenglow = new DropShadow(10, Color.LIGHTGREEN);

        title.setEffect(redglow);

        VBox box = new VBox(40, title, subtitle, bar);
        box.setAlignment(Pos.CENTER);

        SOT.getChildren().add(box);

        Timeline shrinkLoop = new Timeline(
                new KeyFrame(Duration.millis(100), e -> {
                    if (!spacePressed && !locked) {
                        double newWidth = bar.getWidth() - 3;
                        if (newWidth < 20) newWidth = 20;
                        bar.setWidth(newWidth);
                    }

                })
        );
        shrinkLoop.setCycleCount(Timeline.INDEFINITE);
        shrinkLoop.play();


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && !locked) {
                spacePressed = true;

                double newWidth = bar.getWidth() + 10;

                if (newWidth >= MAX_WIDTH) {
                    bar.setWidth(MAX_WIDTH);
                    locked = true;

                    title.setText("SUCCESS!");
                    title.setTextFill(Color.LIGHTGREEN);
                    title.setEffect(greenglow);
                    box.getChildren().remove(subtitle);

                    Platform.runLater(() -> {
                        try { onSuccess.run(); } catch (Exception ignored) {}
                    });

                    return;
                }

                Timeline grow = new Timeline(
                    new KeyFrame(Duration.millis(50),
                        new KeyValue(bar.widthProperty(), newWidth))
                );
                grow.play();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spacePressed = false;
            }
        });


        Platform.runLater(() -> {
            SOT.setFocusTraversable(true);
            SOT.requestFocus();
        });


    }

    public StackPane getTaskPane() {
        return SOT;
    }

    public void setOnSuccess(Runnable action) {
        this.onSuccess = action;
    }
}