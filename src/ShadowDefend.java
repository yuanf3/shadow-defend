import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * ShadowDefend, a tower defence game.
 */
public class ShadowDefend extends AbstractGame {

    // Window dimensions and FPS are public for easier access
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1024;
    public static final double FPS = 60;

    // Other constants
    private static final int INITIAL_TIMESCALE = 1;
    private static final int MAX_TIMESCALE = 5;
    private static final int NUM_LEVELS = 2;

    private static int timescale = INITIAL_TIMESCALE;
    private static String status;
    private int currentLevel;
    private Level level;

    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        currentLevel = 1;
        level = new Level(currentLevel);
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    public static int getTimescale() {
        return timescale;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        ShadowDefend.status = status;
    }

    /**
     * Increases the timescale by 1
     */
    private void increaseTimescale() {
        if (timescale < MAX_TIMESCALE) {
            timescale++;
        }
    }

    /**
     * Decreases the timescale by 1
     */
    private void decreaseTimescale() {
        if (timescale > INITIAL_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * Updates the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // Exit game
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // Handle timescale changes
        if (input.wasPressed(Keys.L)) {
            increaseTimescale();
        }
        if (input.wasPressed(Keys.K)) {
            decreaseTimescale();
        }

        // Let the Level class do everything
        level.update(input);

        // Check for level completion
        if (level.isLevelFinished()) {
            if (currentLevel < NUM_LEVELS) {
                // Reset game state, go to next level
                currentLevel++;
                level = new Level(currentLevel);
            } else {
                // Winner!
                setStatus("Winner!");
            }
        }

    }
}
