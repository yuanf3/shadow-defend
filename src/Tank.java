import bagel.util.Point;

import java.util.List;

/**
 * Tank class, a subclass of Tower
 */
public class Tank extends Tower {

    private static final int RADIUS = 100;
    private static final int COOLDOWN = 1000;
    private static final int COST = 250;
    private static final String SPRITE = "tank.png";
    private static final String PROJ_SPRITE = "tank_projectile.png";
    private static final int PROJ_DAMAGE = 10;

    private final Point position;

    /**
     * Creates a new Tank
     *
     * @param position The Tank's (fixed ) location Point
     */
    public Tank(Point position) {
        super(position, RADIUS, COOLDOWN, SPRITE);
        this.position = position;
    }

    @Override
    public void shootProjectile(Slicer target, List<Projectile> projectiles) {
        projectiles.add(new Projectile(position, PROJ_SPRITE, PROJ_DAMAGE, target));
    }

    public static int getCOST() {
        return COST;
    }
}
