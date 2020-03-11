package com.example.cs_game.ShopGame;

import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CombatSystemManager.GameManager;

class ItemSellingManager {

    // Currently chosen item
    private Item currentItem;

    void setCurrentItem(Item item) {
        currentItem = item;
    }

    Item getCurrentItem() {
        return currentItem;
    }

    /**
     * Sell the item and give player item's price amount of resource
     */
    void sellItem() {
        if (currentItem != null) {
            GameManager.getPlayer().removeItem(currentItem);
            GameManager.getPlayer().gainResource(currentItem.getPrice());
        }
    }

    /**
     * Reset the manager to avoid any unintended error
     */
    void reset() {
        currentItem = null;
    }
}
