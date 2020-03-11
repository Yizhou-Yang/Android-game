package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;

public class DungeonEnemy extends DungeonCharacter{

    DungeonEnemy(Bitmap bmp, int x, int y) {
        super(bmp, x, y);
        dirx = 0;
        diry = 1;
    }

    public void update(){
        x += dirx;
        y += diry;
    }
}
