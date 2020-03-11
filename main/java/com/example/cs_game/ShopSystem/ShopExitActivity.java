package com.example.cs_game.ShopGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cs_game.ShopGame.EquippingStage.EquipmentActivity;
import com.example.cs_game.IntentKeys;
import com.example.cs_game.R;

import java.util.Locale;

public class ShopExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_exit);

        Intent intent = getIntent();
        setScoresText(intent);
        setNextLevelButton(intent);
    }

    /**
     * Display the gamble score earned in the Shop Activity by fetching the information from intent
     * @param intent the Intent object holding the needed information
     */
    private void setScoresText(Intent intent) {
        double gambleScore = intent.getDoubleExtra(IntentKeys.GAMBLE_SCORE_KEY, 3);
        TextView gambleText = findViewById(R.id.gamble_score);
        gambleText.setText(String.format(Locale.CANADA,
                "Gamble score: %.3f", gambleScore));

        int expGained = intent.getIntExtra(IntentKeys.EXP_GAINED_KEY, 0);
        TextView expText = findViewById(R.id.shop_exp_text);
        expText.setText(String.format(Locale.CANADA, "Exp gained: %d", expGained));

        int timeSpent = intent.getIntExtra(IntentKeys.TIME_SPENT_KEY, 0);
        TextView timeSpentText = findViewById(R.id.shop_time_spent_text);
        timeSpentText.setText(String.format(Locale.CANADA,
                "Time Spent: %d seconds", timeSpent));

    }

    private void setNextLevelButton(Intent intent) {
        final String userName = intent.getStringExtra("username");
        findViewById(R.id.equipment_level_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextLevelIntent = new Intent(ShopExitActivity.this,
                        EquipmentActivity.class);
                nextLevelIntent.putExtra("username", userName);
                startActivity(nextLevelIntent);
            }
        });
    }
}
