package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Weapon;

import java.util.List;

interface CombatView {

    void toNextLevel(String message, int timeSpent, int exp, boolean won);

    void toMainMenu();

    void showConsumableList(List<Consumable> consumables);

    void showWeaponList(List<Weapon> weapons);

    void showBodyPartsList(List<BodyPart> bodyParts);

    void updateCombatLog(String combatLog);

    void updateHealths(int playerHealth, int monsterHealth);

    void setButtons();

    void setHealthBars(int maxPlayerHealth, int currPlayerHealth,
                       int maxMonsterHealth, int currMonsterHealth);

    void setMusic(int choice);

}
