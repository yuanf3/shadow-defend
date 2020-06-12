import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Sprite class. Represents a game entity
 */
public abstract class Sprite {

    private final Image image;
    private final Rectangle rect;
    private double angle;

    /**
     * Creates a new Sprite
     *
     * @param point    The starting Point for the entity
     * @param imageSrc The image which will be rendered at the point
     */
    public Sprite(Point point, String imageSrc) {
        this.image = new Image(imageSrc);
        this.rect = image.getBoundingBoxAt(point);
        this.angle = 0;
    }

    public Rectangle getRect() {
        return new Rectangle(rect);
    }

    /**
     * Moves the Sprite by a specified delta
     *
     * @param dx The delta vector
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public Point getCenter() {
        return getRect().centre();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Updates the Sprite. Renders the Sprite at its current position (central point).
     */
    public void update() {
        image.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(angle));
    }
}