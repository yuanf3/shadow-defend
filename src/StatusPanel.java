import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * StatusPanel class
 */
public class StatusPanel {

    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 743;
    private static final Image PANEL_IMAGE = new Image("res/images/statuspanel.png");
    private static final Font FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);

    public void drawPanel(int currentWave, String status, int lives) {
        PANEL_IMAGE.drawFromTopLeft(X_OFFSET, Y_OFFSET);

        // Display wave number
        FONT.drawString("Wave: " + currentWave, X_OFFSET+ 3, Y_OFFSET + 19);

        // Display timescale
        DrawOptions textColour;
        if (ShadowDefend.getTimescale() == 1) {
            textColour = new DrawOptions();
        } else {
            textColour = new DrawOptions().setBlendColour(Colour.GREEN);
        }
        FONT.drawString("Time Scale: " + (double) ShadowDefend.getTimescale(),
                X_OFFSET + 200,
                Y_OFFSET + 19,
                textColour);

        // Display status
        FONT.drawString("Status: " + ShadowDefend.getStatus(), X_OFFSET + 420, Y_OFFSET + 19);

        // Display lives
        FONT.drawString("Lives: " + lives, X_OFFSET + 920, Y_OFFSET + 19);
    }

    /**
     * @return Bounding box of the panel
     */
    public static Rectangle getBoundingBox() {
        Rectangle bb = PANEL_IMAGE.getBoundingBox();
        bb.moveTo(new Point(X_OFFSET, Y_OFFSET));
        return bb;
    }
}
