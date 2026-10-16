package newpackage;

import java.util.function.Consumer;


public class LivesManager {
    private int lives = 3;
    private Consumer<Integer> onLifeChanged = l -> {};
    private Runnable onGameOver = () -> {};
    private final mainApp app;

    public LivesManager(mainApp app) {
        this.app = app;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives <= 0) return;
        lives--;
        try { onLifeChanged.accept(lives); } catch (Exception e) { e.printStackTrace(); }
        if (lives <= 0) {
            try { onGameOver.run(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public void setOnLifeChanged(Consumer<Integer> cb) {
        if (cb != null) this.onLifeChanged = cb;
    }

    public void setOnGameOver(Runnable r) {
        if (r != null) this.onGameOver = r;
    }

    public void reset() {
        lives = 3;
        onLifeChanged.accept(lives);
    }
}