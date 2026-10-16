package newpackage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainApp extends Application{
    private HBox topBar;
    private Text userLabel;
    private Stage window;

    audios audios = new audios();
    private LivesManager livesManager;

    // Track highest unlocked level (1..3). Default: only level 1 unlocked.
    private int unlockedLevels = 1;

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        this.livesManager = new LivesManager(this);
        // When lives hit 0, show loser page
        this.livesManager.setOnGameOver(() -> Platform.runLater(() -> showJumpscareScene()));
        showHomepage();
        window.setTitle("Control Shift");
        window.show();
    }

    public void showHomepage(){
        HomepageWindow hpw = new HomepageWindow(this, audios);
        window.setScene(hpw.getScene());
        audios.getHomeAudioMP().play();
        audios.getGameAudioMP().stop();
    }

    public void showLevelsMap(){
        LevelsMap lv = new LevelsMap(this);
        window.setScene(lv.getScene());
        audios.getHomeAudioMP().play();
        audios.getGameAudioMP().stop();
    }

    public void showVideoScene(){
        audios.getHomeAudioMP().stop();
        OpeningVideo ov = new OpeningVideo(this);
        window.setScene(ov.getScene());
    }

    public void showJumpscareScene(){
        audios.getGameAudioMP().stop();
        JumpscareVid js = new JumpscareVid(this);
        window.setScene(js.getScene());
    }

    // pass livesManager to levels so tasks & camera code can deduct lives and update cameras
    public void showLvl1(){
        if (livesManager != null) livesManager.reset();
        Level1 l1 = new Level1(this, audios, livesManager);
        window.setScene(l1.getScene());
        audios.getGameAudioMP().play();
    }

    public void showLvl2(){
        audios.getHomeAudioMP().stop();
        audios.getGameAudioMP().play();
        if (livesManager != null) livesManager.reset();
        Level2 l2 = new Level2(this, audios, livesManager);
        window.setScene(l2.getScene());
    }

    public void showLvl3(){
        audios.getHomeAudioMP().stop();
        audios.getGameAudioMP().play();
        if (livesManager != null) livesManager.reset();
        Level3 l3 = new Level3(this, audios, livesManager);
        window.setScene(l3.getScene());
    }

    public void showWinnerPage(){
        audios.getvictoryMP().play();
        audios.getvictoryMP().setOnEndOfMedia(()->audios.getvictoryMP().stop());
        WinnerPage wp = new WinnerPage(this);
        window.setScene(wp.getScene());
    }

    /**
     * showFinalWinnerPage - used when the player completes the entire game (level 3).
     * Uses a special banner that removes "Next Level" and shows completion text.
     */
    public void showFinalWinnerPage(){
        audios.getvictoryMP().play();
        audios.getvictoryMP().setOnEndOfMedia(()->audios.getvictoryMP().stop());
        WinnerFinalPage wfp = new WinnerFinalPage(this);
        window.setScene(wfp.getScene());
    }

    public void showLoserPage(){
        LoserPage lp = new LoserPage(this);
        window.setScene(lp.getScene());
    }

    public Stage getWindow(){
        return window;
    }

    public LivesManager getLivesManager() {
        return livesManager;
    }

    public int getUnlockedLevels() {
        return unlockedLevels;
    }

    public void unlockNextLevel(int currentLevel) {
        int target = Math.min(currentLevel + 1, 3);
        if (unlockedLevels < target) {
            unlockedLevels = target;
        }
    }

    public void setUnlockedLevels(int lvl) {
        if (lvl < 1) lvl = 1;
        if (lvl > 3) lvl = 3;
        unlockedLevels = lvl;
    }

    public static void main(String[] args){
        launch(args);
    }
}