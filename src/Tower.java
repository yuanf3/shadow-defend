import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * Slicer class
 */
public abstract class Tower extends Sprite {

    private final int radius;
    private final int cooldown;
    private final int cost;

    private static final String SPRITE_PATH = "res/images/";

    public Tower(int radius, int cooldown, int cost, Point point, String sprite) {
        super(point, SPRITE_PATH + sprite);
        this.radius = radius;
        this.cooldown = cooldown;
        this.cost = cost;
    }


    @Override
    public void update(Input input) {
        Point location = getCenter();

        // Update the sprite
        super.update(input);
    }

}
