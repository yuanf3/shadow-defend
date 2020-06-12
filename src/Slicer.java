import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Slicer class
 */
public abstract class Slicer extends Sprite {

    private static final String SPRITE_PATH = "res/images/";

    private final double speed;
    private final int reward;
    private final int penalty;
    private final List<Point> polyline;

    private int currentHealth;
    private int targetPointIndex;
    private boolean finished;
    private boolean dead;

    /**
     * Creates a new Slicer
     *
     * @param polyline The polyline that the slicer must traverse (must have at least 1 point)
     */
    public Slicer(Point start, List<Point> polyline, int targetPointIndex, int health, double speed, int reward, int penalty, String sprite) {
        super(start, SPRITE_PATH + sprite);
        this.polyline = polyline;
        this.targetPointIndex = targetPointIndex;
        this.speed = speed;
        this.reward = reward;
        this.penalty = penalty;
        currentHealth = health;
        finished = false;
        dead = false;
    }

    /**
     * Spawns child slicers upon death
     *
     * @param slicers List of slicers in the current game state
     */
    public abstract void spawnOnDeath(List<Slicer> slicers);

    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     */
    public void update() {
        if (currentHealth <= 0) {
            dead = true;
            return;
        }

        if (finished) {
            return;
        }

        // Obtain current and target points, convert to vectors
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 direction = target.sub(current);
        double distance = direction.length();
        // Check if close to target point
        if (distance < speed * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Target the next point in the polyline
                targetPointIndex++;
            }
        }

        // Update the sprite
        super.move(direction.normalised().mul(speed * ShadowDefend.getTimescale()));
        super.setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update();
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDead() {
        return dead;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getReward() {
        return reward;
    }

    public List<Point> getPolyline() {
        return polyline;
    }

    public int getTargetPointIndex() {
        return targetPointIndex;
    }
}
