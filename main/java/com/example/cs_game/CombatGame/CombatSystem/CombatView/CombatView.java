package com.example.cs_game.CombatGame.CombatSystem.CombatView;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Consumable;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;

import java.util.List;

public interface CombatView {

    void toNextLevelDialog(String title, String message, int timeSpent,
                           int exp, boolean won, int combatScore);

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
