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

public class JumpscareVid {
    private Scene Vidscene;
    
    public JumpscareVid(mainApp mainApp){
        
        Font VCR_OSD_MONO3 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 24);
        Media jumpscaresrc = new Media("file:///Users/dell/Downloads/finaljumpScare.mp4");
        
        StackPane vidpane = new StackPane();
        MediaPlayer jumpscarePlayer = new MediaPlayer(jumpscaresrc);
        
        jumpscarePlayer.setAutoPlay(true);
        jumpscarePlayer.setOnReady(() -> {
            try {
                jumpscarePlayer.play();
            } catch (Exception ignored) {}
        });

        MediaView jumpscareView = new MediaView(jumpscarePlayer);
        jumpscareView.setFitWidth(1950);
        jumpscareView.setFitHeight(980);

        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), jumpscareView);
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
        
        vidpane.getChildren().addAll(jumpscareView, skip); 
        skip.setTranslateX(570);
        skip.setTranslateY(350);
        
        vidpane.setStyle("-fx-background-color: black");
        

        jumpscarePlayer.setOnEndOfMedia(() -> {
            fadeOut.play();
            fadeOut.setOnFinished(e -> {
                jumpscarePlayer.stop();
                jumpscarePlayer.dispose();
                mainApp.showLoserPage();
            });
        });
        
        skip.setOnMouseClicked(e -> {
            try {
                jumpscarePlayer.pause();
            } catch (Exception ignored) {}
            fadeOut.play();
            fadeOut.setOnFinished(e2 -> {
                try {
                    jumpscarePlayer.stop();
                    jumpscarePlayer.dispose();
                } catch (Exception ignored) {}
                mainApp.showLoserPage();
            });
        });
        
        Vidscene = new Scene(vidpane, 1950, 980);
    }
    
    public Scene getScene() {
        return Vidscene;
    }
}