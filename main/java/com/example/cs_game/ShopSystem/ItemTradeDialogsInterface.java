package com.example.cs_game.ShopGame;

import com.example.cs_game.CharacterSystem.Item.Item;

/**
 * This interface is for a specific shop which necessarily implements gamble in addition to regular
 * shop features
 */
public interface ItemTradeDialogsInterface {
    /**
     *
     */
    void showShopItemOptionsDialog(String itemDescription, int price, int gambleCost);

    void showGambleDialog(int maxGambleNum, int answer);

    void showSellItemDialog(String itemDescription, int price);
}
