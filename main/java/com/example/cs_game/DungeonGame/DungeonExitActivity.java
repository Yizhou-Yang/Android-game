package com.example.cs_game.DungeonGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.Score;
import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.ScoreboardDatabaseHelper;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.R;
import com.example.cs_game.ShopGame.ShopActivity;

public class DungeonExitActivity extends AppCompatActivity {

    String username;
    int resources;
    int seconds;
    int experience;
    ScoreboardDatabaseHelper scoreDb;
    UserDatabaseHelper userdb;
    boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon_exit);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        scoreDb = new ScoreboardDatabaseHelper(this);
        userdb = new UserDatabaseHelper(this);
        seconds = userdb.getUser(username).getSecondsSpent();
        resources = userdb.getUser(username).getResources();
        experience = userdb.getUser(username).getExperience();
        TextView tv = findViewById(R.id.textView8);
        tv.setText(String.valueOf(resources));
        TextView tv2 = findViewById(R.id.textView14);
        tv2.setText(String.valueOf(experience));
        TextView tv3 = findViewById(R.id.textView16);
        tv3.setText(String.valueOf(seconds));
    }

    public void nextLevel(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void saveScore(View view){
        String alias = ((EditText)findViewById(R.id.nameToSaveText)).getText().toString();
        if(!saved) {
            Score.Builder scb = new Score.Builder(username, resources, 1);
            if(!alias.equals("")){
                scb.setAlias(alias);
            }
            Score score = scb.build();
            scoreDb.createScore(score);
            FrontEndUtility.popUp("Score Saved!", this);
            saved = true;
        }else{
            FrontEndUtility.popUp("Score Has Already Been Saved", this);
        }
    }

    /**
     * We don't want user to be able to go back to previous activity
     */
    @Override
    public void onBackPressed() {

    }
}
