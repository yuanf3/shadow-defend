import bagel.util.Point;

import java.util.List;

public class SuperTank extends Tower {

    private static final int RADIUS = 150;
    private static final int COOLDOWN = 500;
    private static final int COST = 600;
    private static final String SPRITE = "supertank.png";
    private static final String PROJ_SPRITE = "supertank_projectile.png";
    private static final int PROJ_DAMAGE = 30;

    private final Point position;

    public SuperTank(Point position) {
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
