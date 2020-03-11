package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;

class DungeonItemFactory {
    static DungeonItem getDungeonItem (String type, Bitmap bmp, int x, int y) {
        if (type.equalsIgnoreCase("resource")) {
            return new DungeonResource(bmp, x, y);
        }
        else if (type.equalsIgnoreCase("exit")) {
            return new DungeonExit(bmp, x, y);
        }
        else if (type.equalsIgnoreCase("wall")) {
            return new DungeonVerticalWall(bmp, x, y);
        }
        return null;
    }
}
