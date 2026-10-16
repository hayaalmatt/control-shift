package newpackage;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class WinnerPage {
    private Scene winnerScene;
    
    public WinnerPage(mainApp mainApp){
        StackPane overlay = new StackPane();
        
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 130);
        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 60);
        Font VCR_OSD_MONO4 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 40);
        
        Rectangle r = new Rectangle(1950, 980); r.setFill(Color.BLACK); r.setOpacity(1);
        overlay.getChildren().add(r);
        
        Label title = new Label("You Won!");
        title.setFont(VCR_OSD_MONO);
        title.setStyle("-fx-text-fill: #ff0000; -fx-effect: dropshadow(gaussian, #ff0000, 5, 0.7, 0, 0);");
        
        Label subtitle = new Label("You have successfully completed this level!");
        subtitle.setFont(VCR_OSD_MONO2);
        subtitle.setStyle("-fx-text-fill: #ff0000; -fx-effect: dropshadow(gaussian, #ff0000, 5, 0.7, 0, 0);");
        
        Button rhome = new Button("Return to home"); rhome.setCursor(Cursor.HAND);
        rhome.setFont(VCR_OSD_MONO4);
        rhome.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-color: #ff0000;\n" +
                        "    -fx-border-width: 3px;\n" +
                        "    -fx-text-fill: #ff0000;"

                + "-fx-effect: dropshadow(gaussian, #ff0000, 5, 0.5, 0, 0);");
        
        Button next = new Button("Next Level"); next.setCursor(Cursor.HAND);
        next.setFont(VCR_OSD_MONO4);
        next.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-color: #ff0000;\n" +
                        "    -fx-border-width: 3px;\n" +
                        "    -fx-text-fill: #ff0000;"
                + "-fx-effect: dropshadow(gaussian, #ff0000, 3, 0.5, 0, 0);");
        
        
        VBox v = new VBox(50, title, subtitle, rhome, next);
        v.setAlignment(Pos.CENTER);
        overlay.getChildren().add(v);
        
        
        rhome.setOnAction(e->mainApp.showHomepage());
        
        next.setOnAction(e->{
            mainApp.showLevelsMap();
        });
        
        
        winnerScene = new Scene(overlay, 1950, 980);
        winnerScene.setFill(Color.BLACK);
        

    }
    
    public Scene getScene(){
        return winnerScene;
    }
}