package com.example.cs_game.CharacterSystem.CharacterStuff;

import androidx.annotation.NonNull;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPartManager;
import com.example.cs_game.CharacterSystem.Death.DeathManager;
import com.example.cs_game.CharacterSystem.Item.Armor;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CharacterSystem.Item.ItemManager;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CharacterSystem.LevelStuff.Level;
import com.example.cs_game.CharacterSystem.LevelStuff.LevelManager;
import com.example.cs_game.CharacterSystem.Observer.CharacterWatcher;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitHolder;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitManager;

import java.util.List;

public class GameCharacter {

    private String charName;

    private CharacterWatcher characterWatcher;

    private BodyPartManager bodyPartManager;

    private ItemManager itemManager;

    private TraitManager traitManager;
    private DeathManager deathConditions;

    private LevelManager levelManager;

    GameCharacter(List<BodyPart> bodyParts, TraitHolder traitHolder, String charName, Level level) {

        characterWatcher = new CharacterWatcher(this);

        this.charName = charName;
        bodyPartManager = new BodyPartManager(bodyParts, characterWatcher);
        deathConditions = new DeathManager(characterWatcher);
        traitManager = new TraitManager(traitHolder, characterWatcher);
        itemManager = new ItemManager(characterWatcher);
        levelManager = new LevelManager(characterWatcher, level);


        characterWatcher.updateCharacter();

        characterWatcher.updateBody();
        characterWatcher.updateItems();
        characterWatcher.updateDead();


    }

    public boolean isDead() {
        return deathConditions.checkIfDead();
    }

    public void lootItem(Item item) {
        itemManager.addItem(item);
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public BodyPartManager getBodyPartManager() {
        return bodyPartManager;
    }

    public List<BodyPart> getBodyParts() {
        return bodyPartManager.getBodyPartsList();
    }

    public List<BodyPart> getAliveBodyParts() {
        return bodyPartManager.getAliveBodyParts();
    }

    public List<Weapon> getUnequippedWeapons() {
        return itemManager.getWeapons();
    }

    public Weapon[] getEquippedWeapons() {
        return itemManager.getEquippedWeapons();
    }

    public List<Armor> getUnequippedArmors() {
        return itemManager.getArmors();
    }

    public List<Armor> getEquippedArmors() {
        return bodyPartManager.getArmors();
    }

    public String[] getBodyPartsNames() {
        return bodyPartManager.getBodyPartsNames();
    }

    public List<Consumable> getConsumables() {
        return itemManager.getConsumables();
    }

    public TraitManager getTraitManager() {
        return traitManager;
    }

    public int getHealth() {
        return bodyPartManager.getCurrHealth();
    }

    public int getMaxHealth() {
        return bodyPartManager.getTotalHealth();
    }

    public void equipWeapon(int slot, Weapon weapon) {
        itemManager.equipWeapon(slot, weapon);
    }

    public void equipArmor(int location, Armor armor) {
        Armor replacedArmor = bodyPartManager.equipArmor(location, armor);
        // BodyPartManager cannot, and should not, access ItemManager;
        // Hence, remove armor with another call, unlike equipWeapon, which is done in itemManager
        itemManager.removeItem(armor);
        if (replacedArmor != null) itemManager.addItem(replacedArmor);
    }

    public void unequipWeapon(Weapon weapon) {
        itemManager.unEquipWeapon(weapon);
    }

    public void unequipArmor(Armor armor) {
        bodyPartManager.unequipArmor(armor);
        // BodyPartManager cannot, and should not, access itemManager;
        // ItemManager.addItem must be called
        itemManager.addItem(armor);
    }

    public int getLevel() {
        return levelManager.getLevel();
    }

    public int getTotalExp() {
        return levelManager.getTotalExp();
    }

    public void gainExp(int exp) {
        levelManager.gainExp(exp);
    }

    @NonNull
    @Override
    public String toString() {
        return charName;
    }


}
