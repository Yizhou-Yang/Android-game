package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class DungeonItem {

    int x, y;
    private Bitmap image;

    DungeonItem(Bitmap bmp, int x, int y) {
        this.image = bmp;
        this.x = x;
        this.y = y;
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
