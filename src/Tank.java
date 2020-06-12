import bagel.util.Point;

public class Tank extends Tower {

    private static final int RADIUS = 100;
    private static final int COOLDOWN = 1000;
    private static final int COST = 250;
    private static final String SPRITE = "tank.png";

    public Tank(Point point) {
        super(RADIUS, COOLDOWN, COST, point, SPRITE);
    }

}
