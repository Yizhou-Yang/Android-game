package com.example.cs_game.MainMenu.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.CombatSystem.CombatActivity;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.Docs.InformationActivity;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.LoginSystem.View.LoginActivity;
import com.example.cs_game.LoginSystem.View.TestingActivity;
import com.example.cs_game.R;
import com.example.cs_game.Scoreboard.ScoreboardActivity;
import com.example.cs_game.ShopGame.ShopActivity;
import com.example.cs_game.Utilities.MusicPlayer;

import static com.example.cs_game.MainMenu.Presenter.MainMenuPresenter.getUserLevel;
import static com.example.cs_game.MainMenu.Presenter.MainMenuPresenter.resetUser;


public class MainActivity extends AppCompatActivity {

    String username;
    User user;
    UserDatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get information from the login-user function to see who the active user is.
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        TextView tv = findViewById(R.id.textView);
        CharSequence currentText = tv.getText();
        tv.setText(currentText + " " + username);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayer.killMusic();
    }

    /**
     * Starts a new game
     *
     * @param view
     */
    public void newGame(View view) {
        resetUser(this, username);
        Intent intent = new Intent(MainActivity.this, LandingActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Resumes the game that was left off
     *
     * @param view
     */
    public void resumeGame(View view) {

        /*
        Intent intent = new Intent(MainActivity.this, TestingActivity.class);
        startActivity(intent);
        */


        Class<?> c;
        int level = getUserLevel(this, username);
        if (level == 0) {
            c = LandingActivity.class;

        } else if (level == 1) {
            c = ShopActivity.class;

        } else if (level == 2) {
            c = CombatActivity.class;
        } else {
            c = TestingActivity.class;
        }
        if (level < 3 && level >= 0) {
            Intent intent = new Intent(MainActivity.this, c);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            CharSequence chars = "No Game Saved, Please Start New Game level was " + level;
            FrontEndUtility.popUp(chars, this);
            Intent intent = new Intent(MainActivity.this, TestingActivity.class);
            startActivity(intent);
        }


    }

    /**
     * Takes the user to the scoreboard
     *
     * @param view
     */
    public void scoreBoard(View view) {
        Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Takes the user to the information page
     */
    public void information(View view) {
        startActivity(new Intent(MainActivity.this, InformationActivity.class));
    }

    /**
     * Logs the user out
     *
     * @param view
     */
    public void exit(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("logging_out", true);
        startActivity(intent);
    }

}
