import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.List;

/**
 * PurchaseItem class
 */
public class PurchaseItem extends Sprite {

    private static final String SPRITE_PATH = "res/images/";
    private static final Font FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);

    private final Image indicator;
    private final String type;
    private final int price;
    private boolean selected;
    private boolean placeable;

    public PurchaseItem(Point point, String sprite) {
        super(point, SPRITE_PATH + sprite);
        this.indicator = new Image(SPRITE_PATH + sprite);
        this.type = sprite;
        switch (type) {
            case "tank.png":
                price = Tank.getCOST();
                break;
            case "supertank.png":
                price = SuperTank.getCOST();
                break;
            default:
                price = 0;
                break;
        }
        selected = false;
        placeable = false;
    }

    /**
     * Checks if the mouse is outside the game window
     *
     * @param mouseLoc The current position of the mouse
     * @return true if mouse is outside window, otherwise false
     */
    private boolean isOutsideWindow(Point mouseLoc) {
        double x = mouseLoc.x;
        double y = mouseLoc.y;
        return (x < 0 || y < 0 || x > ShadowDefend.WIDTH || y > ShadowDefend.HEIGHT);
    }

    /**
     * Checks if a tower can be placed
     *
     * @param mouseLoc The current position of the mouse
     * @param map The current map, containing blocked and unblocked tiles
     * @param towers List of towers in the current game state
     * @return true if tower can be placed, otherwise false
     */
    private boolean isPlaceable(Point mouseLoc, TiledMap map, List<Tower> towers) {
        if (map.getPropertyBoolean((int) mouseLoc.x, (int) mouseLoc.y, "blocked", false)) {
            return false;
        }
        if (BuyPanel.getBoundingBox().intersects(mouseLoc)) {
            return false;
        }
        if (StatusPanel.getBoundingBox().intersects(mouseLoc)) {
            return false;
        }
        for (Tower t : towers) {
            if (t.getRect().intersects(mouseLoc)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws button for the PurchaseItem, handles mouse interactions
     *
     * @param map The current map
     * @param player The current player
     * @param towers List of towers in the current game state
     */
    public void update(TiledMap map, Player player, List<Tower> towers, Input input) {
        super.update();

        // Display price of PurchaseItem
        DrawOptions textColour;
        if (player.getMoney() >= price) {
            textColour = new DrawOptions().setBlendColour(Colour.GREEN);
        } else {
            textColour = new DrawOptions().setBlendColour(Colour.RED);
        }
        FONT.drawString("$" + price,
                getCenter().x - 30,
                getCenter().y + 50,
                textColour);

        Point mouseLoc = input.getMousePosition();
        if (isOutsideWindow(mouseLoc)) {
            return;
        }

        // Left click to select PurchaseItem, or to place tower
        if (input.wasPressed(MouseButtons.LEFT)) {
            if (!selected) {
                if (getRect().intersects(mouseLoc)) {
                    if (player.getMoney() >= price) {
                        selected = true;
                        ShadowDefend.setStatus("Placing");
                    }
                }
            }
            else if (placeable) {
                switch (type) {
                    case "tank.png":
                        towers.add(new Tank(mouseLoc));
                        break;
                    case "supertank.png":
                        towers.add(new SuperTank(mouseLoc));
                        break;
                }
                player.reduceMoney(price);
                selected = false;
                placeable = false;
            }
        }

        // Right click to deselect
        if (input.wasPressed(MouseButtons.RIGHT)) {
            selected = false;
        }

        // Draw indicator
        if (selected) {
            if (isPlaceable(mouseLoc, map, towers)) {
                indicator.draw(mouseLoc.x, mouseLoc.y);
                placeable = true;
            }
            else {
                placeable = false;
            }
        }
    }
}
