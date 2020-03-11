package com.example.cs_game.ShopGame;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Item;

import java.util.List;

/**
 * View component of MVP clean architecture. Specifically for the Shop stage.
 */
interface ShopView extends ItemTradeDialogsInterface {

    int WEAPON_TAB = 0;
    int ARMOR_TAB = 1;
    int CONSUMABLE_TAB = 2;

    int MAIN_MENU_OPTION = 0;
    int SAVE_OPTION = 1;

    void updatePlayerResourcesDisplay(int resources);

    void setShopItemOnClickListener();

    void setInventoryItemOnClickListener();

    void setShopItemList(List<? extends Item> items);

    void setInventoryItemList(List<? extends Item> items);

    void setShopTabs();

    void setInventoryTabs();

    void setShopTabsOnClickListener();

    void setInventoryTabsOnClickListener();

    void setNextLevelButton();

    void showBoughtItem(Item item);

    void onShopTabChange(List<? extends Item> items);

    void onInventoryTabChange(List<? extends Item> items);

    void onItemSold(Item item);

    void showPopUp(String message);

    void toNextLevel(int timeSpent, double gambleScore, int exp);

    void setPauseButton();

    void showPauseDialog(CharSequence[] options);

    void toMainMenu();

    void setMusic(int choice);
}
