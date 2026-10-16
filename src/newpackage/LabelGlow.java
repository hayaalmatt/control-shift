package newpackage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

public class LabelGlow {
    public static void Flicker(Label label){
        Glow ctrlshiftglow = new Glow();
        ctrlshiftglow.setLevel(0);
        label.setEffect(ctrlshiftglow);
        Timeline titleGlow = new Timeline();
        titleGlow.setCycleCount(Timeline.INDEFINITE);
        titleGlow.setAutoReverse(true);
        KeyValue kv = new KeyValue(ctrlshiftglow.levelProperty(), 3);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        titleGlow.getKeyFrames().add(kf);
        titleGlow.play();
    }
}


