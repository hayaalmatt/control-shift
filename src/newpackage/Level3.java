package newpackage;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Level3 {
    private Scene Gamescene;

    private TimeSabotage currentTimeSabotage = null;

    public Level3(mainApp mainApp, audios audios, LivesManager livesManager){
        StackPane gamePane = new StackPane();

        BackgroundImage office6 = new BackgroundImage(
            new Image(getClass().getResource("images/office6.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );
        BackgroundImage office7 = new BackgroundImage(
            new Image(getClass().getResource("images/office7.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );
        BackgroundImage office8 = new BackgroundImage(
            new Image(getClass().getResource("images/office8.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );
        BackgroundImage office9 = new BackgroundImage(
            new Image(getClass().getResource("images/office9.JPG").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        gamePane.setBackground(new Background(office6));

        Gamescene = new Scene(gamePane, 1950, 980);
        Gamescene.setFill(javafx.scene.paint.Color.BLACK);

        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 47);

        Button stickyNoteBtn = new Button();
        ImageView stickyNoteDisplay = new ImageView(new Image(getClass().getResource("images/stickynotepass.PNG").toExternalForm()));
        invisibleGameButtons.newButton(stickyNoteBtn, gamePane, VCR_OSD_MONO2, stickyNoteDisplay,
                57, 67, 157, 25, 688, 384, -190, -120, audios.getsnMP());

        Button NotePadBtn = new Button();
        ImageView NotePadDisplay = new ImageView(new Image(getClass().getResource("images/copybookcode.PNG").toExternalForm()));
        invisibleGameButtons.newButton(NotePadBtn, gamePane, VCR_OSD_MONO2, NotePadDisplay,
                83, 45, 183, 185, 1221, 883, 265, -360, audios.getcopybookMP());

        Button bar3 = new Button();
        bar3.setCursor(Cursor.HAND);
        BackgroundImage bar3bg = new BackgroundImage(new Image(getClass().getResource("images/bar3.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        bar3.setBackground(new Background(bar3bg));
        bar3.setPrefSize(85, 85);
        gamePane.getChildren().add(bar3);
        bar3.setTranslateX(-610);
        bar3.setTranslateY(-430);

        Button laptopBtn = new Button();
        laptopBtn.setCursor(Cursor.HAND);
        laptopBtn.setPrefSize(200, 170);
        laptopBtn.setOpacity(0);
        laptopBtn.setTranslateX(-20);
        laptopBtn.setTranslateY(10);
        gamePane.getChildren().add(laptopBtn);

        ComputerPane cp = new ComputerPane();
        cp.showCompPane(gamePane, laptopBtn, audios, livesManager, true, false);
        cp.updateCamsForLives(3);

        Warnings w = new Warnings();

        final SystemOverheatTask sot = new SystemOverheatTask();
        final boolean[] overheatActive = {false};
        final boolean[] overheatCompleted = {false};

        Timeline overheatTrigger = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            PauseTransition smallPause = new PauseTransition(Duration.seconds(0.5));
            smallPause.setOnFinished(p -> {
                overheatActive[0] = true;
                overheatCompleted[0] = false;

                w.showBanner(gamePane, "SYSTEM OVERHEAT! Quick! Check your computer to fix it!");
                ImageView overheatWarning = new ImageView(new Image(getClass().getResource("images/systemheat.PNG").toExternalForm()));
                w.showWarning(gamePane, overheatWarning);

                try {
                    if (audios != null && audios.getoverheatMP() != null) {
                        javafx.scene.media.MediaPlayer mp = audios.getoverheatMP();
                        mp.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
                        mp.play();
                    }
                } catch (Exception ignored) {}

                laptopBtn.setOnAction(e2 -> {
                    try {
                        if (cp != null) {
                            cp.openCamsTab(); 
                        }
                    } catch (Exception ignored) {}
                    if (!gamePane.getChildren().contains(sot.getTaskPane())) {
                        sot.startTask(Gamescene);
                        gamePane.getChildren().add(sot.getTaskPane());
                        
                        sot.getTaskPane().requestFocus();
                    }
                });

                sot.setOnSuccess(() -> {
                    if (!overheatActive[0]) return;
                    overheatCompleted[0] = true;
                    overheatActive[0] = false;
                    try {
                        if (audios != null && audios.getoverheatMP() != null) {
                            javafx.scene.media.MediaPlayer mp = audios.getoverheatMP();
                            mp.stop();
                            mp.setCycleCount(1);
                        }
                    } catch (Exception ignored) {}
                    ImageView overheatTip = new ImageView(new Image(getClass().getResource("images/overheatTIP.png").toExternalForm()));
                    w.showTip(gamePane, overheatTip);
                    try {
                        if (audios != null && audios.getsuccessMP() != null) {
                            audios.getsuccessMP().play(); audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());
                        }
                    } catch (Exception ignored) {}

                    if (gamePane.getChildren().contains(sot.getTaskPane())) {
                        gamePane.getChildren().remove(sot.getTaskPane());
                    }
                    laptopBtn.setOnAction(ev2 -> {
                        try { if (cp != null) cp.openCamsTab(); } catch (Exception ignored) {}
                    });
                });

            });
            smallPause.play();
        }));
        overheatTrigger.setCycleCount(1);
        overheatTrigger.play();

        Timeline clock7 = new Timeline(new KeyFrame(Duration.seconds(15), ev -> {
            gamePane.setBackground(new Background(office7));
            try { audios.getnewhourMP().play(); audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop()); } catch (Exception ignored){}

            if (overheatActive[0] && !overheatCompleted[0]) {
                overheatActive[0] = false;
                overheatCompleted[0] = true;
                try {
                    if (audios != null && audios.getoverheatMP() != null) {
                        javafx.scene.media.MediaPlayer mp = audios.getoverheatMP();
                        mp.stop(); mp.setCycleCount(1);
                    }
                } catch (Exception ignored) {}
                if (livesManager != null) livesManager.loseLife();
                ImageView overheatTip = new ImageView(new Image(getClass().getResource("images/overheatTIP.png").toExternalForm()));
                w.showTip(gamePane, overheatTip);
                try { audios.getfailMP().play(); audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop()); } catch (Exception ignored){}
                
                if (gamePane.getChildren().contains(sot.getTaskPane())) gamePane.getChildren().remove(sot.getTaskPane());

                laptopBtn.setOnAction(e2 -> {
                    try { if (cp != null) cp.openCamsTab(); } catch (Exception ignored) {}
                });
            }

            PauseTransition wait3 = new PauseTransition(Duration.seconds(3));
            wait3.setOnFinished(ev2 -> {
                TimeSabotage TS = new TimeSabotage();
                currentTimeSabotage = TS;
                TS.show(gamePane, 7, livesManager);
                try { audios.getdieMP().play(); audios.getdieMP().setOnEndOfMedia(()->audios.getdieMP().stop()); } catch (Exception ignored){}
                Timeline poll = new Timeline(new KeyFrame(Duration.seconds(1), ev3 -> {
                    if (TS.isCompleted()) {
                        ImageView clockTip3 = new ImageView(new Image(getClass().getResource("images/clockTIP3.png").toExternalForm()));
                        w.showTip(gamePane, clockTip3);
                    }
                }));
                poll.setCycleCount(3); 
                poll.play();
            });
            wait3.play();

        }));
        clock7.setCycleCount(1);
        clock7.play();

        Timeline clock8 = new Timeline(new KeyFrame(Duration.seconds(30), ev -> {
            if (currentTimeSabotage != null && !currentTimeSabotage.isCompleted()) {
                try {
                    currentTimeSabotage.forceFail();
                } catch (Exception ignored) {}
                currentTimeSabotage = null;
            }

            gamePane.setBackground(new Background(office8));
            try { audios.getnewhourMP().play(); audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop()); } catch (Exception ignored){}
            w.showBanner(gamePane, "Phew! We're almost there! Finish the last task quickly!");
            ImageView camerasWarning = new ImageView(new Image(getClass().getResource("images/camerasab.PNG").toExternalForm()));
            w.showWarning(gamePane, camerasWarning);

            try {
                if (audios != null && audios.getcamerasGlitchMP() != null) {
                    audios.getcamerasGlitchMP().play(); audios.getcamerasGlitchMP().setOnEndOfMedia(()->audios.getcamerasGlitchMP().stop());
                }
            } catch (Exception ignored) {}

            cp.showCameraGlitchOverlay();
            cp.enableDebugTab(true);

            cp.setDebugFinishHandler(success -> {
                try {
                    cp.removeCameraGlitchOverlay();
                    cp.enableDebugTab(false);

                    ImageView debugTip = new ImageView(new Image(getClass().getResource("images/debugTIP2.png").toExternalForm()));
                    w.showTip(gamePane, debugTip);

                    if (success) {
                        audios.getsuccessMP().play();
                        audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());
                        cp.updateCamsForLives(livesManager.getLives());
                    } else {
                        if (livesManager != null) livesManager.loseLife();
                        audios.getfailMP().play();
                        audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                        cp.updateCamsForLives(livesManager.getLives());
                    }
                } catch (Exception ignored) {}
            });

        }));
        clock8.setCycleCount(1);
        clock8.play();

        Timeline clock9 = new Timeline(new KeyFrame(Duration.seconds(45), ev -> {
            gamePane.setBackground(new Background(office9));
            try { audios.getfinalhourMP().play(); audios.getfinalhourMP().setOnEndOfMedia(()->audios.getfinalhourMP().stop()); } catch (Exception ignored){}
            
            if (!cp.isDebugFinished()) {
                cp.removeCameraGlitchOverlay();
                cp.enableDebugTab(false);
                if (livesManager != null) livesManager.loseLife();
                try { audios.getfailMP().play(); audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop()); } catch (Exception ignored){}
                ImageView debugTip = new ImageView(new Image(getClass().getResource("images/debugTIP2.png").toExternalForm()));
                w.showTip(gamePane, debugTip);
                cp.updateCamsForLives(livesManager.getLives());
            }

            Timeline finalWin = new Timeline(new KeyFrame(Duration.seconds(3), e2 -> {
                if (livesManager.getLives() > 0) {
                    mainApp.showFinalWinnerPage();
                }
            }));
            finalWin.setCycleCount(1);
            finalWin.play();

        }));
        clock9.setCycleCount(1);
        clock9.play();

        livesManager.setOnLifeChanged(lives -> cp.updateCamsForLives(lives));

        final StackPane gamePaneRef = gamePane;
        bar3.setOnAction(e -> bar3Window.show(mainApp.getWindow(), mainApp, gamePaneRef, audios));
    }

    public Scene getScene(){
        return Gamescene;
    }
}