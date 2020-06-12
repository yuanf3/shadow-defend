import bagel.util.Point;

import java.util.List;

/**
 * MegaSlicer class, a subclass of Slicer
 */
public class MegaSlicer extends Slicer {

    private static final int HEALTH = 2;
    private static final double SPEED = 1.5;
    private static final int REWARD = 10;
    private static final int PENALTY = 4;
    private static final String SPRITE = "megaslicer.png";

    /**
     * Creates a new MegaSlicer
     *
     * @param start The Point to begin at
     * @param polyline The polyline to traverse (must have at least 1 point)
     * @param targetPointIndex The immediate next Point of the polyline
     */
    public MegaSlicer(Point start, List<Point> polyline, int targetPointIndex) {
        super(start, polyline, targetPointIndex, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

    @Override
    public void spawnOnDeath(List<Slicer> slicers) {
        slicers.add(new SuperSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
        slicers.add(new SuperSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
    }

}
