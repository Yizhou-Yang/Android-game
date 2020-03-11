package com.example.cs_game.DungeonGame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.cs_game.R;

import java.util.Random;

public class DungeonGameView extends SurfaceView implements SurfaceHolder.Callback {
    DungeonMainThread thread;

    DungeonGameActivity activity;

    public DungeonGameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);

    }

    void setActivity(DungeonGameActivity act) {
        this.activity = act;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DungeonMainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float eventx = event.getX();
            float eventy = event.getY();
            if (eventx > activity.getHero().x + 120) {
                activity.getHero().dirx = 4;
                activity.getHero().diry = 0;
                return true;
            } else if (eventx < activity.getHero().x - 40) {
                activity.getHero().dirx = -4;
                activity.getHero().diry = 0;
                return true;
            }
            if (eventy < activity.getHero().y - 40) {
                activity.getHero().dirx = 0;
                activity.getHero().diry = -4;
                return true;
            } else if (eventy > activity.getHero().y + 90) {
                activity.getHero().dirx = 0;
                activity.getHero().diry = 4;
                return true;
            }
            performClick();
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.GRAY);
            for (int i = 0; i < activity.getEnemyList().size(); i++) {
                activity.getEnemyList().get(i).draw(canvas);
            }
            for (int i = 0; i < activity.getResourceList().size(); i++) {
                activity.getResourceList().get(i).draw(canvas);
            }
            activity.getHero().draw(canvas);
            activity.getExit().draw(canvas);
      for (int i=0; i < activity.getVerticalWalls().size(); i++) {
        activity.getVerticalWalls().get(i).draw(canvas);
      }
        }
    }


}
