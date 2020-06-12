import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;

/**
 * BuyPanel class
 */
public class BuyPanel {

    // Panel image and fonts
    private static final Image PANEL_IMAGE = new Image("res/images/buypanel.png");
    private static final Font KEYBINDS_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private static final Font MONEY_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);

    // Purchase item buttons
    private static final PurchaseItem B1 = new PurchaseItem(new Point(64,40), "tank.png");
    private static final PurchaseItem B2 = new PurchaseItem(new Point(184,40), "supertank.png");
    private static final PurchaseItem B3 = new PurchaseItem(new Point(304,40), "airsupport.png");

    /**
     * Draws the BuyPanel
     *
     * @param money The amount of money to display
     */
    public void drawPanel(int money) {
        PANEL_IMAGE.drawFromTopLeft(0, 0);

        // Display key binds (not changeable)
        String keyBindsText =
            "Key binds:\n\n" +
            "S - Start wave\n" +
            "L - Increase Timescale\n" +
            "K - Decrease Timescale";
        double panelWidth = PANEL_IMAGE.getWidth();
        double keyBindsTextWidth = KEYBINDS_FONT.getWidth(keyBindsText);
        KEYBINDS_FONT.drawString(keyBindsText, (panelWidth - keyBindsTextWidth) / 2, 20);

        // Display available money
        String moneyText = "$" + money;
        MONEY_FONT.drawString(moneyText, 824, 65);
    }

    /**
     * @return Bounding box of the panel
     */
    public static Rectangle getBoundingBox() {
        return PANEL_IMAGE.getBoundingBox();
    }

    public void update(TiledMap map, Player player, List<Tower> towers, List<Airplane> airplanes, Input input) {
        B1.update(map, player, towers, airplanes, input);
        B2.update(map, player, towers, airplanes, input);
        B3.update(map, player, towers, airplanes, input);
    }
}
