package newpackage;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Pos;
import javafx.scene.text.Font;


public class Warnings {
    public void showTip(StackPane gameroot, ImageView img){
        img.setFitWidth(img.getFitWidth()/2); img.setFitHeight(img.getFitHeight()/2);
        img.setTranslateX(-400); img.setTranslateY(-250);
        img.setOpacity(0);

        gameroot.getChildren().add(img);

        FadeTransition fadein = new FadeTransition(Duration.millis(250), img);
        fadein.setFromValue(0); fadein.setToValue(1);

        PauseTransition wait = new PauseTransition(Duration.seconds(5));

        FadeTransition fadeout = new FadeTransition(Duration.millis(250), img);
        fadeout.setFromValue(1); fadeout.setToValue(0);

        SequentialTransition sequence = new SequentialTransition(fadein, wait, fadeout);
        sequence.play();
    }

    public void showWarning(StackPane gameroot, ImageView img){
        img.setFitWidth(400); img.setFitHeight(298);
        img.setTranslateX(490); img.setTranslateY(-310);
        img.setOpacity(0);

        gameroot.getChildren().add(img);

        FadeTransition fadein = new FadeTransition(Duration.millis(250), img);
        fadein.setFromValue(0); fadein.setToValue(1);

        PauseTransition wait = new PauseTransition(Duration.seconds(4));

        FadeTransition fadeout = new FadeTransition(Duration.millis(250), img);
        fadeout.setFromValue(1); fadeout.setToValue(0);

        SequentialTransition sequence = new SequentialTransition(fadein, wait, fadeout);
        sequence.play();
    }

    
    public void showBanner(StackPane gamePane, String text) {
        Label banner = new Label(text);
        banner.setTextFill(Color.RED);
        banner.setFont(Font.font("VCR OSD MONO", 20));
        banner.setPadding(new Insets(8, 14, 8, 14));
        banner.setOpacity(0); 

        banner.setBackground(new Background(
                new BackgroundFill(Color.web("#111"), null, null)
        ));

        HBox box = new HBox(banner);
        box.setAlignment(Pos.TOP_RIGHT);
        box.setPadding(new Insets(10));

        box.setMouseTransparent(true);

        gamePane.getChildren().add(box);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), banner);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(5));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), banner);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> gamePane.getChildren().remove(box));

        SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
        seq.play();
    }
}