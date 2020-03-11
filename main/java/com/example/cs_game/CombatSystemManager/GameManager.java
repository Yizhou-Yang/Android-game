package com.example.cs_game.CombatSystemManager;

import android.content.Context;
import android.util.Log;

import com.example.cs_game.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.GenerationSystem.BasicGenerator;
import com.example.cs_game.GenerationSystem.StartupGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameManager {

    // There will always be one character at most during anytime
    private static Player player;

    // choose basic generator as our current generator
    public static StartupGenerator GameGenerator = BasicGenerator.buildGenerator();
    public static Player getPlayer() {
        return player;
    }
    public static void setPlayer(Player player) {
        GameManager.player = player;
    }

    //the SL part of the game manager
    private static GameData gamedata;
    private User user;
    private Context context;
    private static final String SUFFIX = "-sav1.dat";

    public GameManager(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    void newGame(){
        gamedata = new GameData();
    }

    void loadGame(){

        String filename = user.getUsername() + SUFFIX;
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                gamedata = (GameData) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("GameState", "FileNotFoundException");
            gamedata = new GameData();

        } catch (IOException e) {
            Log.e("GameState", "IOException");
            gamedata =  new GameData();

        } catch (ClassNotFoundException e) {
            Log.e("GameState", "ClassNotFoundException");
            gamedata =  new GameData();
        }
    }

    void saveGame(){
        String filename = user.getUsername() + SUFFIX;
        try {
            ObjectOutputStream outputStream;
            outputStream = new ObjectOutputStream(
                    context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStream.writeObject(gamedata);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    static GameData getGameState(){
        return gamedata;
    }

}


