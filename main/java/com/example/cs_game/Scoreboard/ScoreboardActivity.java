package com.example.cs_game.Scoreboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.Score;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.R;

import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    ScoreBoardListProvider listProvider;
    String username;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.scoreboardArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        listProvider = new ScoreBoardListProvider(this);
        username = getIntent().getStringExtra("username");
        users = listProvider.getGlobalLeaderboard();
        StringBuffer buffer = new StringBuffer();
        for (User user : users) {
            buffer.append(user.report());
            buffer.append("\n----X----\n");
        }
    }

    public void ShowTable(View view) {
        UserDatabaseHelper db = new UserDatabaseHelper(this);
        Spinner spinner = findViewById(R.id.spinner);
        String selected = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        if (selected.equals("Personal score")) {
            List<Score> scores = listProvider.getPersonalboard(username, db.getUser(username).getLevel(), true);
            String output = String.format("%10s        %10s        %10s \n", "Name", "Score", "Level");
            for (Score score : scores) {
                output = output + String.format("%12s         %12s         %12s \n", score.getAlias(), String.valueOf(score.getScore()), String.valueOf(score.getLevel()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores")) {
            List<User> users = listProvider.getGlobalLeaderboard();
            String output = String.format("%10s        %10s        %10s        %10s \n", "Name", "Resources", "Experience", "Seconds Spent");
            for (User user : users) {
                output = output + String.format("%12s         %12s         %12s          %12s \n", user.getUsername(), String.valueOf(user.getResources()), String.valueOf(user.getExperience()), String.valueOf(user.getSecondsSpent()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores sorted by seconds")) {
            List<User> users = listProvider.sortUsersBy(listProvider.getGlobalLeaderboard(), "seconds spent", true);
            String output = String.format("%10s        %10s        %10s        %10s \n", "Name", "Resources", "Experience", "Seconds Spent");
            for (User user : users) {
                output = output + String.format("%12s         %12s         %12s            %12s \n", user.getUsername(), String.valueOf(user.getResources()), String.valueOf(user.getExperience()), String.valueOf(user.getSecondsSpent()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores sorted by resources")) {
            List<User> users = listProvider.sortUsersBy(listProvider.getGlobalLeaderboard(), "resources", true);
            String output = String.format("%10s        %10s        %10s        %10s \n", "Name", "Resources", "Experience", "Seconds Spent");
            for (User user : users) {
                output = output + String.format("%12s         %12s         %12s            %12s \n", user.getUsername(), String.valueOf(user.getResources()), String.valueOf(user.getExperience()), String.valueOf(user.getSecondsSpent()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores sorted by experience")) {
            List<User> users = listProvider.sortUsersBy(listProvider.getGlobalLeaderboard(), "experience", true);
            String output = String.format("%10s        %10s        %10s        %10s \n", "Name", "Resources", "Experience", "Seconds Spent");
            for (User user : users) {
                output = output + String.format("%12s         %12s         %12s            %12s \n", user.getUsername(), String.valueOf(user.getResources()), String.valueOf(user.getExperience()), String.valueOf(user.getSecondsSpent()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores for Dungeon Level")) {
            List<Score> scores = listProvider.getLevelLeaderboard(1, true);
            String output = String.format("%10s        %10s        %10s \n", "Name", "Score", "Level");
            for (Score score : scores) {
                output = output + String.format("%12s         %12s         %12s \n", score.getAlias(), String.valueOf(score.getScore()), String.valueOf(score.getLevel()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores for Shop Level")) {
            List<Score> scores = listProvider.getLevelLeaderboard(2, true);
            String output = String.format("%10s        %10s        %10s \n", "Name", "Score", "Level");
            for (Score score : scores) {
                output = output + String.format("%12s         %12s         %12s \n", score.getAlias(), String.valueOf(score.getScore()), String.valueOf(score.getLevel()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
        else if (selected.equals("Global scores for Combat Level")) {
            List<Score> scores = listProvider.getLevelLeaderboard(0, true);
            String output = String.format("%10s        %10s        %10s \n", "Name", "Score", "Level");
            for (Score score : scores) {
                output = output + String.format("%12s         %12s         %12s \n", score.getAlias(), String.valueOf(score.getScore()), String.valueOf(score.getLevel()));
            }
            TextView tv = findViewById(R.id.textView11);
            tv.setText(output);
        }
    }

    public void ShowUserStats(View view) {

    }

    public void ShowScoreBoardGlobal(View view) {
        List<Score> s = listProvider.getLevelLeaderboard(0, true);

    }


    public void ShowScoreBoardPersonal(View view) {

    }

}
