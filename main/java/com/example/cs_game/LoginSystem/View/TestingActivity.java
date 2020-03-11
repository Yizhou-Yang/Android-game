package com.example.cs_game.LoginSystem.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.Score;
import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.ScoreboardDatabaseHelper;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.R;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;

import java.util.List;


public class TestingActivity extends AppCompatActivity {

    UserDatabaseHelper userDb;
    ScoreboardDatabaseHelper scoreDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        userDb = new UserDatabaseHelper(this);
        scoreDb = new ScoreboardDatabaseHelper(this);
        readAll();
    }

    public void readAll() {
        List<Score> scores = scoreDb.getAllScores(1);
        StringBuffer buffer = new StringBuffer();
        for (Score score:scores) {
            buffer.append(score.toString() + "\n");
        }
        FrontEndUtility.dialog("Scores", buffer.toString(),this);

        /*
        Cursor res = userDb.getAllData();
        if (res.getCount() == 0) {
            // No data
            return;
        } else {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append("Username " + res.getString(0) + "\n");
                buffer.append("Next Variable " + res.getString(1) + "\n\n");

            }
            FrontEndUtility.dialog("Data", buffer.toString(), this);
        }

         */

    }

    public void readUser(String username) {
        User user = userDb.getUser(username);
        String message =  user.report();
        FrontEndUtility.dialog("User Data", message, this);
    }

    public void showField(View view) {
        EditText et = findViewById(R.id.TestingText);
        String name = et.getText().toString();
        readUser(name);
    }

    public void updateField(View view) {
        EditText user = findViewById(R.id.TestingUpdateText);
        EditText color = findViewById(R.id.TestingColorText);
        EditText resources = findViewById(R.id.TestingResourceText);
        EditText monster = findViewById(R.id.TestingMonsterText);
        int d_resources = Integer.parseInt(resources.getText().toString());
        int d_monster = Integer.parseInt(monster.getText().toString());
        //User user1 = new User(user.getText().toString(), d_resources, d_monster, color.getText().toString(), 0);
        //userDb.updateUser(user1);

    }
}
