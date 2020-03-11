package com.example.cs_game.CombatGame.CombatSystem.CombatView;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Consumable;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.CombatManager;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.CombatGame.CombatSystem.CombatPresenter.CombatPresenter;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseInterface;
import com.example.cs_game.Utilities.IntentKeys;
import com.example.cs_game.Utilities.ItemListFragment;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.MainMenu.View.MainActivity;
import com.example.cs_game.Utilities.MusicPlayer;
import com.example.cs_game.R;

import java.util.List;

public class CombatActivity extends AppCompatActivity implements CombatView {

    private CombatPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        UserDatabaseInterface db = new UserDatabaseHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        User user = db.getUser(username);
        db.close();

        presenter = new CombatPresenter(this, db, user);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    /**
     * Set the health bars of player and monster for the beginning of combat
     */
    @Override
    public void setHealthBars(int maxPlayerHealth, int currPlayerHealth,
                              int maxMonsterHealth, int currMonsterHealth) {
        ProgressBar playerHealthBar = findViewById(R.id.player_health);
        playerHealthBar.setMax(maxPlayerHealth);
        playerHealthBar.setProgress(currPlayerHealth);

        ProgressBar monsterHealthBar = findViewById(R.id.monster_health);
        monsterHealthBar.setMax(maxMonsterHealth);
        monsterHealthBar.setProgress(currMonsterHealth);
    }

    // set the buttons for the fragment, attack and consumable.
    @Override
    public void setButtons() {
        ButtonFragment buttonFragment = new ButtonFragment();
        buttonFragment.setOnButtonClickedListener(new ButtonFragment.OnButtonClickedListener() {
            @Override
            public void onAttackButtonClicked() {
                presenter.onAttackButtonClicked();
            }

            @Override
            public void onConsumableButtonClicked() {
                presenter.onConsumableButtonClicked();
            }
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.options, buttonFragment).commit();
    }

    /**
     * UpdateDisplay the combat log
     *
     * @param combatLog the string to be added to the combat log TextView
     */
    @Override
    public void updateCombatLog(String combatLog) {
        TextView combatLogTextView = findViewById(R.id.combat_log);
        combatLogTextView.setText(combatLog);
    }

    /**
     * Show the list of given weapons
     *
     * @param weapons the list of weapons to be displayed on the screen
     */
    @Override
    public void showWeaponList(List<Weapon> weapons) {
        ItemListFragment<Weapon> fragment = new ItemListFragment<>(weapons);
        fragment.setOnItemClickedListener(new OnItemClickedInFragmentListener<Weapon>() {
            @Override
            public void onItemClick(@NonNull Weapon item) {
                presenter.onItemClicked(item);
            }
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.options, fragment).addToBackStack("weaponList").commit();
    }

    /**
     * how the list of given consumables
     *
     * @param consumables the list of consumables to be displayed on the screen
     */
    @Override
    public void showConsumableList(List<Consumable> consumables) {
        ItemListFragment<Consumable> fragment = new ItemListFragment<>(consumables);
        fragment.setOnItemClickedListener(new OnItemClickedInFragmentListener<Consumable>() {
            @Override
            public void onItemClick(@NonNull Consumable item) {
                presenter.onItemClicked(item);
            }
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.options, fragment).addToBackStack("consumableList").commit();
    }

    /**
     * Show the list of body parts of character
     *
     * @param bodyParts the list of BodyPart to display
     */
    @Override
    public void showBodyPartsList(List<BodyPart> bodyParts) {
        BodyPartFragment bodyPartFragment = new BodyPartFragment(bodyParts);
        bodyPartFragment.setListener(new OnItemClickedInFragmentListener<BodyPart>() {
            @Override
            public void onItemClick(@NonNull BodyPart item) {
                presenter.onItemClicked(item);
                clearFragmentBackStack();
            }
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.options, bodyPartFragment).addToBackStack("bodyPartList").commit();
    }

    /**
     * Clear all fragment on the back stack.
     */
    private void clearFragmentBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        while (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
    }

    /**
     * Go back options if user decides to cancel his choice to use weapon or consumable.
     * Moreover, user should not be able to go back yo EquipmentActivity once combat starts.
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * Update health by updating the ProgressBar referred by healthBarId to newHealth value.
     *
     * @param healthBarId the id referring to the ProgressBar
     * @param newHealth   the new health that health bar should take on
     */
    private void updateHealth(int healthBarId, int newHealth) {
        ProgressBar healthBar = findViewById(healthBarId);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(healthBar,
                "progress", healthBar.getProgress(),
                newHealth);
        progressAnimator.setDuration(800);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    /**
     * Update the health bar of player and monster on the screen
     *
     * @param playerHealth  the new player health to display on the screen
     * @param monsterHealth the new monster health to display on the screen
     */
    @Override
    public void updateHealths(int playerHealth, int monsterHealth) {
        updateHealth(R.id.player_health, playerHealth);
        updateHealth(R.id.monster_health, monsterHealth);

    }

    @Override
    public void setMusic(int choice) {
        MusicPlayer.playMusic(choice, getApplicationContext());
    }

    /**
     * Proceed to the next game; only called when player won the fight
     */
    @Override
    public void toNextLevelDialog(String title, String message, final int timeSpent,
                                  final int exp, final boolean won, final int combatScore) {
        CombatManager.setCombatManager(null);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CombatActivity.this,
                        CombatExitActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra(IntentKeys.TIME_SPENT_KEY, timeSpent);
                intent.putExtra(IntentKeys.EXP_GAINED_KEY, exp);
                intent.putExtra(IntentKeys.BATTLE_WON_KEY, won);
                intent.putExtra(IntentKeys.COMBAT_SCORE_KEY, combatScore);

                startActivity(intent);
                finish();
            }
        };
        FrontEndUtility.dialogWithOption(title, message, this, listener);


    }

    /**
     * Proceed to main menu and clear player data
     */
    @Override
    public void toMainMenu() {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntentForNextActivity(MainActivity.class);
                clearPlayer(intent.getStringExtra("username"));
                startActivity(intent);
                finish();
            }
        };

        FrontEndUtility.dialogWithOption("To Main Menu",
                "You lost the battle.", this, listener);
    }

    /**
     * Create and return intent heading to the Activity of Class type
     *
     * @param type the class object of the next activity
     * @return the intent to be sent for the next activity
     */
    private Intent getIntentForNextActivity(Class type) {
        Intent intent = new Intent(this, type);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        return intent;
    }
    /**
     * Clear the player data corresponding to username
     *
     * @param username the string of the User's account
     */
    //should be moved to the presenter
    private void clearPlayer(String username) {
        UserDatabaseHelper db = new UserDatabaseHelper(this);
        User user = db.getUser(username);

        //user.addLevel(GameManager.getPlayer().getResources());
        // DJ here, not sure why you are adding level here so I've commented this out
        //user.setLevel(2);
        user.setResources(presenter.gameManager.getGameState().getPlayer().getResources() / 2);
        user.addExperience(1);
        db.updateUser(user);
        presenter.gameManager.getGameState().setPlayer(null);
    }
}