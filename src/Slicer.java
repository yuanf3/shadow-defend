import bagel.Input;
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
    private int lifetime;

    /**
     * Creates a new Slicer
     *
     * @param polyline The polyline that the slicer must traverse (must have at least 1 point)
     */
    public Slicer(List<Point> polyline, int health, double speed, int reward, int penalty, String sprite) {
        super(polyline.get(0), SPRITE_PATH + sprite);
        this.speed = speed;
        this.reward = reward;
        this.penalty = penalty;
        this.polyline = polyline;
        currentHealth = health;
        targetPointIndex = 1;
        finished = false;
        dead = false;
        lifetime = 120;
    }

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     */
    @Override
    public void update(Input input) {
        lifetime--;
        if (lifetime == 0) {
            dead = true;
        }
        if (finished || dead) {
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
        super.update(input);
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
}
