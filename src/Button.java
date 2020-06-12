import bagel.Input;
import bagel.MouseButtons;
import bagel.util.Point;

public class Button extends Sprite {

    public Button(Point point, String sprite) {
        super(point, sprite);
    }

    @Override
    public void update(Input input) {
        if (input.wasPressed(MouseButtons.LEFT)) {
            if (getRect().intersects(input.getMousePosition())) {
                onClick();
            }
        }
        super.update(input);
    }

    private void onClick() {
        System.out.println("click");
    }
}
