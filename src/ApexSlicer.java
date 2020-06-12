import bagel.util.Point;

import java.util.List;

/**
 * ApexSlicer class, a subclass of Slicer
 */
public class ApexSlicer extends Slicer {

    private static final int HEALTH = 25;
    private static final double SPEED = 0.75;
    private static final int REWARD = 150;
    private static final int PENALTY = 16;
    private static final String SPRITE = "apexslicer.png";

    /**
     * Creates a new ApexSlicer
     *
     * @param start The Point to begin at
     * @param polyline The polyline to traverse (must have at least 1 point)
     * @param targetPointIndex The immediate next Point of the polyline
     */
    public ApexSlicer(Point start, List<Point> polyline, int targetPointIndex) {
        super(start, polyline, targetPointIndex, HEALTH, SPEED, REWARD, PENALTY, SPRITE);
    }

    @Override
    public void spawnOnDeath(List<Slicer> slicers) {
        slicers.add(new MegaSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
        slicers.add(new MegaSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
        slicers.add(new MegaSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
        slicers.add(new MegaSlicer(this.getCenter(), this.getPolyline(), this.getTargetPointIndex()));
    }

}
