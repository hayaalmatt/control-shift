package newpackage;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Level1 {
    private Scene Gamescene;
    CameraBackgrounds CB = new CameraBackgrounds();

    private Timeline winTimeline = null;

    private TimeSabotage currentTimeSabotage = null;

    private final Warnings w = new Warnings();


    public Level1(mainApp mainApp, audios audios, LivesManager livesManager){
        Font StrangePath = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 75);
        Font StrangePath2 = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 30);
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 65);
        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 47);
        Font VCR_OSD_MONO3 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 24);
        Font VCR_OSD_MONO4 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 40);

        StackPane gamePane = new StackPane();
        BackgroundImage office12 = new BackgroundImage(new Image(getClass().getResource("images/office12.JPG").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        gamePane.setBackground(new Background(office12));
        BackgroundImage office1 = new BackgroundImage(new Image(getClass().getResource("images/office1.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        BackgroundImage office2 = new BackgroundImage(new Image(getClass().getResource("images/office2.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        BackgroundImage office3 = new BackgroundImage(new Image(getClass().getResource("images/office3.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        ImageView cam2tip = new ImageView(new Image(getClass().getResource("images/cam2TIP.png").toExternalForm()));
        ImageView cam3tip = new ImageView(new Image(getClass().getResource("images/cam3TIP.png").toExternalForm()));
        ImageView clock2tip = new ImageView(new Image(getClass().getResource("images/clockTIP2.png").toExternalForm()));

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
        cp.showCompPane(gamePane, laptopBtn, audios, livesManager, false, false);

        cp.updateCams(new BackgroundImage(new Image(getClass().getResource("images/camlocked.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)));

        livesManager.setOnLifeChanged(lives -> {
            if (!cp.isCam2Done()) {
                cp.updateCams(new BackgroundImage(new Image(getClass().getResource("images/camlocked.jpeg").toExternalForm()),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, false)));
            } else {
                cp.updateCamsForLives(lives);
            }

            if (lives <= 0) {
                if (winTimeline != null) {
                    winTimeline.stop();
                    winTimeline = null;
                }
            }
        });

        HomepageWindow hw = new HomepageWindow(mainApp, audios);

        bar3.setOnAction(e->bar3Window.show(mainApp.getWindow(), mainApp, gamePane, audios));

        Gamescene = new Scene(gamePane, 1950, 980);
        Gamescene.setFill(Color.BLACK);

        Timeline clock12 = new Timeline(
                new KeyFrame(Duration.seconds(3), e->{
                    w.showBanner(gamePane, "Your shift has begun: "
                            + "\nGo to your computer to unlock camera 2 before"
                            + " the clock hits 1!");
                }));
        clock12.setCycleCount(1);
        clock12.play();

        Timeline clock1 = new Timeline(
                new KeyFrame(Duration.seconds(30), e -> {
                    gamePane.setBackground(new Background(office1));
                    audios.getnewhourMP().play();
                    audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop());
                    w.showBanner(gamePane, "Don't forget to unlock camera 3! "
                            + "\nNote: Keep a close eye on the cameras, the closer the robot, the closer the failure.");//time becomes 1am
                    
                    if (!cp.isCam2Done()) {
                        cp.forceUnlockCam2();
                        showTip(gamePane, cam2tip);
                    }
                })
        );
        clock1.setCycleCount(1);
        clock1.play();

        Timeline clock2 = new Timeline(
                new KeyFrame(Duration.seconds(60), e -> {
                    gamePane.setBackground(new Background(office2));
                    audios.getnewhourMP().play();
                    audios.getnewhourMP().setOnEndOfMedia(()->audios.getnewhourMP().stop());
                    w.showBanner(gamePane, "Tip: Watch out for the time!");
                    if (!cp.isCam3Done()) {
                        cp.forceUnlockCam3();
                        showTip(gamePane, cam3tip);
                    }
                    TimeSabotage TS = new TimeSabotage();
                    currentTimeSabotage = TS;
                    TS.show(gamePane, 2, livesManager);
                    audios.getdieMP().play();
                })
        );
        clock2.setCycleCount(1);
        clock2.play();

        Timeline clock3 = new Timeline(
                new KeyFrame(Duration.seconds(90), e->{
                    gamePane.setBackground(new Background(office3));
                    audios.getfinalhourMP().play();
                    audios.getfinalhourMP().setOnEndOfMedia(()->audios.getfinalhourMP().stop());
                    if (currentTimeSabotage != null && !currentTimeSabotage.isCompleted()) {
                        currentTimeSabotage.forceFail();
                        showTip(gamePane, clock2tip);
                    }
                    winTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e2 -> {
                        if (livesManager.getLives() > 0) {
                            mainApp.unlockNextLevel(1);
                            mainApp.showWinnerPage();
                        }
                    }));
                    winTimeline.setCycleCount(1);
                    winTimeline.play();
                }));
        clock3.setCycleCount(1);
        clock3.play();
    }

    public Scene getScene(){
        return Gamescene;
    }

    public static boolean Level1Success(){
        return false;
    }

    public void showTip(StackPane gameroot, ImageView img){
        img.setFitWidth(img.getFitWidth()/2); img.setFitHeight(img.getFitHeight()/2);
        img.setTranslateX(-400); img.setTranslateY(-300);
        img.setOpacity(0);

        gameroot.getChildren().add(img);

        FadeTransition fadein = new FadeTransition(Duration.millis(250), img);
        fadein.setFromValue(0); fadein.setToValue(1);

        PauseTransition wait = new PauseTransition(Duration.seconds(4));

        FadeTransition fadeout = new FadeTransition(Duration.millis(250), img);
        fadeout.setFromValue(1); fadeout.setToValue(0);

        SequentialTransition sequence = new SequentialTransition(fadein, wait, fadeout);
        sequence.play();
    }
}