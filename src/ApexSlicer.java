import bagel.util.Point;

import java.util.List;

public class ApexSlicer extends Slicer {

    private static final int HEALTH = 25;
    private static final double SPEED = 0.75;
    private static final int REWARD = 150;
    private static final int PENALTY = 16;
    private static final String SPRITE = "apexslicer.png";

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
