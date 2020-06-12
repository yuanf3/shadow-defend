import bagel.util.Point;
import bagel.util.Vector2;

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
     * @param start The Point to begin at
     * @param polyline The polyline to traverse (must have at least 1 point)
     * @param targetPointIndex The immediate next Point of the polyline
     * @param health The amount of health to begin with
     * @param speed The movement speed in pixels/second
     * @param reward The amount of money awarded to the Player if dead
     * @param penalty The amount of lives deducted from the Player if finished
     * @param sprite The image file of the Slicer
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
     * Spawns child Slicers upon death
     *
     * @param slicers List of Slicers in the current game state
     */
    public abstract void spawnOnDeath(List<Slicer> slicers);

    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    /**
     * Updates the current state of the Slicer. The Slicer moves towards its next target point in
     * the polyline.
     */
    public void update() {
        if (currentHealth <= 0) {
            dead = true;
            return;
        }

        // Obtain current and target points, convert to vectors
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        Vector2 targetVector = targetPoint.asVector();
        Vector2 currentVector = currentPoint.asVector();
        Vector2 direction = targetVector.sub(currentVector);
        double distance = direction.length();
        // Check if slicer is close to its target point
        if (distance < speed * ShadowDefend.getTimescale()) {
            // Check if slicer has reached the end of the polyline
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Otherwise, target the next point in the polyline
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
