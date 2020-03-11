/**
 * Page in between the main menu and a new game where the user gets to set their desired
 * Customizations for the game.
 * Can only be changed by starting a new game.
 */
package com.example.cs_game.MainMenu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.DungeonGame.DungeonGameActivity;
import com.example.cs_game.R;

import static com.example.cs_game.MainMenu.Presenter.LandingPresenter.updateUserCustomization;

public class LandingActivity extends AppCompatActivity {

    String username;
    private int audio = 0;
    private int difficulty = 0;
    private int resourceVariety = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        handleSpinners();


    }

    /**
     * Read the desired settings and save them. Then start game
     *
     * @param view
     */
    public void play(View view) {
        Intent intent = new Intent(LandingActivity.this, DungeonGameActivity.class);
        updateValues();
        updateUserCustomization(username, audio, difficulty, resourceVariety, this);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void populateSpinner(Spinner spinner, int array) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void handleSpinners() {
        Spinner sizeSpinner = findViewById(R.id.sizeSpinner);
        Spinner diffSpinner = findViewById(R.id.difficultySpinner);
        Spinner varietySpinner = findViewById(R.id.varietySpinner);

        populateSpinner(sizeSpinner, R.array.sizeArray);
        populateSpinner(diffSpinner, R.array.difficultyArray);
        populateSpinner(varietySpinner, R.array.varietyArray);
    }

    private int getAudio() {
        Spinner spinner = findViewById(R.id.sizeSpinner);
        String selected = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        if (selected.equals("Rustic")) {
            return 0;
        } else if (selected.equals("Rock")) {
            return 1;
        } else {
            return 2;
        }
    }

    private int getDifficulty() {
        Spinner spinner = findViewById(R.id.difficultySpinner);
        String selected = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        if (selected.equals("Easy")) {
            return 0;
        } else if (selected.equals("Medium")) {
            return 1;
        } else {
            return 2;
        }
    }

    private int getResourceVariety() {
        Spinner spinner = findViewById(R.id.varietySpinner);
        String selected = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        if (selected.equals("Classic")) {
            return 0;
        } else if (selected.equals("Fancy")) {
            return 1;
        } else {
            return 2;
        }
    }

    private void updateValues() {
        audio = getAudio();
        difficulty = getDifficulty();
        resourceVariety = getResourceVariety();
        return;
    }

    /**
     * We don't want user to be able to go back to previous activity
     */
    @Override
    public void onBackPressed() {

    }
}
