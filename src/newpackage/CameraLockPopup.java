package newpackage;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class CameraLockPopup {

    private static final String PASSWORD = "stayalert1";
    private static final String SCRAMBLE_WORD = "danger";

    private boolean cam2Done = false;
    private boolean cam3Done = false;
    private int cam3Attempts = 3;

    private final CameraBackgrounds CB;
    private final audios audios;
    private final LivesManager livesManager;
    private final ComputerPane parentComputerPane;

    private Pane overlay;          
    private Pane popupLayer;       
    private TextField passField;
    private TextField scrambleInput;
    private Button baseButton;
    private Button base2Button;
    private StackPane rootRef;     

    Warnings w = new Warnings();
    ImageView cam2tip = new ImageView(new Image(getClass().getResource("images/cam2TIP.png").toExternalForm()));
    ImageView cam3tip = new ImageView(new Image(getClass().getResource("images/cam3TIP.png").toExternalForm()));

    public CameraLockPopup(CameraBackgrounds cb, audios audios, LivesManager livesManager, ComputerPane parent) {
        this.CB = cb;
        this.audios = audios;
        this.livesManager = livesManager;
        this.parentComputerPane = parent;
    }

    
    public void setCam2Done(boolean done) {
        this.cam2Done = done;
    }

    
    public void setCam3Done(boolean done) {
        this.cam3Done = done;
    }

    public void show(StackPane root){
        this.rootRef = root;
        int screenW = 1950;
        int screenH = 980;

        overlay = new Pane();
        overlay.setPrefSize(screenW, screenH);
        overlay.setVisible(false);

        ImageView popupImg = new ImageView(new Image(getClass().getResourceAsStream("images/cam2lockedtask.png")));
        popupImg.setFitWidth(screenW * 0.45);
        popupImg.setPreserveRatio(true);



        double popupX = (screenW - popupImg.getFitWidth()) / 2;
        double popupY = screenH * 0.15;

        popupImg.setLayoutX(popupX);
        popupImg.setLayoutY(popupY);

        overlay.getChildren().add(popupImg);

        passField = new TextField();
        passField.setPrefWidth(260);
        passField.setPrefHeight(32);
        passField.setStyle(
                "-fx-background-color: transparent;" + 
                "-fx-border-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 24px;"
        );

        passField.setLayoutX(850);
        passField.setLayoutY(510);



        baseButton = new Button(); baseButton.setPrefSize(480, 280);
        baseButton.setTranslateX(-250); baseButton.setTranslateY(-150);
        baseButton.setOpacity(0);
        root.getChildren().add(baseButton);
        baseButton.setCursor(Cursor.HAND);

        BackgroundImage cam3lock = new BackgroundImage(
                new Image(getClass().getResource("images/cam3lockedbtn.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );

        base2Button = new Button();
        base2Button.setPrefSize(960, 305);
        base2Button.setTranslateX(0); base2Button.setTranslateY(180);
        base2Button.setOpacity(1);
        root.getChildren().add(base2Button);
        base2Button.setCursor(Cursor.HAND);
        base2Button.setBackground(new Background(cam3lock));

        overlay.getChildren().add(passField);
        root.getChildren().add(overlay);

        if (cam2Done) {
            baseButton.setDisable(true);
            baseButton.setOpacity(0);
        }

        if (cam3Done) {
            base2Button.setDisable(true);
            base2Button.setOpacity(0);
        }

        baseButton.setOnAction(e->{
            if (cam2Done) return;
            overlay.setVisible(true);
            passField.requestFocus();
        });

        passField.setOnAction(e -> {
            if (cam2Done) return;
            String text = passField.getText();
            passField.clear();

            if (text.equals(PASSWORD)) {
                removeOverlayIfPresent();
                cam2Done = true;
                baseButton.setDisable(true);
                w.showTip(root, cam2tip);
                audios.getsuccessMP().play();
                audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());

                if (parentComputerPane != null && livesManager != null) {
                    parentComputerPane.updateCamsForLives(livesManager.getLives());
                }
            } else {
                removeOverlayIfPresent();
                cam2Done = true;
                baseButton.setDisable(true);
                if (livesManager != null) livesManager.loseLife();
                w.showTip(root, cam2tip);
                audios.getfailMP().play();
                audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());

                if (parentComputerPane != null && livesManager != null) {
                    parentComputerPane.updateCamsForLives(livesManager.getLives());
                }
            }
        });



        Pane cam3error = new Pane();
        ImageView cam3errorimg = new ImageView(new Image(getClass().getResourceAsStream("images/cam3beforecam2error.PNG")));
        cam3errorimg.setFitWidth(1000); cam3errorimg.setFitHeight(750);
        cam3errorimg.setTranslateX(480); cam3errorimg.setTranslateY(120);
        cam3error.getChildren().add(cam3errorimg);

        base2Button.setOnAction(e->{
            if (cam2Done){
                showCamera3Popup(root, base2Button);
            }
            else{
                root.getChildren().add(cam3error);
                audios.getfailMP().play();
                audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                root.setOnMouseClicked(e2->root.getChildren().remove(cam3error));
            }
        });

    }

    private void showCamera3Popup(StackPane root, Button base2) {
        if (cam3Done) return;

        popupLayer = new Pane();
        popupLayer.setPrefSize(1950, 980);

        ImageView popupImg = new ImageView(new Image(
                getClass().getResourceAsStream("images/cam3task.png")
        ));
        popupImg.setFitWidth(1950 * 0.43);
        popupImg.setPreserveRatio(true);

        double popupX = (1950 - popupImg.getFitWidth()) / 2;
        double popupY = 980 * 0.13;

        popupImg.setLayoutX(popupX);
        popupImg.setLayoutY(popupY);

        popupLayer.getChildren().add(popupImg);

        scrambleInput = new TextField();
        scrambleInput.setPrefWidth(popupImg.getFitWidth() * 0.53);
        scrambleInput.setPrefHeight(32);
        scrambleInput.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-text-fill: orange;" +
                "-fx-font-size: 26px;"
        );

        scrambleInput.setTranslateX(720); scrambleInput.setTranslateY(485);

        popupLayer.getChildren().add(scrambleInput);
        root.getChildren().add(popupLayer);

        scrambleInput.setOnAction(e -> {
            if (cam3Done) {
                removePopupLayerIfPresent();
                return;
            }

            String txt = scrambleInput.getText();
            scrambleInput.clear();

            if (txt.equalsIgnoreCase(SCRAMBLE_WORD)) {
                removePopupLayerIfPresent();
                cam3Done = true;
                base2.setDisable(true); base2.setOpacity(0);
                w.showTip(root, cam3tip);
                audios.getsuccessMP().play();
                audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());
            } else {
                cam3Attempts--;
                if (cam3Attempts <= 0) {
                    removePopupLayerIfPresent();
                    cam3Done = true;
                    base2.setDisable(true); base2.setOpacity(0);
                    if (livesManager != null) livesManager.loseLife();
                    w.showTip(root, cam3tip);
                    audios.getfailMP().play();
                    audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());

                    if (parentComputerPane != null && livesManager != null) {
                        parentComputerPane.updateCamsForLives(livesManager.getLives());
                    }
                } else {
                    audios.getfailMP().play();
                    audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                }
            }
        });

        popupLayer.toFront();
        scrambleInput.requestFocus();
    }

    private void removeOverlayIfPresent() {
        try {
            if (overlay != null && rootRef != null && rootRef.getChildren().contains(overlay)) {
                rootRef.getChildren().remove(overlay);
            }
            if (passField != null) passField.clear();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void removePopupLayerIfPresent() {
        try {
            if (popupLayer != null && rootRef != null && rootRef.getChildren().contains(popupLayer)) {
                rootRef.getChildren().remove(popupLayer);
            }
            if (scrambleInput != null) scrambleInput.clear();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public void forceUnlockCam2() {
        if (cam2Done) return;
        removeOverlayIfPresent();
        cam2Done = true;
        if (baseButton != null) baseButton.setDisable(true);
        if (livesManager != null) livesManager.loseLife();
        audios.getfailMP().play();
        audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());

        if (parentComputerPane != null && livesManager != null) {
            parentComputerPane.updateCamsForLives(livesManager.getLives());
        }
    }

    public void forceUnlockCam3() {
        if (cam3Done) return;
        removePopupLayerIfPresent();
        cam3Done = true;
        if (base2Button != null) base2Button.setDisable(true); base2Button.setOpacity(0);
        if (livesManager != null) livesManager.loseLife();
        audios.getfailMP().play();
        audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());

        if (parentComputerPane != null && livesManager != null) {
            parentComputerPane.updateCamsForLives(livesManager.getLives());
        }
    }

    public boolean isCam2Done() { return cam2Done; }
    public boolean isCam3Done() { return cam3Done; }
}