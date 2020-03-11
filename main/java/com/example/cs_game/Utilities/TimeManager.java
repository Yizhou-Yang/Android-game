package com.example.cs_game.Utilities;

public class TimeManager {
    // Time when count was started
    private long beginTime;

    /**
     * Begin counting time by setting beginTime to current time.
     */
    public void beginCount() {
        beginTime = System.currentTimeMillis();
    }

    /**
     * Get the difference of time between now and beginTime in seconds.
     *
     * @return time difference between now and beginTime in seconds if beginTime was intialized.
     * Otherwise, return 0
     */
    public int getDifference() {
        if (beginTime == 0) return 0;
        return (int) (System.currentTimeMillis() - beginTime) / 1000;
    }
}
