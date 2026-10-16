package newpackage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomepageWindow {
    private Scene scene;
    private Button play, demo, scoreboard, settings;
    private HBox topBar;
    private Text userLabel;
    
    
    public HomepageWindow(mainApp mainApp, audios audios){
        Font StrangePath = Font.loadFont(getClass().getResourceAsStream("Strange Path.ttf"), 75);
        Font VCR_OSD_MONO = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 65);
        Font VCR_OSD_MONO2 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 47);
        Font VCR_OSD_MONO4 = Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 40);
        
        BackgroundImage homePagebg = new BackgroundImage(new Image(getClass().getResource("images/homepage.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        
        BackgroundImage DarkMetalPlate = new BackgroundImage(new Image(getClass().getResource("images/DarkMetalPlate.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        
        
        
        
        StackPane homePagePane = new StackPane();
        homePagePane.setBackground(new Background(homePagebg));
        
        
        
        Label CONTROLSHIFT = new Label("CONTROL SHIFT"); CONTROLSHIFT.setFont(StrangePath);
        CONTROLSHIFT.setTextFill(Color.rgb(147, 41, 33)); LabelGlow.Flicker(CONTROLSHIFT);

        play = new Button("PLAY"); 
        demo = new Button("DEMO");
        scoreboard = new Button("SCOREBOARD");
        settings = new Button("SETTINGS");
        
        homepageButton homepageButton = new homepageButton();
        
        homepageButton.newButton(play, VCR_OSD_MONO, DarkMetalPlate);
        homepageButton.newButton(demo, VCR_OSD_MONO, DarkMetalPlate);
        homepageButton.newButton(scoreboard, VCR_OSD_MONO4, DarkMetalPlate);
        homepageButton.newButton(settings, VCR_OSD_MONO2, DarkMetalPlate);
        
        VBox buttons = new VBox(play, demo, scoreboard, settings);
        
        topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setPrefHeight(50);
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Rectangle rect = new Rectangle(1920, 50); rect.setFill(Color.BLACK);
        StackPane rtop = new StackPane(rect, topBar);
        
        userLabel = new Text();
        userLabel.setFill(Color.WHITE);
        
        updateTopBar(mainApp.getWindow());
        
        
        homePagePane.getChildren().addAll(rtop, CONTROLSHIFT, buttons, audios.getGameAudioMV());
        rtop.setTranslateY(-470);
        CONTROLSHIFT.setTranslateX(-210); CONTROLSHIFT.setTranslateY(-280);
        buttons.setSpacing(-65); buttons.setTranslateX(450); buttons.setTranslateY(270);
        
        audios.getHomeAudioMP().setVolume(1.0); 
        audios.getHomeAudioMP().setCycleCount(MediaPlayer.INDEFINITE);
        
        
        play.setOnAction(e->{
            if (!CurrentUser.isLoggedIn()) {
                LoginWindow.show(mainApp.getWindow(), this);
                return;
            }
            else{
            mainApp.showLevelsMap();
            }
            });
        settings.setOnAction(e->SettingsWindow.show(mainApp.getWindow(), this, homePagePane, audios.getHomeAudioMP(), buttons));
        
        demo.setOnAction(e -> {
            TutorialWindow tut = new TutorialWindow(mainApp.getWindow(), this);
            mainApp.getWindow().setScene(tut.getScene());
        });
        
        scene = new Scene(homePagePane, 1950, 980);
        scene.setFill(Color.BLACK);

    }
    
    public Scene getScene(){
        return scene;
    }
    
    
    
    public Button getPlay(){
        return play;
    }
    public Button getDemo(){
        return demo;
    }
    public Button getScoreBoard(){
        return scoreboard;
    }
    
    public void updateTopBar(Stage stage) {
        topBar.getChildren().clear();

        if (!CurrentUser.isLoggedIn()) {
            Text loginText = new Text("Login / Sign up"); loginText.setCursor(Cursor.HAND);
            loginText.setFill(Color.WHITE);

            loginText.setOnMouseClicked(e -> LoginWindow.show(stage, this));

            topBar.getChildren().add(loginText);
        } else {
            Circle avatar = new Circle(12);
            avatar.setFill(CurrentUser.getGender().equals("female") ? Color.PINK : Color.LIGHTBLUE);

            userLabel.setText(CurrentUser.getUsername());
            topBar.getChildren().addAll(avatar, new Text("  "), userLabel);
        }
    }
}

