import bagel.util.Point;

import java.util.List;

public class SuperSlicer extends Slicer {

    private static final int HEALTH = 1;
    private static final double SPEED = 1.5;
    private static final int REWARD = 15;
    private static final int PENALTY = 2;
    private static final String SPRITE = "superslicer.png";

    public SuperSlicer(List<Point> polyline) {
        super(polyline, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

}
