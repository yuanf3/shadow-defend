import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * Projectile class
 */
public class Projectile extends Sprite {

    private static final String SPRITE_PATH = "res/images/";
    private static final int SPEED = 10;

    private final Slicer target;
    private final int damage;

    private boolean collided;

    /**
     * Creates a new Projectile
     *
     * @param towerPosition The Point where the Projectile was fired from
     * @param sprite The image file of the Projectile
     * @param damage The damage dealt by the Projectile
     * @param target The target Slicer of the Projectile
     */
    public Projectile(Point towerPosition, String sprite, int damage, Slicer target) {
        super(towerPosition, SPRITE_PATH + sprite);
        this.damage = damage;
        this.target = target;
        collided = false;
    }

    /**
     * Checks for collision between Projectile and Slicers. If so, deals damage to Slicer
     *
     * @param slicers List of Slicers in the current game state
     * @return true if Projectile has collided, false otherwise
     */
    private boolean checkCollision(List<Slicer> slicers) {
        for (Slicer slicer : slicers) {
            if (slicer.getRect().intersects(this.getCenter())) {
                slicer.takeDamage(damage);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the current state of the Projectile. The Projectile moves towards its target Slicer,
     * checking for collision with any Slicers.
     *
     * @param slicers List of Slicers in the current game state
     */
    public void update(List<Slicer> slicers) {
        Point currentPoint = getCenter();
        Point targetPoint = target.getCenter();
        Vector2 targetVector = targetPoint.asVector();
        Vector2 currentVector = currentPoint.asVector();
        Vector2 direction = targetVector.sub(currentVector);

        if (checkCollision(slicers)) {
            collided = true;
            return;
        }

        // Update the sprite
        super.move(direction.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        super.update();
    }

    public boolean isCollided() {
        return collided;
    }
}
