package com.example.cs_game.CombatGame.CombatSystem.CombatView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.Score;
import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.ScoreboardDatabaseHelper;
import com.example.cs_game.Utilities.IntentKeys;
import com.example.cs_game.MainMenu.View.LandingActivity;
import com.example.cs_game.MainMenu.View.MainActivity;
import com.example.cs_game.R;
import com.example.cs_game.Utilities.FrontEndUtility;

import java.util.Locale;

public class CombatExitActivity extends AppCompatActivity {

    private boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_exit);

        Intent intent = getIntent();
        setScore(intent);
        setSaveScoreButton(intent);
        setNextLevelButton(intent);
    }

    private void setScore(Intent intent) {
        if (intent != null) {
            int timeSpent = intent.getIntExtra(IntentKeys.TIME_SPENT_KEY, 60);
            int exp = intent.getIntExtra(IntentKeys.EXP_GAINED_KEY, 0);
            boolean won = intent.getBooleanExtra(IntentKeys.BATTLE_WON_KEY, false);
            int combatScore = intent.getIntExtra(IntentKeys.COMBAT_SCORE_KEY, 0);

            TextView expText = findViewById(R.id.combat_exp_text);
            expText.setText(String.format(Locale.CANADA, "Exp gained: %d", exp));

            TextView timeSpentText = findViewById(R.id.combat_time_spent_text);
            timeSpentText.setText(String.format(Locale.CANADA,
                    "Time Spent: %d seconds", timeSpent));

            TextView combatScoreText = findViewById(R.id.combat_score_text);
            combatScoreText.setText(String.format(Locale.CANADA, "Combat Score: %d",
                    combatScore));

            TextView battleResultText = findViewById(R.id.battle_result_text);
            String battleResult;
            if (won) battleResult = "Battle Won!";
            else battleResult = "Battle Lost";
            battleResultText.setText(battleResult);
        }
    }

    private void setNextLevelButton(final Intent intent) {
        if (intent != null) {
            final boolean won = intent.getBooleanExtra(IntentKeys.BATTLE_WON_KEY,
                    false);
            findViewById(R.id.combat_next_level_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextLevelIntent;
                    if (won) {
                        nextLevelIntent = new Intent(CombatExitActivity.this,
                                LandingActivity.class);
                    } else {
                        nextLevelIntent = new Intent(CombatExitActivity.this,
                                MainActivity.class);
                    }
                    nextLevelIntent.putExtra("username",
                            intent.getStringExtra("username"));

                    startActivity(nextLevelIntent);
                }
            });

        }
    }

    private void setSaveScoreButton(Intent intent) {
        final int combatScore = intent.getIntExtra(
                IntentKeys.COMBAT_SCORE_KEY, 0);
        final String username = intent.getStringExtra("username");
        findViewById(R.id.combat_save_score_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias = ((EditText) findViewById(R.id.combat_score_name_entry)).
                        getText().toString();
                if (!saved) {
                    if (!alias.equals("")) {
                        saveScore(alias, username, combatScore);
                    } else FrontEndUtility.popUp("Invalid name",
                            CombatExitActivity.this);
                } else {
                    FrontEndUtility.popUp("Score Has Already Been Saved",
                            CombatExitActivity.this);
                }
            }
        });
    }

    private void saveScore(String alias, String username, int combatScore) {
        ScoreboardDatabaseHelper scoreDb = new ScoreboardDatabaseHelper(
                getApplicationContext());
        Score.Builder scb = new Score.Builder(username, combatScore, 3);
        scb.setAlias(alias);
        Score score = scb.build();
        scoreDb.createScore(score);
        FrontEndUtility.popUp("Score Saved!", this);
        saved = true;
    }


}
