package newpackage;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

import java.util.function.Consumer;

public class ComputerPane {

    Background cams;

    CameraBackgrounds cb = new CameraBackgrounds();

    private StackPane camsPane;
    private StackPane codePane;
    private StackPane debugPane;

    private StackPane computerPane; 

    private StackPane contentHolder;

    private DebugTab debugTab;
    private CodeTask codeTask;

    private Button codeTabBtn; 

    private Button debugBtn;

    private ImageView camGlitchOverlay;

    private CameraLockPopup cameraLockPopup;


    public void showCompPane(StackPane gamePane, Button laptopBtn, audios audios, LivesManager livesManager, boolean allCamsUnlocked, boolean enableCodeTab){

        Font StrangePath = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 75);
        Font StrangePath2 = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 30);
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 65);
        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 47);
        Font VCR_OSD_MONO3 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 24);
        Font VCR_OSD_MONO4 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 40);

        computerPane = new StackPane();
        computerPane.setVisible(false);


        Button closeBtn = new Button("X");
        closeBtn.setCursor(Cursor.HAND);
        closeBtn.setFont(VCR_OSD_MONO2);
        closeBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-text-fill: black;");

        closeBtn.setTranslateX(520);
        closeBtn.setTranslateY(-463);

        Button camerasTab = new Button();
        camerasTab.setCursor(Cursor.HAND);
        camerasTab.setOpacity(0);
        camerasTab.setPrefSize(170, 50);
        camerasTab.setTranslateX(-420);
        camerasTab.setTranslateY(-390);

        Button codeTab = new Button();
        codeTab.setCursor(Cursor.HAND);
        codeTab.setOpacity(0);
        codeTab.setPrefSize(190, 50);
        codeTab.setTranslateX(-230);
        codeTab.setTranslateY(-390);
        this.codeTabBtn = codeTab;
        codeTab.setDisable(!enableCodeTab);

        debugBtn = new Button();
        debugBtn.setCursor(Cursor.HAND);
        debugBtn.setOpacity(0);
        debugBtn.setPrefSize(190, 50);
        debugBtn.setTranslateX(-25);
        debugBtn.setTranslateY(-390);
        debugBtn.setDisable(true); 

        contentHolder = new StackPane();

        createCamsPane(livesManager, audios, allCamsUnlocked);
        createCodePane();
        createDebugPane(audios);

        showPane(camsPane);

        camerasTab.setOnAction(e -> {
            showPane(camsPane);
            audios.gettabsMP().play(); audios.gettabsMP().setOnEndOfMedia(()->audios.gettabsMP().stop());
        });

        codeTab.setOnAction(e -> {
            showPane(codePane);
            if (codeTask != null) {
                codeTask.show();
            }
            if (debugTab != null) debugTab.hide();
            audios.gettabsMP().play(); audios.gettabsMP().setOnEndOfMedia(()->audios.gettabsMP().stop());
        });

        debugBtn.setOnAction(e -> {
            showPane(debugPane);
            if (debugTab != null) debugTab.show();
            if (codeTask != null) codeTask.hide();
            audios.gettabsMP().play(); audios.gettabsMP().setOnEndOfMedia(()->audios.gettabsMP().stop());
        });

        laptopBtn.setOnAction(e -> {
            computerPane.setVisible(true);
            audios.getcomputerMP().play(); audios.getcomputerMP().setOnEndOfMedia(()->audios.getcomputerMP().stop());
        });

        closeBtn.setOnAction(e -> computerPane.setVisible(false));

        computerPane.getChildren().addAll(contentHolder, camerasTab, codeTab, debugBtn, closeBtn);

        gamePane.getChildren().add(computerPane);

        if (enableCodeTab && codeTask != null) {
            codeTask.setLivesManager(livesManager);
            codeTask.setOnFinish(success -> {
                try {
                    codeTask.hide();
                    if (codeTabBtn != null) codeTabBtn.setDisable(true);
                } catch (Exception ignored) {}
            });
        }

    }

    private void showPane(StackPane pane) {
        contentHolder.getChildren().clear();
        contentHolder.getChildren().add(pane);
    }

    public void createCamsPane(LivesManager livesManager, audios audios, boolean allCamsUnlocked){
        camsPane = new StackPane();
        
        camsPane.setBackground(new Background(cb.getCam2n3lock()));
        
        cameraLockPopup = new CameraLockPopup(cb, audios, livesManager, this);

        if (allCamsUnlocked) {
            cameraLockPopup.setCam2Done(true);
            cameraLockPopup.setCam3Done(true);
            camsPane.setBackground(new Background(cb.getRobotin1()));
        }

        cameraLockPopup.show(camsPane);

    }
    public void createCodePane(){
        codePane = new StackPane();
        BackgroundImage bgCode = new BackgroundImage(
                new Image(getClass().getResource("images/codetab.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        codePane.setBackground(new Background(bgCode));

        codeTask = new CodeTask();
        codePane.getChildren().add(codeTask.getRoot());


    }
    public void createDebugPane(audios audios){
        debugPane = new StackPane();
        BackgroundImage bgDebug = new BackgroundImage(
                new Image(getClass().getResource("images/newdebugtab.JPG").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        debugPane.setBackground(new Background(bgDebug));
        debugTab = new DebugTab(audios);
        debugPane.getChildren().add(debugTab.getRoot());

    }

    public void updateCams(BackgroundImage bg){
        camsPane.setBackground(new Background(bg));
    }

    public void updateCamsForLives(int lives) {
        if (camsPane == null) return;
        if (lives >= 3) {
            camsPane.setBackground(new Background(cb.getRobotin1()));
        } else if (lives == 2) {
            camsPane.setBackground(new Background(cb.getRobotin2()));
        } else if (lives == 1) {
            camsPane.setBackground(new Background(cb.getRobotin3()));
        } else {
            camsPane.setBackground(new Background(cb.getRobotin3()));
        }
    }

    public boolean isCam2Done() {
        return cameraLockPopup != null && cameraLockPopup.isCam2Done();
    }
    public boolean isCam3Done() {
        return cameraLockPopup != null && cameraLockPopup.isCam3Done();
    }
    public void forceUnlockCam2() {
        if (cameraLockPopup != null) cameraLockPopup.forceUnlockCam2();
    }
    public void forceUnlockCam3() {
        if (cameraLockPopup != null) cameraLockPopup.forceUnlockCam3();
    }

    public boolean isCodeTaskCompleted() {
        return codeTask != null && codeTask.isCompleted();
    }

    public void closeCodeTaskAndDisableTab() {
        try {
            if (codeTask != null) codeTask.hide();
            if (codeTabBtn != null) codeTabBtn.setDisable(true);
        } catch (Exception ignored) {}
    }

    
    public void setCodeTaskFinishHandler(Consumer<Boolean> handler) {
        if (codeTask != null) {
            codeTask.setOnFinish(handler);
        }
    }

    public void openCodeTab() {
        try {
            if (computerPane != null) computerPane.setVisible(true);
            if (codePane != null) {
                showPane(codePane);
                if (codeTask != null) codeTask.show();
            }
        } catch (Exception ignored) {}
    }

    public void openCamsTab() {
        try {
            if (computerPane != null) computerPane.setVisible(true);
            if (camsPane != null) {
                showPane(camsPane);
            }
        } catch (Exception ignored) {}
    }

    public void closeComputer() {
        try {
            if (computerPane != null) computerPane.setVisible(false);
        } catch (Exception ignored) {}
    }

    
    public void showCameraGlitchOverlay() {
        try {
            if (camsPane == null) return;
            if (camGlitchOverlay == null) {
                Image img = new Image(getClass().getResource("images/camerasglitchoverlay.jpg").toExternalForm());
                camGlitchOverlay = new ImageView(img);
                camGlitchOverlay.setFitHeight(980);
                camGlitchOverlay.setPreserveRatio(true);
                camGlitchOverlay.setMouseTransparent(true); 
            }
            if (!camsPane.getChildren().contains(camGlitchOverlay)) {
                camsPane.getChildren().add(camGlitchOverlay);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public void removeCameraGlitchOverlay() {
        try {
            if (camsPane == null || camGlitchOverlay == null) return;
            camsPane.getChildren().remove(camGlitchOverlay);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public void enableDebugTab(boolean enable) {
        if (debugBtn != null) debugBtn.setDisable(!enable);
    }

    public boolean isDebugFinished() {
        return debugTab != null && debugTab.isFinished();
    }

    public void setDebugFinishHandler(Consumer<Boolean> handler) {
        if (debugTab != null) {
            debugTab.setOnFinish(handler);
        }
    }

    public DebugTab getDebugTab() { return debugTab; }
}