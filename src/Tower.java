import bagel.Input;
import bagel.util.Point;

import java.util.List;

/**
 * Tower class
 */
public abstract class Tower extends Sprite {

    private static final String SPRITE_PATH = "res/images/";
    private static final int MILLISEC_TO_SEC = 1000;
    // Tower sprites need to be rotated 90 degrees to be consistent with direction
    private static final double ROTATION = Math.PI/2;

    private final int radius;
    private final int cooldown;
    private final Point position;

    private double time;

    public Tower(Point position, int radius, int cooldown, String sprite) {
        super(position, SPRITE_PATH + sprite);
        this.radius = radius;
        this.cooldown = cooldown;
        this.position = position;
        time = Integer.MAX_VALUE;
    }

    /**
     * Finds a target slicer to aim at
     *
     * @param slicers List of slicers in the current game state
     * @return The Slicer to target if available, otherwise null
     */
    private Slicer findTarget(List<Slicer> slicers) {
        for (Slicer s : slicers) {
            Point targetPoint = s.getCenter();
            if (targetPoint.distanceTo(position) <= radius) {
                return s;
            }
        }
        return null;
    }

    /**
     * Shoots a projectile towards the target Slicer
     *
     * @param target The target Slicer
     * @param projectiles List of projectiles in the current game state
     */
    public abstract void shootProjectile(Slicer target, List<Projectile> projectiles);

    /**
     * Updates the current state of the tower.
     *
     * @param slicers List of slicers in the current game state
     * @param projectiles List of projectiles in the current game state
     */
    public void update(List<Slicer> slicers, List<Projectile> projectiles) {
        time += ShadowDefend.getTimescale();

        if (time / ShadowDefend.FPS * MILLISEC_TO_SEC >= cooldown) {
            Slicer target = findTarget(slicers);
            if (target != null) {
                time = 0;
                // Face the target
                super.setAngle(Math.atan2(target.getCenter().y - position.y, target.getCenter().x - position.x) + ROTATION);
                // Shoot projectile
                shootProjectile(target, projectiles);
            }
        }

        // Update the sprite
        super.update();
    }
}
