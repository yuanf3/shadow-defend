import bagel.util.Point;

import java.util.List;

/**
 * SuperSlicer class, a subclass of Slicer
 */
public class SuperSlicer extends Slicer {

    private static final int HEALTH = 1;
    private static final double SPEED = 1.5;
    private static final int REWARD = 15;
    private static final int PENALTY = 2;
    private static final String SPRITE = "superslicer.png";

    /**
     * Creates a new SuperSlicer
     *
     * @param start The Point to begin at
     * @param polyline The polyline to traverse (must have at least 1 point)
     * @param targetPointIndex The immediate next Point of the polyline
     */
    public SuperSlicer(Point start, List<Point> polyline, int targetPointIndex) {
        super(start, polyline, targetPointIndex, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

    @Override
    public void spawnOnDeath(List<Slicer> slicers) {
        slicers.add(new RegularSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
        slicers.add(new RegularSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
    }

}
