package com.example.cs_game.Docs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.R;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }

    public StringBuffer overall(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Overall Structure: \nUsers can track their resources, " +
                "seconds spent playing and experience over 3 games. After playing all the games once the User can " +
                "choose to continue playing with the same save data and keep accumilating resources " +
                "with 'Resume Game' or start a fresh with 'New Game");
        return buffer;
    }
    public StringBuffer dungeonInformation(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("General Description:\nThe Dungeon game is a 2D game where the objective is to reach the exit " +
                "safely while collecting as many resources as possible.\n\n");
        buffer.append("Controls:\nTo change the direction of movement simply tap in that direction " +
                "relative to your current position.\nTo collect resources or exit simply go over the " +
                "icons that represent them\nBEWARE, Monsters will kill you immediately");
        return buffer;
    }
    public StringBuffer shopInformation(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("General Description:\nThe Shop Game is one where you can trade the resources " +
                "you have collected for weapons, armour and potions. You can gamble to make the most" +
                " of your trades.\n\n");
        buffer.append("Instructions:\nTo buy items simply click on them. To gamble you must try to " +
                "guess a number within a given range and your discount value depends on how close " +
                "you are to the true value");
        return buffer;
    }
    public StringBuffer combatInformation(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("General Description:\nThe Combat Game is a RPG style game where you face off " +
                "against an evil monster in a battle to the death.\n\n");
        buffer.append("Instructions:\nEvery turn you can choose to attack or use a potion." +
                "\nIf attacking you must decide which weapon to choose and which body part of the " +
                "monster to attack. How much damage you deal, and whether you hit at all depends on " +
                "your choice\nThe Monster will attack you every turn.");
        return buffer;
    }


    public void display(View view){
        StringBuffer buffer;
        String title;
        if (view.getId() == R.id.overallInfoButton){
            buffer = overall();
            title = "Overall Info";

        } else if (view.getId() == R.id.dungeonInfoButton){
            buffer = dungeonInformation();
            title = "Dungeon Info";

        } else if (view.getId() == R.id.shopInfoButton){
            buffer = shopInformation();
            title = "Shop Info";
        } else {
            buffer = combatInformation();
            title = "Combat Info";
        }

        FrontEndUtility.dialog(title, buffer.toString(), this);
    }



}
