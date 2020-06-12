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

    public Projectile(Point towerPosition, String sprite, int damage, Slicer target) {
        super(towerPosition, SPRITE_PATH + sprite);
        this.damage = damage;
        this.target = target;
        collided = false;
    }

    /**
     * Checks for collision between projectile and slicer. If so, deals damage to slicer
     *
     * @param slicers List of slicers in the current game state
     * @return true if projectile has collided, false otherwise
     */
    private boolean checkCollision(List<Slicer> slicers) {
        for (Slicer s : slicers) {
            if (s.getRect().intersects(this.getRect())) {
                s.takeDamage(damage);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the current state of the projectile. The projectile moves towards its target slicer,
     * checking for collision with any slicers.
     *
     * @param slicers List of slicers in the current game state
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
