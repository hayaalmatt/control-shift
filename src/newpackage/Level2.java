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
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Level2 {
    private Scene Gamescene;
    
    public Level2(mainApp mainApp, audios audios, LivesManager livesManager){
        StackPane gamePane = new StackPane();

        BackgroundImage office3pm = new BackgroundImage(
            new Image(getClass().getResource("images/office3.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );
        BackgroundImage office4pm = new BackgroundImage(
            new Image(getClass().getResource("images/office4.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );
        BackgroundImage office5pm = new BackgroundImage(
            new Image(getClass().getResource("images/office5.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        BackgroundImage office6pm = new BackgroundImage(
            new Image(getClass().getResource("images/office6.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        BackgroundImage office4off = new BackgroundImage(
            new Image(getClass().getResource("images/office4off.jpeg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        gamePane.setBackground(new Background(office3pm));

        Gamescene = new Scene(gamePane, 1950, 980);
        Gamescene.setFill(Color.BLACK);

        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 47);

        Button stickyNoteBtn = new Button();
        ImageView stickyNoteDisplay = new ImageView(new Image(getClass().getResource("images/stickynotepass.PNG").toExternalForm()));
        invisibleGameButtons.newButton(stickyNoteBtn, gamePane, VCR_OSD_MONO2, stickyNoteDisplay, 57, 67, 157, 25, 688, 384, -190, -120, audios.getsnMP());

        Button NotePadBtn = new Button();
        ImageView NotePadDisplay = new ImageView(new Image(getClass().getResource("images/copybookcode.PNG").toExternalForm()));
        invisibleGameButtons.newButton(NotePadBtn, gamePane, VCR_OSD_MONO2, NotePadDisplay, 83, 45, 183, 185, 1221, 883, 265, -360, audios.getcopybookMP());

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
        cp.showCompPane(gamePane, laptopBtn, audios, livesManager, true, true);

        cp.updateCamsForLives(3);

        Warnings w = new Warnings();

        Timeline startCode = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            w.showBanner(gamePane, "Oh no! the robot seemed to have hacked our code! Check the Code Tab on your computer.");
            ImageView codesab = new ImageView(new Image(getClass().getResource("images/codesab.PNG").toExternalForm()));
            w.showWarning(gamePane, codesab);
            audios.getcodeSabotageMP().play();
            audios.getcodeSabotageMP().setOnEndOfMedia(()->audios.getcodeSabotageMP().stop());

            cp.openCodeTab();

            cp.setCodeTaskFinishHandler(success -> {
                cp.closeCodeTaskAndDisableTab();
                ImageView codeTip = new ImageView(new Image(getClass().getResource("images/codeTIP.png").toExternalForm()));
                w.showTip(gamePane, codeTip);
            });
        }));
        startCode.setCycleCount(1);
        startCode.play();

        final LightTask[] lightTaskHolder = new LightTask[1];

        Timeline t1 = new Timeline(new KeyFrame(Duration.seconds(30), ev -> {
            gamePane.setBackground(new Background(office4pm));
            audios.getnewhourMP().play();
            audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop());

            if (!cp.isCodeTaskCompleted()) {
                cp.closeCodeTaskAndDisableTab();
                if (livesManager != null) livesManager.loseLife();
                audios.getfailMP().play();
                audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                ImageView codeTip = new ImageView(new Image(getClass().getResource("images/codeTIP.png").toExternalForm()));
                w.showTip(gamePane, codeTip);
            }

            LightTask lt = new LightTask(audios); 
            lightTaskHolder[0] = lt;

            gamePane.setBackground(new Background(office4off));
            w.showBanner(gamePane, "Power failure! Restore the lights!");

            ImageView lightsWarning = new ImageView(new Image(getClass().getResource("images/lightsab.PNG").toExternalForm()));
            w.showWarning(gamePane, lightsWarning);

            try {
                if (audios != null && audios.getlightsOffMP() != null) {
                    audios.getlightsOffMP().play();
                    audios.getlightsOffMP().setOnEndOfMedia(()->audios.getlightsOffMP().stop());
                }
            } catch (Exception ignored) {}

            laptopBtn.setDisable(true);
            cp.closeComputer();

            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(d -> {
                lt.setOnFinish(success -> {
                    if (lt.isFinished()) {
                        laptopBtn.setDisable(false);

                        ImageView lightTip = new ImageView(new Image(getClass().getResource("images/lightTIP.png").toExternalForm()));
                        w.showTip(gamePane, lightTip);

                        if (success) {
                            gamePane.setBackground(new Background(office4pm));
                            audios.getsuccessMP().play();
                            audios.getsuccessMP().setOnEndOfMedia(()->audios.getsuccessMP().stop());
                        } else {
                            gamePane.setBackground(new Background(office4pm));
                            if (livesManager != null) livesManager.loseLife();
                            audios.getfailMP().play();
                            audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                        }
                    }
                });

                if (!gamePane.getChildren().contains(lt.getRoot())) {
                    gamePane.getChildren().add(lt.getRoot());
                }
                lt.show();
            });
            delay.play();

        }));
        t1.setCycleCount(1);
        t1.play();

        Timeline t2 = new Timeline(new KeyFrame(Duration.seconds(60), ev -> {
            gamePane.setBackground(new Background(office5pm));
            audios.getnewhourMP().play();
            audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop());

            w.showBanner(gamePane, "Oh no! The robot has hacked our cameras! Check the computer");
            ImageView camerasWarning = new ImageView(new Image(getClass().getResource("images/camerasab.PNG").toExternalForm()));
            w.showWarning(gamePane, camerasWarning);

            try {
                if (audios != null && audios.getcamerasGlitchMP() != null) {
                    audios.getcamerasGlitchMP().play();
                    audios.getcamerasGlitchMP().setOnEndOfMedia(()->audios.getcamerasGlitchMP().stop());
                }
            } catch (Exception ignored) {}

            cp.showCameraGlitchOverlay();

            cp.enableDebugTab(true);

            cp.setDebugFinishHandler(success -> {
                try {
                    cp.removeCameraGlitchOverlay();
                    cp.enableDebugTab(false);

                    ImageView debugTip = new ImageView(new Image(getClass().getResource("images/debugTIP.png").toExternalForm()));
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
        t2.setCycleCount(1);
        t2.play();

        Timeline t3 = new Timeline(new KeyFrame(Duration.seconds(90), ev -> {
            
            gamePane.setBackground(new Background(office6pm));
            audios.getfinalhourMP().play();
            audios.getfinalhourMP().setOnEndOfMedia(()->audios.getfinalhourMP().stop());

            if (!cp.isDebugFinished()) {
                cp.removeCameraGlitchOverlay();
                cp.enableDebugTab(false);

                if (livesManager != null) livesManager.loseLife();

                audios.getfailMP().play();
                audios.getfailMP().setOnEndOfMedia(()->audios.getfailMP().stop());
                ImageView debugTip = new ImageView(new Image(getClass().getResource("images/debugTIP.png").toExternalForm()));
                w.showTip(gamePane, debugTip);

                cp.updateCamsForLives(livesManager.getLives());
            }

            Timeline winTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e2 -> {
                if (livesManager.getLives() > 0) {
                    mainApp.unlockNextLevel(2);
                    mainApp.showWinnerPage();
                }
            }));
            winTimeline.setCycleCount(1);
            winTimeline.play();

        }));
        t3.setCycleCount(1);
        t3.play();

        livesManager.setOnLifeChanged(lives -> {
            cp.updateCamsForLives(lives);
        });

        bar3.setOnAction(e -> bar3Window.show(mainApp.getWindow(), mainApp, gamePane, audios));
    }

    public Scene getScene(){
        return Gamescene;
    }
}