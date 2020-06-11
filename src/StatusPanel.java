import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

public class StatusPanel {

    private static final int VERT_OFFSET = 743;
    private static final Image PANEL_IMAGE = new Image("res/images/statuspanel.png");
    private static final Font FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);

    public void drawPanel(int currentWave, String status, int lives) {
        double centreX = PANEL_IMAGE.getWidth() / 2;
        double centreY = VERT_OFFSET + PANEL_IMAGE.getHeight() / 2;

        PANEL_IMAGE.draw(centreX, centreY);

        // Display wave number
        FONT.drawString("Wave: " + currentWave, 3, VERT_OFFSET + 19);

        // Display timescale
        if (ShadowDefend.getTimescale() == 1) {
            FONT.drawString("Time Scale: " + (double) ShadowDefend.getTimescale(),
                    200,
                    VERT_OFFSET + 19);
        } else {
            FONT.drawString("Time Scale: " + (double) ShadowDefend.getTimescale(),
                    200,
                    VERT_OFFSET + 19,
                    new DrawOptions().setBlendColour(Colour.GREEN));
        }

        // Display status
        FONT.drawString("Status: " + status, 420, VERT_OFFSET + 19);

        // Display lives
        FONT.drawString("Lives: " + lives, 920, VERT_OFFSET + 19);
    }
}
