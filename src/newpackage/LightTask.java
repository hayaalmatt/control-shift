package newpackage;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LightTask {

    private StackPane root = new StackPane();
    private String selectedId = null;
    private Button selectedButton = null;
    private int solvedCount = 0;

    private Map<String, Color> colors = new HashMap<String, Color>() {{
        put("yellow", Color.web("#f7eb52"));
        put("blue",   Color.web("#302cfa"));
        put("red",    Color.web("#e93627"));
        put("pink",   Color.web("#eb39f4"));
    }};

    private Map<String, Line> solvedLines = new HashMap<>();

    private Consumer<Boolean> onFinish = null;
    private boolean finished = false;
    private boolean success = false;

    private final audios audios;

    public LightTask(audios audios) {
        this.audios = audios;

        root.setVisible(false);
        root.setStyle("-fx-background-color: rgba(0,0,0,0.7);");

        ImageView taskImg = new ImageView(
                new Image(getClass().getResource("images/wiretask1.jpeg").toExternalForm())
        );
        taskImg.setPreserveRatio(true);
        taskImg.setFitWidth(800);
        StackPane.setAlignment(taskImg, Pos.CENTER);
        root.getChildren().add(taskImg);

        Button leftYellow = makeWireButton("yellow", -340, -160);
        Button leftBlue   = makeWireButton("blue",   -340, -28);
        Button leftRed    = makeWireButton("red",    -340, 90);
        Button leftPink   = makeWireButton("pink",   -340, 215);

        Button rightRed    = makeWireButton("red",    305, -164);
        Button rightBlue   = makeWireButton("blue",   305, -33);
        Button rightYellow = makeWireButton("yellow", 305, 92);
        Button rightPink   = makeWireButton("pink",   305, 220);

        root.getChildren().addAll(
                leftYellow, leftBlue, leftRed, leftPink,
                rightRed, rightBlue, rightYellow, rightPink
        );
    }

    private Button makeWireButton(String id, double x, double y) {
        Button b = new Button();
        b.setStyle("-fx-background-color: transparent;");
        b.setCursor(Cursor.HAND);
        b.setPrefSize(30, 30);
        b.setShape(new Circle(15));
        b.setTranslateX(x);
        b.setTranslateY(y);

        b.setOnAction(e -> {
            try {
                if (audios != null && audios.getwiresMP() != null) {
                    audios.getwiresMP().play();
                    audios.getwiresMP().setOnEndOfMedia(() -> audios.getwiresMP().stop());
                }
            } catch (Exception ignored) {}

            handleClick(id, b);
        });
        return b;
    }

    private void handleClick(String id, Button b) {

        if (finished) return;

        if (selectedButton == null) {
            selectedButton = b;
            selectedId = id;
            return;
        }

        if (selectedButton == b)
            return;

        if (selectedId.equals(id)) {
            Line line = drawAnimatedWire(selectedButton, b, colors.get(id));
            solvedLines.put(id, line);
            solvedCount++;
        }

        selectedButton = null;
        selectedId = null;

        if (solvedCount == 4 && !finished) {
            finished = true;
            success = true;
            root.setVisible(false);
            if (onFinish != null) {
                try { onFinish.accept(true); } catch (Exception ignored) {}
            }
        }
    }

    private Line drawAnimatedWire(Button b1, Button b2, Color color) {

    Point2D p1Scene = b1.localToScene(
            b1.getWidth() / 2.0,
            b1.getHeight() / 2.0
    );

    Point2D p2Scene = b2.localToScene(
            b2.getWidth() / 2.0,
            b2.getHeight() / 2.0
    );

    Point2D start = root.sceneToLocal(p1Scene);
    Point2D end   = root.sceneToLocal(p2Scene);

    Line line = new Line(
            start.getX(),
            start.getY(),
            start.getX(),
            start.getY()
    );

    line.setStroke(color);
    line.setStrokeWidth(20);
    line.setStrokeLineCap(StrokeLineCap.ROUND);
    line.setStrokeLineJoin(StrokeLineJoin.ROUND);

    DropShadow glow = new DropShadow();
    glow.setColor(color.deriveColor(0, 1, 1, 0.85));
    glow.setRadius(12);
    glow.setSpread(0.45);
    line.setEffect(glow);

    root.getChildren().add(1, line);

    Timeline anim = new Timeline(
            new KeyFrame(
                    Duration.millis(260),
                    new KeyValue(line.endXProperty(), end.getX(), Interpolator.EASE_BOTH),
                    new KeyValue(line.endYProperty(), end.getY(), Interpolator.EASE_BOTH)
            )
    );
    anim.play();

    createSpark(end.getX(), end.getY(), color);

    return line;
}


    private void createSpark(double x, double y, Color color) {

        Rectangle spark = new Rectangle(10, 10, color);
        spark.setArcWidth(10);
        spark.setArcHeight(10);
        spark.setTranslateX(x);
        spark.setTranslateY(y);

        Glow glow = new Glow(1.0);
        spark.setEffect(glow);

        root.getChildren().add(2, spark);

        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(spark.opacityProperty(), 1),
                        new KeyValue(spark.scaleXProperty(), 1),
                        new KeyValue(spark.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.millis(220),
                        new KeyValue(spark.opacityProperty(), 0),
                        new KeyValue(spark.scaleXProperty(), 1.8),
                        new KeyValue(spark.scaleYProperty(), 1.8)
                )
        );

        t.setOnFinished(e -> root.getChildren().remove(spark));
        t.play();
    }

    public void show() {
        root.setVisible(true);

        for (Line l : solvedLines.values()) {
            if (!root.getChildren().contains(l))
                root.getChildren().add(1, l);
        }

        Button close = new Button("X");
        close.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 18;");
        close.setTranslateX(380);
        close.setTranslateY(-280);
        close.setOnAction(e -> {
            if (!finished) {
                finished = true;
                success = false;
                if (onFinish != null) {
                    try { onFinish.accept(false); } catch (Exception ignored) {}
                }
            }
            root.setVisible(false);
        });

        if (!root.getChildren().contains(close))
            root.getChildren().add(close);
    }

    public StackPane getRoot() {
        return root;
    }

    public void setOnFinish(Consumer<Boolean> handler) {
        this.onFinish = handler;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isSuccess() {
        return success;
    }

    public void forceFail() {
        if (finished) return;
        finished = true;
        success = false;
        root.setVisible(false);
        if (onFinish != null) {
            try { onFinish.accept(false); } catch (Exception ignored) {}
        }
    }

    public static boolean LevelTaskSuccess(){
        return true;
    }

}