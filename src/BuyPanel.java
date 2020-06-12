import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class BuyPanel {

    private static final Image PANEL_IMAGE = new Image("res/images/buypanel.png");
    private static final Font KEYBINDS_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private static final Font MONEY_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);

    private static final Button button1 = new Button(new Point(64,40), "res/images/tank.png");
    private static final Button button2 = new Button(new Point(184,40), "res/images/supertank.png");
    private static final Button button3 = new Button(new Point(304,40), "res/images/airsupport.png");

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

        // Display money
        String moneyText = "$" + money;
        MONEY_FONT.drawString(moneyText, 824, 65);
    }

    public void update(Input input) {
        button1.update(input);
        button2.update(input);
        button3.update(input);
    }
}
