package com.example.cs_game.DungeonGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.Utilities.MusicPlayer;
import com.example.cs_game.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DungeonGameActivity extends AppCompatActivity {

    private DungeonGameView gameView;
    private User user;
    private UserDatabaseHelper userDatabaseHelper;
    private List<DungeonEnemy> enemies;
    private int dungeonSize;
    private int newEnemyInTurns;
    private int turnCount;
    private int characterType;
    private DungeonHero hero;
    private List<DungeonItem> resources;
    private DungeonItem exit;
    private int startSeconds;
    private int elapsedSeconds;
    private List<DungeonItem> verticalWalls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        this.userDatabaseHelper = new UserDatabaseHelper(this);
        this.user = userDatabaseHelper.getUser(username);
        this.dungeonSize = this.user.getDifficultyCustomization() + 1;
        this.newEnemyInTurns = (3 - this.user.getDifficultyCustomization()) * 75;
        this.characterType = this.user.getSpriteCustomization();


        user.setResources(0);
        userDatabaseHelper.updateUser(user);

        gameView = new DungeonGameView(this);
        gameView.setActivity(this);

        setContentView(gameView);

        enemies = new ArrayList<>();
        setEnemies();

        setHero();
        setMusic();

        resources = new ArrayList<>();
        setResources();

        verticalWalls = new ArrayList<>();
        setVerticalWalls();

        exit = DungeonItemFactory.getDungeonItem("exit", BitmapFactory.decodeResource(getResources(), R.drawable.door), 1000, 150);
        startSeconds = (int) System.currentTimeMillis() / 1000;
        elapsedSeconds = 0;
    }

    private void setVerticalWalls() {
        for (int i = 0; i <= dungeonSize; i++) {
            for (int j = 0; j <= dungeonSize; j++) {
                {
                    int wallx = (1000 / (dungeonSize)) * i;
                    int wally = (1500 / (dungeonSize)) * j;
                    verticalWalls.add(
                            DungeonItemFactory.getDungeonItem("wall",
                                    BitmapFactory.decodeResource(getResources(), R.drawable.verticalwall),
                                    wallx, wally));
                    verticalWalls.add(DungeonItemFactory.getDungeonItem("wall", BitmapFactory.decodeResource(getResources(), R.drawable.verticalwall), wallx + (1000 / ((dungeonSize) * 2)), wally + (1500 / ((dungeonSize) * 2))));
                }
            }
        }
    }

    private void setEnemies() {

        for (int i = 0; i <= this.dungeonSize; i++) {
            this.enemies.add(
                    (DungeonEnemy)DungeonCharacterFactory.getDungeonCharacter("enemy",
                            BitmapFactory.decodeResource(getResources(), R.drawable.bat),
                            i * (900 / dungeonSize),
                            0));
        }
    }

    private void setResources() {
        Random r = new Random();
        for (int i = 0; i <= dungeonSize * 3; i++) {
            for (int j = 0; j <= dungeonSize * 3; j++) {
                if (characterType == 0) {
                    resources.add(
                            DungeonItemFactory.getDungeonItem("resource",
                                    BitmapFactory.decodeResource(getResources(), R.drawable.pizza),
                                    (r.nextInt(1048)),
                                    (r.nextInt(1600))));
                } else if (characterType == 1) {
                    resources.add(
                            DungeonItemFactory.getDungeonItem("resource",
                                    BitmapFactory.decodeResource(getResources(), R.drawable.wine),
                                    (r.nextInt(1048)),
                                    (r.nextInt(1600))));
                }
                else {
                    resources.add(
                            DungeonItemFactory.getDungeonItem("resource",
                                    BitmapFactory.decodeResource(getResources(), R.drawable.donut),
                                    (r.nextInt(1048)),
                                    (r.nextInt(1600))));
                }

            }
        }

    }

    void setHero() {
        if (characterType == 0) {
            hero =
                    (DungeonHero) DungeonCharacterFactory.getDungeonCharacter("hero", BitmapFactory.decodeResource(getResources(), R.drawable.man), 1, 1650);
        } else if (characterType == 1) {
            hero =
                    (DungeonHero) DungeonCharacterFactory.getDungeonCharacter("hero", BitmapFactory.decodeResource(getResources(), R.drawable.knight), 1, 1650);
        } else {
            hero =
                    (DungeonHero) DungeonCharacterFactory.getDungeonCharacter("hero", BitmapFactory.decodeResource(getResources(), R.drawable.doge), 1, 1650);

        }
    }

    void setMusic() {
        int music = user.getAudioCustomization();
        MusicPlayer.playMusic(music, getApplicationContext());
    }


    @Override
    protected void onPause() {
        super.onPause();
        gameView.thread.setRunning(false);
        elapsedSeconds = elapsedSeconds + (((int) System.currentTimeMillis() / 1000) - startSeconds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSeconds = (int) System.currentTimeMillis() / 1000;

    }

    void launchExitActivity(int resources) {
        elapsedSeconds = elapsedSeconds + ((int) System.currentTimeMillis() / 1000 - startSeconds);
        this.user.addResources(resources);
        this.user.addExperience(resources * (dungeonSize));
        this.user.addSecondsSpent(elapsedSeconds);
        this.user.setLevel(1);
        userDatabaseHelper.updateUser(this.user);

        Intent intent = new Intent(this, DungeonExitActivity.class);
        intent.putExtra("resources", resources);
        intent.putExtra("username", this.user.getUsername());
        startActivity(intent);
    }

    public void update() {

        updateHero();
        updateEnemies();

        if (heroAtDoor() || heroDead()) {
            gameView.thread.setRunning(false);
            launchExitActivity(hero.resourcesCollected);
        }
        turnCount++;
    }

    void updateHero() {
        if (heroNearWallx()) {
            hero.dirx = 0;
        }
        if (heroNearWally()) {
            hero.diry = 0;
        }
        hero.update();
        isAtResource();
        if ((hero.resourcesCollected >= 300) && ((elapsedSeconds + ((int) System.currentTimeMillis() / 1000) - startSeconds) <= 30)) {
            hero.resourcesCollected = hero.resourcesCollected * 10;
            launchExitActivity(hero.resourcesCollected);
        }
    }

    void updateEnemies() {
        Random r = new Random();
        if (turnCount == newEnemyInTurns) {
            enemies.add(
                    (DungeonEnemy) DungeonCharacterFactory.getDungeonCharacter("enemy",
                            BitmapFactory.decodeResource(getResources(), R.drawable.bat),
                            r.nextInt(1501),
                            r.nextInt(1651)));
            turnCount = 0;
        }
        for (int i = 0; i < enemies.size(); i++) {
            DungeonEnemy e = enemies.get(i);
            if (e.y != hero.y) {
                e.diry = (-(e.y - hero.y) / Math.abs(e.y - hero.y));
                e.dirx = 0;
            } else if (e.x != hero.x) {
                e.dirx = (-(e.x - hero.x) / Math.abs(e.x - hero.x));
                e.diry = 0;
            }
            e.update();
        }
    }

    boolean heroNearWallx() {
        for (int i = 0; i < verticalWalls.size(); i++) {
            DungeonItem wall = verticalWalls.get(i);
            if (hero.y + 120 >= wall.y && hero.y <= wall.y + 120) {
                if (hero.dirx > 0) {
                    int diff = hero.x + 50 + hero.dirx - wall.x;
                    if (diff == 0 || diff == -1 || diff == -2 || diff == -3) {
                        return true;
                    }
                }
                if (hero.dirx < 0) {
                    int diff2 = wall.x + 20 - (hero.x - 25 + hero.dirx);
                    if (diff2 == 0 || diff2 == -1 || diff2 == -2 || diff2 == -3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean heroNearWally() {
        for (int i = 0; i < verticalWalls.size(); i++) {
            DungeonItem wall = verticalWalls.get(i);
            if (hero.x + 50 + hero.dirx >= wall.x && hero.x <= wall.x + 20) {
                if (hero.diry > 0) {
                    int diff = hero.y + 120 + hero.diry - wall.y;
                    if (diff == 0 || diff == 1 || diff == 2 || diff == 3) {
                        return true;
                    }
                }
                if (hero.diry < 0) {
                    int diff2 = wall.y + 120 - (hero.y + hero.diry);
                    if (diff2 == 0 || diff2 == 1 || diff2 == 2 || diff2 == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean heroAtDoor() {
        return (hero.x + 100 >= this.exit.x && hero.y + 100 >= this.exit.y && hero.y - 50 <= this.exit.y + 100);
    }

    boolean heroDead() {
        for (int i = 0; i < enemies.size(); i++) {
            DungeonEnemy e = enemies.get(i);
            if ((e.x + 25 >= hero.x - 25 && e.x + 25 <= hero.x + 50) && (e.y + 25 >= hero.y - 25 && e.y + 25 <= hero.y + 50)) {
                hero.resourcesCollected = 0;
                return true;
            }
        }
        return false;
    }


    void isAtResource() {
        List<DungeonItem> toDelete = new ArrayList<>();
        for (int i = 0; i < resources.size(); i++) {
            DungeonItem r = resources.get(i);
            if ((hero.x + 100 >= r.x - 25 && hero.x - 25 <= r.x + 25) && (hero.y + 150 >= r.y && hero.y <= r.y + 50)) {
                toDelete.add(r);
                hero.resourcesCollected += 10;
            }
        }
        for (int i = 0; i < toDelete.size(); i++) {
            resources.remove(toDelete.get(i));
        }
    }

    List<DungeonItem> getVerticalWalls() {
        return this.verticalWalls;
    }

    List<DungeonItem> getResourceList() {
        return this.resources;
    }

    List<DungeonEnemy> getEnemyList() {
        return this.enemies;
    }

    DungeonHero getHero() {
        return this.hero;
    }

    DungeonItem getExit() {
        return this.exit;
    }


}
