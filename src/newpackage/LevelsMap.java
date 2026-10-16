package newpackage;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LevelsMap {
    private Scene scene;
    public LevelsMap(mainApp mainApp){
        int unlocked = mainApp.getUnlockedLevels();

        String bgResource;
        if (unlocked >= 3) {
            bgResource = "images/AllUnlockedLevels.jpeg";
        } else if (unlocked == 2) {
            bgResource = "images/level1and2unlocked.jpeg";
        } else {
            bgResource = "images/onlylevel1unlocked.jpeg";
        }

        BackgroundImage levelsMapBg = new BackgroundImage(new Image(getClass().getResource(bgResource).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        
        StackPane levelsMapPane = new StackPane();
        levelsMapPane.setBackground(new Background(levelsMapBg));
        
        Button lvl1 = new Button(); lvl1.setPrefSize(500, 270); lvl1.setOpacity(0); lvl1.setCursor(Cursor.HAND);
        levelsMapPane.getChildren().add(lvl1);
        lvl1.setTranslateX(320); lvl1.setTranslateY(-220);
        lvl1.setOnAction(e-> mainApp.showVideoScene());
        
        Button lvl2 = new Button(); lvl2.setPrefSize(500, 220); lvl2.setOpacity(0); lvl2.setCursor(Cursor.HAND);
        lvl2.setTranslateX(-340); lvl2.setTranslateY(60);
        lvl2.setDisable(unlocked < 2);
        if (unlocked >= 2) {
            levelsMapPane.getChildren().add(lvl2);
            lvl2.setOnAction(e -> mainApp.showLvl2());
        } else {
            levelsMapPane.getChildren().add(lvl2);
        }
        
        Button lvl3 = new Button(); lvl3.setPrefSize(570, 420); lvl3.setOpacity(0); lvl3.setCursor(Cursor.HAND);
        lvl3.setTranslateX(320); lvl3.setTranslateY(220);
        lvl3.setDisable(unlocked < 3);
        if (unlocked >= 3) {
            levelsMapPane.getChildren().add(lvl3);
            lvl3.setOnAction(e -> mainApp.showLvl3());
        } else {
            levelsMapPane.getChildren().add(lvl3);
        }
        
        
        scene = new Scene(levelsMapPane, 1950, 980);
        scene.setFill(Color.BLACK);
        
    }
    
    public Scene getScene() {
        return scene;
    }
}