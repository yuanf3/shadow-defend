import bagel.Window;

/**
 * Player class
 */
public class Player {

    private int money;
    private int lives;

    public Player() {
        money = 500;
        lives = 25;
    }

    /**
     * Rewards money to the player
     *
     * @param reward The amount of money to be rewarded
     */
    public void rewardMoney(int reward) {
        money += reward;
    }

    /**
     * Deducts money from the player
     *
     * @param price The amount of money to be deducted
     */
    public void reduceMoney(int price) { money -= price; }

    /**
     * Deducts lives from the player, performing check for game over
     *
     * @param penalty The amount of lives to be deducted
     */
    public void reduceLives(int penalty) {
        lives -= penalty;
        // Game over, exit game immediately
        if (lives <= 0) {
            Window.close();
        }
    }

    public int getMoney() {
        return money;
    }

    public int getLives() {
        return lives;
    }
}
