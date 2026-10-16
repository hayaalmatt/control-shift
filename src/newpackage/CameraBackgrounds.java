package newpackage;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class CameraBackgrounds {
    private BackgroundImage cam2n3lock;
    private BackgroundImage cam3lock;
    private BackgroundImage cam3lockedrobotin2;
    private BackgroundImage robotin1;
    private BackgroundImage robotin2;
    private BackgroundImage robotin3;
    
    public CameraBackgrounds(){
        cam2n3lock = new BackgroundImage(
                new Image(getClass().getResource("images/camlocked.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        cam3lock = new BackgroundImage(
                new Image(getClass().getResource("images/onlycam3locked.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        cam3lockedrobotin2 = new BackgroundImage(
                new Image(getClass().getResource("images/cam2robotcam3locked.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        robotin1 = new BackgroundImage(
                new Image(getClass().getResource("images/robotin1st.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        robotin2 = new BackgroundImage(
                new Image(getClass().getResource("images/robotin2nd.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        robotin3 = new BackgroundImage(
                new Image(getClass().getResource("images/robotin3rd.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
    }
    
    public BackgroundImage getCam2n3lock(){ return cam2n3lock; }
    public BackgroundImage getCam3lock(){ return cam3lock; }
    public BackgroundImage getCam3lockRobotin2(){ return cam3lockedrobotin2; }
    public BackgroundImage getRobotin1(){ return robotin1; }
    public BackgroundImage getRobotin2(){ return robotin2; }
    public BackgroundImage getRobotin3(){ return robotin3; }
}


