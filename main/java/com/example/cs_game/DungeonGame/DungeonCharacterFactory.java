package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;

class DungeonCharacterFactory {
    static DungeonCharacter getDungeonCharacter(String type, Bitmap bmp, int x, int y) {
        if (type.equalsIgnoreCase("hero")) {
            return new DungeonHero(bmp, x, y);
        }
        else if (type.equalsIgnoreCase("enemy")) {
            return new DungeonEnemy(bmp, x, y);
        }
        return null;
    }
}
