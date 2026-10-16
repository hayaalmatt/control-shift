package newpackage;

import javafx.animation.FillTransition;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DebugTab {

    private StackPane root = new StackPane();

    private final List<String> correctOrder =
        java.util.Arrays.asList("SCAN", "FIX", "APPLY", "CONFIRM");

    private List<String> pressed = new ArrayList<>();

    private List<Rectangle> glowRects = new ArrayList<>();

    private final audios audios;

    private Consumer<Boolean> onFinish = null;
    private boolean finished = false;

    public DebugTab(audios audios) {
        this.audios = audios;

        root.setVisible(false);
        root.setMouseTransparent(true);
        double w = 195;
        double h = 55;

        Rectangle glowConfirm = makeGlow(-105, 25, w, h);
        Rectangle glowScan    = makeGlow(143,  25, w, h);
        Rectangle glowApply   = makeGlow(-105, 135, w, h);
        Rectangle glowFix     = makeGlow(143,  135, w, h);

        glowRects.add(glowConfirm);
        glowRects.add(glowScan);
        glowRects.add(glowApply);
        glowRects.add(glowFix);

        root.getChildren().addAll(glowConfirm, glowScan, glowApply, glowFix);

        Button btnConfirm = makeBtn("CONFIRM", -105, 25, w, h);
        Button btnScan    = makeBtn("SCAN",     145, 25, w, h);
        Button btnApply   = makeBtn("APPLY",   -105, 130, w, h);
        Button btnFix     = makeBtn("FIX",      143, 135, w, h);

        root.getChildren().addAll(btnConfirm, btnScan, btnApply, btnFix);
    }

    private Rectangle makeGlow(double x, double y, double w, double h) {
        Rectangle r = new Rectangle(w, h);
        r.setFill(Color.TRANSPARENT);
        r.setStrokeWidth(4);
        r.setStroke(Color.TRANSPARENT);
        r.setTranslateX(x);
        r.setTranslateY(y);
        return r;
    }

    private Button makeBtn(String label, double x, double y, double w, double h) {
        Button b = new Button();
        b.setOpacity(0);
        b.setCursor(Cursor.HAND);
        b.setPrefSize(w, h);
        b.setTranslateX(x);
        b.setTranslateY(y);

        b.setOnAction(e -> {
            try {
                if (audios != null && audios.getclickMP() != null) {
                    audios.getclickMP().play();
                    audios.getclickMP().setOnEndOfMedia(() -> audios.getclickMP().stop());
                }
            } catch (Exception ignored) {}
            press(label);
        });
        b.setOnMouseEntered(ev -> highlight(label, true));
        b.setOnMouseExited(ev -> highlight(label, false));

        return b;
    }

    private void highlight(String label, boolean enter) {
        Rectangle r;
        switch (label) {
            case "CONFIRM":
                r = glowRects.get(0);
                break;
            case "SCAN":
                r = glowRects.get(1);
                break;
            case "APPLY":
                r = glowRects.get(2);
                break;
            case "FIX":
                r = glowRects.get(3);
                break;
            default:
                r = null;
        }

        if (r != null)
            r.setStroke(enter ? Color.WHITE : Color.TRANSPARENT);
    }

    private void press(String label) {
        if (finished) return;

        pressed.add(label);
        int idx = pressed.size() - 1;

        if (!correctOrder.get(idx).equals(label)) {
            flash(Color.RED);
            pressed.clear();
            if (!finished) {
                finished = true;
                if (onFinish != null) {
                    try { onFinish.accept(false); } catch (Exception ignored) {}
                }
            }
            return;
        }

        if (pressed.size() == correctOrder.size()) {
            flash(Color.LIMEGREEN);
            pressed.clear();
            if (!finished) {
                finished = true;
                if (onFinish != null) {
                    try { onFinish.accept(true); } catch (Exception ignored) {}
                }
            }
        }
    }

    private void flash(Color color) {
        for (Rectangle r : glowRects) {
            FillTransition ft = new FillTransition(Duration.seconds(0.25), r,
                    Color.TRANSPARENT, color.deriveColor(0, 1, 1, 0.35));

            FillTransition ft2 = new FillTransition(Duration.seconds(0.30), r,
                    color.deriveColor(0, 1, 1, 0.35), Color.TRANSPARENT);

            ft.setOnFinished(e -> ft2.play());
            ft.play();
        }
    }

    public void show() {
        root.setVisible(true);
        root.setMouseTransparent(false);
    }

    public void hide() {
        root.setVisible(false);
        root.setMouseTransparent(true);
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

    public static boolean LevelTaskSuccess(){
        return true;
    }
}