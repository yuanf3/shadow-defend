import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Airplane class
 */
public class Airplane extends Sprite {

    private static final String SPRITE = "res/images/airsupport.png";
    private static final int OUTSIDE = -50;
    private static final Vector2 H_VECTOR = new Vector2(1,0);
    private static final Vector2 V_VECTOR = new Vector2(0,1);
    private static final double H_ANGLE = Math.PI/2;
    private static final double V_ANGLE = Math.PI;
    private static final int SPEED = 3;
    private static final int COST = 500;

    private static boolean horizontal = true;

    private final boolean this_horizontal;
    private final Vector2 direction;
    private final double angle;
    private final List<Explosive> explosives;
    private double drop_time;
    private double time;
    private boolean finished;

    /**
     * Creates a new Airplane
     *
     * @param position The mouse position
     */
    public Airplane(Point position) {
        super(getStartPoint(position), SPRITE);
        this_horizontal = horizontal;
        direction = this_horizontal ? H_VECTOR : V_VECTOR;
        angle = this_horizontal ? H_ANGLE : V_ANGLE;

        explosives = new ArrayList<>();
        drop_time = generate_random_time();
        time = Integer.MAX_VALUE;
        finished = false;

        // Alternate the direction for next airplane
        horizontal = !horizontal;
    }

    /**
     * Determines the Point outside the map to spawn the Airplane at
     *
     * @param position The supplied starting location of the airplane
     * @return The actual starting point for the airplane, depending on direction of movement
     */
    private static Point getStartPoint(Point position) {
        if (horizontal) {
            return new Point(OUTSIDE, position.y);
        }
        return new Point(position.x, OUTSIDE);
    }

    /**
     * Generate a random number of seconds
     *
     * @return A number between 1 and 2
     */
    private double generate_random_time() {
        Random rand = new Random();
        return rand.nextDouble() + 1;
    }

    /**
     * Check if Airplane has left the map and all its Explosives have exploded
     *
     * @return true if airplane is finished, otherwise false
     */
    private boolean checkFinished() {
        if (this_horizontal && (getCenter().x > ShadowDefend.WIDTH) && explosives.isEmpty()) {
            return true;
        }
        if (!this_horizontal && (getCenter().y > ShadowDefend.HEIGHT) && explosives.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the current state of the Airplane
     *
     * @param slicers List of Slicers in the current game state
     */
    public void update(List<Slicer> slicers) {
        if (checkFinished()) {
            finished = true;
            return;
        }

        // Check to drop explosives
        time += ShadowDefend.getTimescale();
        if (time / ShadowDefend.FPS >= drop_time) {
            if (!ShadowDefend.isOutsideWindow(this.getCenter())) {
                explosives.add(new Explosive(this.getCenter()));
                time = 0;
                drop_time = generate_random_time();
            }
        }

        // Update explosives
        for (int i = explosives.size() - 1; i >= 0; i--) {
            Explosive explosive = explosives.get(i);
            explosive.update(slicers);
            if (explosive.isExploded()) {
                explosives.remove(i);
            }
        }

        // Update the sprite
        super.setAngle(angle);
        super.move(direction.mul(SPEED * ShadowDefend.getTimescale()));
        super.update();
    }

    public static int getCOST() {
        return COST;
    }

    public boolean isFinished() {
        return finished;
    }
}
