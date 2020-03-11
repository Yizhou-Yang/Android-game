package com.example.cs_game.ShopGame;

import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CombatSystemManager.GameManager;

/**
 * This will provide simple interface to manage complex buying mechanisms due to gambling
 */
class ItemPurchaseManager {
    // true if in gambling state (the user is gambling)
    private boolean gambleState = false;

    // When an currentItem is chose (by the user), this stores the currentItem's original price
    // so that when price gets altered by gambling, the currentItem can eventually get back
    // its original price
    private int originalPrice;

    // Currently chosen currentItem (by the user)
    private Item currentItem;

    private GambleManager gambleManager = new GambleManager();

    Item getCurrentItem() {
        return currentItem;
    }

    /**
     * Set the new chosen currentItem and original price
     *
     * @param currentItem the Item this helper will manage
     */
    void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
        originalPrice = currentItem.getPrice();
    }

    /**
     * Begin gamble. Pay the gamble cost first, and set up gamble range
     */
    void beginGamble() {
        int gambleCost = (int) Math.ceil(currentItem.getPrice() * 0.1);
        GameManager.getPlayer().pay(gambleCost);

        gambleManager.setNewMaximum(currentItem.getPrice());

        gambleState = true;

    }

    /**
     * If gambleState == true, then calculate the sales ratio with input and set new price for the
     * currentItem
     *
     * @param input the gamble attempt
     */
    void assessGamble(int input) {
        if (gambleState) {
            gambleManager.calculateSalesRatio(input);
            setNewPrice();

            gambleState = false;
        }
    }

    /**
     * A gamble attempt has been executed and the corresponding sales ratio will be applied to give
     * the currentItem a new price.
     */
    private void setNewPrice() {
        int newPrice = gambleManager.getNewPrice(currentItem.getPrice());
        currentItem.setPrice(newPrice);
    }

    /**
     * Revert the price of the currentItem back to its original price; should be called after gamble is
     * finished.
     */
    void revertPrice() {
        currentItem.setPrice(originalPrice);
    }

    /**
     * Attempt to buy the Item object currently stored in currentItem;
     *
     * @return if currentItem not null and player has enough resources to buy successfully, return true;
     * otherwise return false
     */
    boolean buyItem() {
        if (currentItem == null) return false;

        return GameManager.getPlayer().buyItem(currentItem);
    }

    /**
     * Reset this helper. Make sure nothing unintended happens
     */
    void reset() {
        currentItem = null;
        originalPrice = -1;
    }

    int getGambleAnswer() {
        if (gambleState) return gambleManager.getCorrectNum();

        return -1;
    }

    /**
     * Get collected gamble scores
     *
     * @return the gamble score managed by gambleManager
     */
    double getGambleScore() {
        return gambleManager.getGambleScore();
    }

    /**
     * Get collected gamble attempts
     *
     * @return the gamble attempts managed by gambleManager
     */
    int getGambleAttempts() {
        return gambleManager.getAttempts();
    }

    /**
     * Get the cost of gamble for currently holding currentItem
     *
     * @return gamble cost of currently holding currentItem
     */
    int getGambleCost() {
        return GambleManager.getGambleCost(currentItem.getPrice());
    }
}
