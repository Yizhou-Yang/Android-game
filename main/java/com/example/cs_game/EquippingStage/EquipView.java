package com.example.cs_game.ShopGame.EquippingStage;

import com.example.cs_game.CharacterSystem.Item.Equipment;

import java.util.List;

interface EquipView {

    int WEAPON_TAB = 0;
    int ARMOR_TAB = 1;

    void showEquippedItems(List<? extends Equipment> equipments);
    void showUnequippedItems(List<? extends Equipment> equipments);

    void setTabs();
    void setOnTabClickedListener();

    void setNextLevelButtonOnClick();

    void setOnUnequippedItemClickListener();

    void showDialogOnItemClick(CharSequence[] options, Equipment equipment);

    void setOnEquippedItemClickListener();

    void updateDisplay(int tabPosition);

}
