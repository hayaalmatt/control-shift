package newpackage;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class homepageButton {
    
    public static void newButton(Button button, Font prefFont, BackgroundImage bg){
        
        button.setCursor(Cursor.HAND);
        button.setBackground(new Background(bg));
        button.setPrefSize(300, 200);
        button.setTextFill(Color.rgb(177, 56, 47));
        button.setFont(prefFont);
        button.setStyle("-fx-alignment: center;");
        
        DropShadow btnGlow = new DropShadow();
        btnGlow.setColor(Color.rgb(177, 56, 47));
        btnGlow.setRadius(4);
        btnGlow.setSpread(100);
        button.setOnMouseEntered(e -> button.setEffect(btnGlow)); button.setOnMouseExited(e -> button.setEffect(null));
    
        
    }
}


