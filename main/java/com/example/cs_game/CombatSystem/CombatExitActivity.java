package com.example.cs_game.CombatSystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cs_game.IntentKeys;
import com.example.cs_game.MainMenu.View.LandingActivity;
import com.example.cs_game.MainMenu.View.MainActivity;
import com.example.cs_game.R;

import java.util.Locale;

public class CombatExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_exit);

        Intent intent = getIntent();
        setScore(intent);
        setNextLevelButton(intent);
    }

    private void setScore(Intent intent) {
        if (intent != null) {
            int timeSpent = intent.getIntExtra(IntentKeys.TIME_SPENT_KEY, 60);
            int exp = intent.getIntExtra(IntentKeys.EXP_GAINED_KEY, 0);
            boolean won = intent.getBooleanExtra(IntentKeys.BATTLE_WON_KEY, false);

            TextView expText = findViewById(R.id.combat_exp_text);
            expText.setText(String.format(Locale.CANADA, "Exp gained: %d", exp));

            TextView timeSpentText = findViewById(R.id.combat_time_spent_text);
            timeSpentText.setText(String.format(Locale.CANADA,
                    "Time Spent: %d seconds", timeSpent));

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
            findViewById(R.id.landing_level_button).setOnClickListener(new View.OnClickListener() {
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


}
