package com.example.cs_game.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.cs_game.R;

public class MusicPlayer {

    private static MediaPlayer player;
    private static int choice = -1;

    /**
     * Play different music depending on choice in given context (activity)
     * @param choice user's choice of music
     * @param context the screen (activity) to play the music in
     */
    public static void playMusic(int choice, Context context) {
        if (player == null || MusicPlayer.choice != choice) {
            killMusic();
            if (choice == 0) {
                player = MediaPlayer.create(context, R.raw.rustic);
            } else if (choice == 1) {
                player = MediaPlayer.create(context, R.raw.rock);
            } else {
                player = MediaPlayer.create(context, R.raw.classic);
            }
        }

        MusicPlayer.choice = choice;


        if (!player.isPlaying()) {
            player.start();
            player.setLooping(true);
        }


    }

    public static void killMusic() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
