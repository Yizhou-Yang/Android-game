package com.example.cs_game.DungeonGame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class DungeonMainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private DungeonGameView gameView;
    private boolean running;
    private static Canvas canvas;

    DungeonMainThread(SurfaceHolder surfaceHolder, DungeonGameView gameView) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                this.gameView.activity.update();
                this.gameView.draw(canvas);
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void setRunning(boolean isRunning) {
        running = isRunning;
    }


}
