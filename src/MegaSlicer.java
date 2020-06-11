import bagel.util.Point;

import java.util.List;

public class MegaSlicer extends Slicer {

    private static final int HEALTH = 2;
    private static final double SPEED = 1.5;
    private static final int REWARD = 10;
    private static final int PENALTY = 4;
    private static final String SPRITE = "megaslicer.png";

    public MegaSlicer(List<Point> polyline) {
        super(polyline, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

}
