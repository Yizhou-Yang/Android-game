package com.example.cs_game.ShopGame.TradeManager;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Item;
import com.example.cs_game.DatabaseSystem.LocalSLSystem.GameManager;

public class ItemSellingManager {

    // Currently chosen item
    private Item currentItem;
    private GameManager gameManager = GameManager.getGameManager();

    public void setCurrentItem(Item item) {
        currentItem = item;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    /**
     * Sell the item and give player item's price amount of resource
     */
    public void sellItem() {
        if (currentItem != null) {
            gameManager.getPlayer().removeItem(currentItem);
            gameManager.getPlayer().gainResource(currentItem.getPrice());
        }
    }

    /**
     * Reset the manager to avoid any unintended error
     */
    public void reset() {
        currentItem = null;
    }
}
