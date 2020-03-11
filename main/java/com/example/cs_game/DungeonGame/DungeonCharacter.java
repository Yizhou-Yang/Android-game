package com.example.cs_game.DungeonGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class DungeonCharacter {
  private Bitmap image;
  int x, y;
  int dirx;
  int diry;

  DungeonCharacter(Bitmap bmp, int x, int y) {
    image = bmp;
    this.x = x;
    this.y = y;
  }

  void draw(Canvas canvas) {
    canvas.drawBitmap(image, x, y, null);
  }

  abstract void update();

}
