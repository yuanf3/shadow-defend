import bagel.util.Point;

import java.util.List;

/**
 * Explosive class
 */
public class Explosive extends Sprite {

    private static final String SPRITE = "res/images/explosive.png";
    private static final int DELAY = 2;
    private static final int RADIUS = 200;
    private static final int DAMAGE = 500;

    private double time;
    private boolean exploded;

    /**
     * Creates a new Explosive (drop)
     *
     * @param position The Point at which the Explosive was dropped
     */
    public Explosive(Point position) {
        super(position, SPRITE);
        time = 0;
        exploded = false;
    }

    /**
     * Explode, dealing damage to slicers within the blast radius
     *
     * @param slicers List of Slicers in the current game state
     */
    private void explode(List<Slicer> slicers) {
        for (Slicer slicer : slicers) {
            if (slicer.getCenter().distanceTo(this.getCenter()) <= RADIUS) {
                slicer.takeDamage(DAMAGE);
            }
        }
        exploded = true;
    }

    /**
     * Updates the current state of the Explosive
     *
     * @param slicers List of slicers in the current game state
     */
    public void update(List<Slicer> slicers) {
        time += ShadowDefend.getTimescale();
        if (time / ShadowDefend.FPS >= DELAY) {
            explode(slicers);
            return;
        }

        // Update the sprite
        super.update();
    }

    public boolean isExploded() {
        return exploded;
    }
}
