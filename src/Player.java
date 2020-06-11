import bagel.Window;

public class Player {

    private int money;
    private int lives;

    public Player() {
        money = 500;
        lives = 25;
    }

    public int getMoney() {
        return money;
    }

    public int getLives() {
        return lives;
    }

    public void rewardMoney(int reward) {
        money += reward;
    }

    public void reduceLives(int penalty) {
        lives -= penalty;
        // Game over, exit immediately
        if (lives <= 0) {
            Window.close();
        }
    }
}
