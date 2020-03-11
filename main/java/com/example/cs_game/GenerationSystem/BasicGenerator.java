package com.example.cs_game.GenerationSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPartManager;
import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CharacterSystem.CharacterStuffBuilder;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitBody;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitBodyFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitDeath;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitDeathFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitHolder;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitItem;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitItemFunctionInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is a direct implementation of GenericGenerator, and uses implements the strategy
 * interface from StartupGenerator
 */
public class BasicGenerator extends GenericGenerator implements StartupGenerator {

    private List<TraitDeathFunctionInterface> deathTraitFuncs;

    private List<TraitBodyFunctionInterface> bodyTraitFuncs;

    private List<TraitItemFunctionInterface> itemTraitFuncs;

    public static StartupGenerator buildGenerator() {


        return new BasicGenerator();
    }

    /**
     * the constructor needs a set of lists for specifying the actual set of items, and their
     * statistics.
     */
    BasicGenerator() {

        // first item in the list of lists is always the 'Price'
        // second item is damage/healing power/protection
        // third item for weapons is accuracy
        weaponClasses = new String[]{"Axe", "Sword", "Spear", "Pistol", "Rifle"};
        weaponStats = new int[][]{{10, 25, 0}, {4, 20, 10}, {3, 12, 30},
                {20, 20, 25}, {35, 30, 20}};

        healthPotionClasses = new String[]{"Weak Health Potion", "Strong Health Potion"};
        healthPotionStats = new int[][]{{30, 8}, {75, 20}};

        armorClasses = new String[]{"Light", "Medium", "Heavy", "Super Heavy"};
        armorStats = new int[][]{{5, 10}, {15, 30}, {25, 60}, {50, 90}};

        deathTraitFuncs = generateDeathTraits();

        bodyTraitFuncs = generateBodyTraits();

        itemTraitFuncs = generateItemTraits();
    }

    /**
     * TRAITS
     * The following three functions are for the purpose of creating the actual functions
     * for traits in the game
     */
    public List<TraitDeathFunctionInterface> generateDeathTraits() {
        List<TraitDeathFunctionInterface> traits = new ArrayList<>();
        TraitDeathFunctionInterface deathTrait =
                new TraitDeathFunctionInterface() {
                    @Override
                    public boolean apply(BodyPartManager body, int power) {
                        double curpower = setPower(power);


                        int x = body.getCurrHealth();
                        int y = body.getTotalHealth();

                        return (x / (double) y) <= 1.0 - curpower;

                    }

                    @Override
                    public double setPower(int num) {
                        return 0.01 * num;
                    }


                };

        traits.add(deathTrait);
        return traits;
    }

    /**
     * BODY TRAITS
     * @return
     */
    @Override
    public List<TraitBodyFunctionInterface> generateBodyTraits() {
        List<TraitBodyFunctionInterface> traits = new ArrayList<>();


        TraitBodyFunctionInterface healthTrait =
                new TraitBodyFunctionInterface() {
                    @Override
                    public void apply(BodyPart bodyPart, int power) {

                        int curh = bodyPart.getTotalHealth();
                        double newpower = setPower(power);

                        System.out.println("IMPORTANT STUFFFF   " + curh);
                        System.out.println("YES " + newpower);
                        int newh = (int) (curh * newpower);

                        bodyPart.setHealth(newh);

                        System.out.println("HAPPEN " + newh);
                        bodyPart.setTotalHealth(newh);

                    }

                    @Override
                    public double setPower(int num) {

                        return (1 + (0.01 * (double) num));
                    }
                };
        traits.add(healthTrait);
        return traits;
    }

    /**
     * TRAIT CONSTRUCTOR
     */

    @Override
    public List<TraitItemFunctionInterface> generateItemTraits() {
        List<TraitItemFunctionInterface> traits = new ArrayList<>();
        TraitItemFunctionInterface ItemTrait =
                new TraitItemFunctionInterface() {
                    @Override
                    public void apply(Item item, int power) {
                        double curpower = setPower(power);

                        Weapon weapon = (Weapon) item;
                        int curdamage = weapon.getDamage();

                        weapon.setDamage((int) (curdamage * curpower));

                    }

                    @Override
                    public boolean isType(Item item) {
                        return (item instanceof Weapon);
                    }

                    @Override
                    public double setPower(int num) {
                        return 1 + (0.01 * num);
                    }

                };
        traits.add(ItemTrait);
        return traits;
    }

    /**
     * This function is for creating a player with a random weapon from everything we've defined
     * above
     */
    @Override
    public Player generatePlayer() {
        String[] bodyPartsNames = {"Head", "Body", "Left Arm",
                "Right Arm", "Left Leg", "Right Leg"};
        int[] bodyHealths = {10, 60, 20, 20, 25, 25};
        double[] bodyDodges = {0.9, 0.2, 0.5, 0.5, 0.4, 0.4};

        //trait powers/functions


        int[] deathTraitPower = {100};
        String[] deathTraitNames = {"Toughness"};


        int[] bodyTraitPower = {12};
        String[] bodyTraitNames = {"Vitality"};


        int[] itemTraitPower = {10};
        String[] itemTraitNames = {"Strength"};


        List<BodyPart> playerBodyParts = CharacterStuffBuilder.bodyPartCreator(
                bodyPartsNames, bodyHealths, bodyDodges);
        List<TraitDeath> playerDeath = CharacterStuffBuilder.traitDeathCreator(
                deathTraitPower, deathTraitFuncs, deathTraitNames);
        List<TraitBody> playerBody = CharacterStuffBuilder.traitBodyCreator(
                bodyTraitPower, bodyTraitFuncs, bodyTraitNames);
        List<TraitItem> playerItem = CharacterStuffBuilder.traitItemCreator(
                itemTraitPower, itemTraitFuncs, itemTraitNames);
        TraitHolder traitHolder = CharacterStuffBuilder.traitCreator(
                playerBody, playerDeath, playerItem);

        Player player = CharacterStuffBuilder.playerCreator(playerBodyParts, traitHolder);

        Random random = new Random();

        Weapon weapon = generateWeaponClass(weaponClasses[random.nextInt(5)]);

        player.lootItem(weapon);

        player.equipWeapon(0, weapon);

        return player;
    }

    /**
     * This functions is for generating a monster from the above defined traits/items
     */
    @Override
    public Monster generateMonster() {
        String name = "w75h77";

        Random random = new Random();


        String[] playerInitNames = {"Head", "Body", "Left Arm",
                "Right Arm", "Left Leg", "Right Leg"};
        int[] bodyHealths = {varia(5, 0), varia(40, 0), varia(15, 0),
                varia(15, 0), varia(20, 0), varia(20, 0)};

        double[] bodyDodges = {0.9 * random.nextDouble(), 0.2 *
                random.nextDouble(), 0.5 * random.nextDouble(),
                0.5 * random.nextDouble(), 0.4 * random.nextDouble(), 0.4 * random.nextDouble()};

        //trait powers/functions


        int[] deathTraitPower = {100};
        String[] deathTraitNames = {"Toughness"};


        int[] bodyTraitPower = {varia(10, 0.1)};
        String[] bodyTraitNames = {"Vitality"};


        int[] itemTraitPower = {varia(5, 0.7)};
        String[] itemTraitNames = {"Strength"};


        List<BodyPart> monsterBodyParts = CharacterStuffBuilder.bodyPartCreator(
                playerInitNames, bodyHealths, bodyDodges);
        List<TraitDeath> monsterDeath = CharacterStuffBuilder.traitDeathCreator(
                deathTraitPower, deathTraitFuncs, deathTraitNames);
        List<TraitBody> monsterBody = CharacterStuffBuilder.traitBodyCreator(
                bodyTraitPower, bodyTraitFuncs, bodyTraitNames);
        List<TraitItem> monsterItem = CharacterStuffBuilder.traitItemCreator(
                itemTraitPower, itemTraitFuncs, itemTraitNames);
        TraitHolder traitHolder = CharacterStuffBuilder.traitCreator(
                monsterBody, monsterDeath, monsterItem);


        Weapon weapon1 = generateWeaponClass(weaponClasses[random.nextInt(5)]);
        Weapon weapon2 = generateWeaponClass(weaponClasses[random.nextInt(5)]);
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon2);

        return CharacterStuffBuilder.monsterCreator(monsterBodyParts, traitHolder, name, weapons);


    }


}




