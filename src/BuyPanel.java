import bagel.Font;
import bagel.Image;
import bagel.util.Point;

public class BuyPanel {

    private static final Image PANEL_IMAGE = new Image("res/images/buypanel.png");
    private static final Font KEYBINDS_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private static final Font MONEY_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);

    public void drawPanel(int money) {
        double centreX = PANEL_IMAGE.getWidth() / 2;
        double centreY = PANEL_IMAGE.getHeight() / 2;

        PANEL_IMAGE.draw(centreX, centreY);

        // Display keybinds (not changeable)
        String keybindsText =
            "Key binds:\n\n" +
            "S - Start wave\n" +
            "L - Increase Timescale\n" +
            "K - Decrease Timescale";
        double keybindsTextWidth = KEYBINDS_FONT.getWidth(keybindsText);
        KEYBINDS_FONT.drawString(keybindsText, centreX - keybindsTextWidth / 2, 20);

        // Display money
        String moneyText = "$" + money;
        MONEY_FONT.drawString(moneyText, centreX*2 - 200, 65);
    }
}
