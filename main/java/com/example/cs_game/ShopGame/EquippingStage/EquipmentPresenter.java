package com.example.cs_game.ShopGame.EquippingStage;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Armor;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Equipment;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;
import com.example.cs_game.DatabaseSystem.LocalSLSystem.GameManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class EquipmentPresenter {

    // The View part of MVP for Equipment stage
    private EquipView equipView;

    EquipmentPresenter(EquipView equipView) {
        this.equipView = equipView;
    }

    void onCreate() {
        equipView.setOnTabClickedListener();
        equipView.setTabs();

        List<? extends Equipment> equippedItems = getEquippedItems(EquipView.WEAPON_TAB);
        List<? extends Equipment> unequippedItems = getUnequippedItems(EquipView.WEAPON_TAB);

        equipView.showUnequippedItems(unequippedItems);
        equipView.showEquippedItems(equippedItems);

        equipView.setOnUnequippedItemClickListener();
        equipView.setNextLevelButtonOnClick();

        equipView.setOnEquippedItemClickListener();

    }

    /**
     * Unequip given equipment from player and update the screen display
     *
     * @param equipment the equipment to unequip from player
     */
    void onEquippedItemClick(Equipment equipment) {

        if (equipment instanceof Weapon) {
            GameManager.getPlayer().unequipWeapon((Weapon) equipment);
            equipView.updateDisplay(EquipView.WEAPON_TAB);
        } else if (equipment instanceof Armor) {
            GameManager.getPlayer().unequipArmor((Armor) equipment);
            equipView.updateDisplay(EquipView.ARMOR_TAB);
        }

    }

    /**
     * Show dialog of where to onEquipLocationClicked the given equipment
     *
     * @param equipment the equipment to onEquipLocationClicked
     */
    void onUnequippedItemClicked(Equipment equipment) {
        CharSequence[] options;
        if (equipment instanceof Weapon) {
            options = new CharSequence[]{"Weapon 1", "Weapon 2"};
        } else if (equipment instanceof Armor){
            options = new CharSequence[]{"Head", "Body", "Left Arm",
                    "Right Arm", "Left Leg", "Right Leg"};
        } else {
            options = new CharSequence[] {};
        }
        equipView.showDialogOnItemClick(options, equipment);
    }

    /**
     * Update display of the list of items, where the list is indicated by tabPosition
     *
     * @param tabPosition indicator of which type of list of items to update display
     */
    void updateDisplay(int tabPosition) {
        List<? extends Equipment> equippedItems = getEquippedItems(tabPosition);
        List<? extends Equipment> unequippedItems = getUnequippedItems(tabPosition);

        equipView.showEquippedItems(equippedItems);
        equipView.showUnequippedItems(unequippedItems);
    }

    /**
     * Equip the given item on appropriate slot indicated by location
     *
     * @param location  indicator of which body part to equip the equipment on
     * @param equipment the equipment to equip
     */
    void onEquipLocationClicked(int location, Equipment equipment) {
        if (equipment instanceof Weapon) {
            GameManager.getPlayer().equipWeapon(location, (Weapon) equipment);
            equipView.updateDisplay(EquipView.WEAPON_TAB);
        } else if (equipment instanceof Armor) {
            GameManager.getPlayer().equipArmor(location, (Armor) equipment);
            equipView.updateDisplay(EquipView.ARMOR_TAB);
        }
    }

    /**
     * Return appropriate list of equipped items depending on tabPosition
     *
     * @param tabPosition indicator of which list to return
     * @return list of type of equipment (Weapon or Armor), which are currently equipped by player
     */
    private List<? extends Equipment> getEquippedItems(int tabPosition) {
        if (tabPosition == EquipView.WEAPON_TAB) return new ArrayList<>(Arrays.asList(
                GameManager.getPlayer().getEquippedWeapons()));
        else return GameManager.getPlayer().getEquippedArmors();
    }

    /**
     * Return appropriate list of unequipped items depending on tabPosition
     *
     * @param tabPosition indicator of which list to return
     * @return list of type of equipment (Weapon or Armor), which are currently unequipped by player
     */
    private List<? extends Equipment> getUnequippedItems(int tabPosition) {
        if (tabPosition == EquipView.WEAPON_TAB) {
            return GameManager.getPlayer().getUnequippedWeapons();
        } else {
            return GameManager.getPlayer().getUnequippedArmors();
        }
    }
}
