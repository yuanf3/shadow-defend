import bagel.util.Point;

import java.util.List;

public class RegularSlicer extends Slicer {

    private static final int HEALTH = 1;
    private static final double SPEED = 2;
    private static final int REWARD = 2;
    private static final int PENALTY = 1;
    private static final String SPRITE = "slicer.png";

    public RegularSlicer(Point start, List<Point> polyline, int targetPointIndex) {
        super(start, polyline, targetPointIndex, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

    @Override
    public void spawnOnDeath(List<Slicer> slicers) {
        return;
    }

}
