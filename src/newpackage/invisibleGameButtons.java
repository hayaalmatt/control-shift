package newpackage;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public class invisibleGameButtons {
    public static void newButton(Button button, StackPane gamePane, Font xFont, ImageView image,
            int w, int h, int x, int y, int w2, int h2, int x2, int y2, MediaPlayer audio){
        StackPane buttonPane = new StackPane();
        button.setCursor(Cursor.HAND);
        button.setPrefSize(w, h);
        button.setOpacity(0);
        
        gamePane.getChildren().add(button);
        button.setTranslateX(x);
        button.setTranslateY(y);
        
        image.setFitWidth(w2);
        image.setFitHeight(h2);
        
        Button X = new Button("X");
        X.setCursor(Cursor.HAND);
        X.setFont(xFont);
        X.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        
        buttonPane.getChildren().addAll(image, X);
        X.setTranslateX(x2); X.setTranslateY(y2);
        
        button.setOnMouseClicked(e -> {
            gamePane.getChildren().add(buttonPane);
            audio.play(); audio.setOnEndOfMedia(()->audio.stop());
                });
        X.setOnMouseClicked(e -> gamePane.getChildren().remove(buttonPane));
        
    }
}


