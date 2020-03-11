package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class DungeonHero extends DungeonCharacter {
    int resourcesCollected;



    DungeonHero(Bitmap bmp, int x, int y) {
        super(bmp, x, y);
        dirx = 4;
        diry = 0;
    }

    void update() {
        if (x + dirx + 50 < 1100 && x + dirx > 0) {
            x += dirx;
        }
        if (y + diry + 50 < 1750 && y + diry > 0) {
            y += diry;
        }
    }
}
