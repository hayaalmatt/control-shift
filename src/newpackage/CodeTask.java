package newpackage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Consumer;

public class CodeTask {

    private StackPane root;

    private TextField line7Field;
    private TextField line8Field;

    private Label statusLabel;

    private LivesManager livesManager = null;
    private Consumer<Boolean> onFinish = null;

    private boolean completed = false;
    
    audios audios = new audios();

    public CodeTask() {

        root = new StackPane();
        root.setVisible(false);

        Font codeFont = Font.font("Consolas", 30);

        Label line7Info = new Label(
                "An entity has changed this line of code:\n" +
                "IF danger_detected == FALSE:\n" +
                "What is the original form of this line?"
        );
        line7Info.setFont(codeFont);
        line7Info.setTextFill(Color.LIMEGREEN);
        line7Info.setTranslateY(-150); 

        line7Field = new TextField();
        line7Field.setPromptText("IF danger_detected == TRUE:");
        line7Field.setMaxWidth(500);
        line7Field.setTranslateY(-20); 
        line7Field.setStyle("-fx-background-color: black; -fx-text-fill: lime; -fx-prompt-text-fill: #4f8f4f; -fx-font-size: 28px;");

        Label line8Info = new Label(
                "An entity has deleted line 08.\n" +
                "What was originally written?"
        );
        line8Info.setFont(codeFont);
        line8Info.setTextFill(Color.LIMEGREEN);
        line8Info.setTranslateY(70); 

        line8Field = new TextField();
        line8Field.setPromptText("ACTIVATE_DEFENSES()");
        line8Field.setMaxWidth(500);
        line8Field.setTranslateY(190); 
        line8Field.setStyle("-fx-background-color: black; -fx-text-fill: lime; -fx-prompt-text-fill: #4f8f4f; -fx-font-size: 28px;");

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Consolas", 32));
        statusLabel.setTranslateY(250); 
        statusLabel.setTextFill(Color.RED);

        line7Field.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) checkAnswers(); });
        line8Field.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) checkAnswers(); });

        root.getChildren().addAll(line7Info, line7Field, line8Info, line8Field, statusLabel);
    }

    public CodeTask(LivesManager lm, java.util.function.Consumer<Boolean> finishCallback) {
        this();
        this.livesManager = lm;
        this.onFinish = finishCallback;
    }

    public void setLivesManager(LivesManager lm) {
        this.livesManager = lm;
    }

    public void setOnFinish(java.util.function.Consumer<Boolean> finishCallback) {
        this.onFinish = finishCallback;
    }

    private void checkAnswers() {
        String a1 = line7Field.getText().trim();
        String a2 = line8Field.getText().trim();

        boolean ok = a1.equals("IF danger_detected == TRUE:") &&
                      a2.equals("ACTIVATE_DEFENSES()");

        completed = true; 

        if (ok) {
            statusLabel.setText("Stable running code");
            statusLabel.setTextFill(Color.LIMEGREEN);
            audios.getsuccessMP().play();
            audios.getsuccessMP().setOnEndOfMedia(() -> audios.getsuccessMP().stop());
            if (onFinish != null) {
                try { onFinish.accept(true); } catch (Exception ignored) {}
            }
        } else {
            statusLabel.setText("Code malfunction detected");
            statusLabel.setTextFill(Color.RED);
            if (livesManager != null) {
                livesManager.loseLife();
            }
            audios.getfailMP().play();
            audios.getfailMP().setOnEndOfMedia(() -> audios.getfailMP().stop());
            if (onFinish != null) {
                try { onFinish.accept(false); } catch (Exception ignored) {}
            }
        }
    }

    public StackPane getRoot() { return root; }
    public void show() { root.setVisible(true); completed = false; } 
    public void hide() { root.setVisible(false); }

    public boolean isCompleted() { return completed; }

    public static boolean LevelTaskSuccess(){
        return true;
    }
}