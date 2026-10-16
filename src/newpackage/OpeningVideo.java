package newpackage;

import javafx.animation.FadeTransition;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class OpeningVideo {
    private Scene Vidscene;
    
    public OpeningVideo(mainApp mainApp){
        Font VCR_OSD_MONO3 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 24);
        Media storylinesrc = new Media("file:///Users/dell/Downloads/FinalStoryline.mp4");
        
        StackPane vidpane = new StackPane();
        MediaPlayer storylinePlayer = new MediaPlayer(storylinesrc);
        storylinePlayer.setAutoPlay(true);
        storylinePlayer.setOnReady(() -> {
            try {
                storylinePlayer.play();
            } catch (Exception ignored) {}
        });

        MediaView storylineView = new MediaView(storylinePlayer);
        storylineView.setFitWidth(1950);
        storylineView.setFitHeight(980);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), storylineView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), storylineView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        
        Button skip = new Button("Skip");
        skip.setCursor(Cursor.HAND);
        skip.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        skip.setFont(VCR_OSD_MONO3);
        
        DropShadow whiteGlow = new DropShadow();
        whiteGlow.setColor(Color.WHITE);
        whiteGlow.setRadius(2);
        whiteGlow.setSpread(100);

        skip.setOnMouseEntered(e -> skip.setEffect(whiteGlow));
        skip.setOnMouseExited(e -> skip.setEffect(null));
        
        vidpane.getChildren().addAll(storylineView, skip); 
        skip.setTranslateX(570);
        skip.setTranslateY(350);
        
        vidpane.setStyle("-fx-background-color: black");
        
        fadeIn.play();

        storylinePlayer.setOnEndOfMedia(() -> {
            fadeOut.play();
            fadeOut.setOnFinished(e -> {
                storylinePlayer.stop();
                storylinePlayer.dispose();
                mainApp.showLvl1();
            });
        });
        
        skip.setOnMouseClicked(e -> {
            try {
                storylinePlayer.pause();
            } catch (Exception ignored) {}
            fadeOut.play();
            fadeOut.setOnFinished(e2 -> {
                try {
                    storylinePlayer.stop();
                    storylinePlayer.dispose();
                } catch (Exception ignored) {}
                mainApp.showLvl1();
            });
        });
        
        Vidscene = new Scene(vidpane, 1950, 980);
    }
    
    public Scene getScene() {
        return Vidscene;
    }
}