package com.example.cs_game.ShopGame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.DisplayableAdapter;
import com.example.cs_game.IntentKeys;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.MainMenu.View.MainActivity;
import com.example.cs_game.Utilities.MusicPlayer;
import com.example.cs_game.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity implements ShopView {

    private DisplayableAdapter<Item> shopItemAdapter = new ShopItemAdapter<>();
    private DisplayableAdapter<Item> inventoryItemAdapter = new ShopItemAdapter<>();

    private ShopPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        UserDatabaseHelper db = new UserDatabaseHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        User user = db.getUser(username);
        db.close();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        presenter = new ShopPresenter(this, user);
        presenter.onCreate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    /**
     * Initialize the shop items and display them
     */
    @Override
    public void setShopItemList(List<? extends Item> items) {
        RecyclerView shopItemList = findViewById(R.id.shop_items);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.HORIZONTAL, false);

        shopItemList.setLayoutManager(layoutManager);

        shopItemAdapter.addAllItems(items);
        shopItemList.setAdapter(shopItemAdapter);
    }

    /**
     * Set the action that occurs when user clicks on any shop shop_item
     */
    @Override
    public void setShopItemOnClickListener() {
        shopItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
                // final because inner anonymous classes need access to this
                final Item item = shopItemAdapter.getItem(position);
                presenter.onShopItemClicked(item);
            }
        });
    }

    /**
     * Add the default tabs to the TabLayout object that tabId refers to
     */
    @Override
    public void setShopTabs() {
        TabLayout tabs = findViewById(R.id.shop_tab);
        tabs.addTab(tabs.newTab().setText("Weapons"));
        tabs.addTab(tabs.newTab().setText("Armors"));
        tabs.addTab(tabs.newTab().setText("Consumables"));

    }

    /**
     * Set OnTabSelectedListeners on shop tabs
     */
    @Override
    public void setShopTabsOnClickListener() {
        TabLayout tabs = findViewById(R.id.shop_tab);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                presenter.onShopTabSelected(tabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Called when shop tab is changed.
     *
     * @param items the new list of items to display
     */
    @Override
    public void onShopTabChange(List<? extends Item> items) {
        shopItemAdapter.removeAllItems();
        shopItemAdapter.addAllItems(items);
        shopItemAdapter.notifyDataSetChanged();
    }

    /**
     * Set tabs for inventory
     */
    @Override
    public void setInventoryTabs() {
        TabLayout tabs = findViewById(R.id.inventory_tab);
        tabs.addTab(tabs.newTab().setText("Weapons"));
        tabs.addTab(tabs.newTab().setText("Armors"));
        tabs.addTab(tabs.newTab().setText("Consumables"));
    }

    /**
     * Set OnTabSelectedListeners on inventory tabs
     */
    @Override
    public void setInventoryTabsOnClickListener() {
        TabLayout tabs = findViewById(R.id.inventory_tab);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                presenter.onInventoryTabSelected(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Called when inventory tab is changed.
     *
     * @param items the new list of items to display
     */
    @Override
    public void onInventoryTabChange(List<? extends Item> items) {
        inventoryItemAdapter.removeAllItems();
        inventoryItemAdapter.addAllItems(items);
        inventoryItemAdapter.notifyDataSetChanged();
    }

    /**
     * Set the inventory shop_item list
     */
    @Override
    public void setInventoryItemList(List<? extends Item> items) {
        RecyclerView inventoryItemList = findViewById(R.id.inventory_container);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.HORIZONTAL, false);

        inventoryItemList.setLayoutManager(layoutManager);

        inventoryItemAdapter.addAllItems(items);
        inventoryItemList.setAdapter(inventoryItemAdapter);
    }

    /**
     * Set up a listener to be called when item in inventory is clicked
     */
    @Override
    public void setInventoryItemOnClickListener() {
        inventoryItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
                // final because inner anonymous classes need access to this
                final Item item = inventoryItemAdapter.getItem(position);
                presenter.onInventoryItemClicked(item);
            }

        });
    }

    /**
     * Show dialog, which contains multiple options (Buy, Gamble, Cancel). Should be called when
     * shop items are clicked.
     *
     * @param itemDescription description of the item user is trying to buy
     * @param price      price of the item clicked
     * @param gambleCost gamble cost for the item clicked
     */
    @Override
    public void showShopItemOptionsDialog(String itemDescription, int price, int gambleCost) {
        // Build the dialog for confirming player's decision to buy the clicked shop_item
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
        builder.setTitle("Buy");
        builder.setMessage(itemDescription + "\nAre you sure? Cost: " + price + " Gold");
        builder.setCancelable(false);
        builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onBuy();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton(String.format(Locale.CANADA,
                "Gamble: %d gold", gambleCost),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onGambleOption();
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onCancel();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Show gamble dialog, which shows range of gamble (0 to maxGambleRange)
     *
     * @param maxGambleNum the largest number the player can guess
     * @param answer       temporary parameter for testing purposes
     */
    @Override
    public void showGambleDialog(int maxGambleNum, int answer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
        LayoutInflater inflater = (LayoutInflater) ShopActivity.this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View gambleTemplate = inflater.inflate(R.layout.gamble_input, null);

        TextView gambleDescription = gambleTemplate.findViewById(R.id.gamble_range_text);
        gambleDescription.setText(String.format(Locale.CANADA,
                "Guess from range: 0 - %d answer (for test): %d",
                maxGambleNum, answer));
        final EditText gambleInput = gambleTemplate.findViewById(R.id.gamble_number_input);
        Button gambleButton = gambleTemplate.findViewById(R.id.gamble_button);

        builder.setTitle("Gamble!");
        builder.setView(gambleTemplate);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();

        gambleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gambleInput EditText object only accepts numbers so this is okay
                String input = gambleInput.getText().toString();
                presenter.onGamble(input);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Show dialog asking user if they want to sell the chosen item.
     * @param itemDescription the description of the item user is selling
     * @param price the price of the item to be shown on screen.
     */
    @Override
    public void showSellItemDialog(String itemDescription, int price) {
        // Build the dialog for confirming player's decision to buy the clicked shop_item
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
        builder.setTitle("Sell");
        builder.setMessage(itemDescription + "\nAre you sure? Earn: " + price + " Gold");
        builder.setPositiveButton("Sell", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onSell();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * When a user sells the item, update the inventory display
     *
     * @param item the Item that was sold;
     */
    @Override
    public void onItemSold(Item item) {
        inventoryItemAdapter.removeItem(item);
        inventoryItemAdapter.notifyDataSetChanged();
    }

    /**
     * Show the bought item in case it was bough while inventory and shop tabs were same.
     * Ex: Weapon shop tab and Weapon inventory tab.
     * @param item Item that was bought, which is to be displayed
     */
    @Override
    public void showBoughtItem(Item item) {
        if (isAtSameTab()) {
            inventoryItemAdapter.addItem(item);
            inventoryItemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Show Toast message popup.
     * @param message the message to show to user on the popup
     */
    @Override
    public void showPopUp(String message) {
        FrontEndUtility.popUp(message, this);
    }

    /**
     * Update the display of amount of gold the player has
     */
    @Override
    public void updatePlayerResourcesDisplay(int resources) {
        TextView playerGold = findViewById(R.id.gold);
        playerGold.setText(String.format(Locale.CANADA,
                "%d Gold", resources));
    }

    /**
     * Set the pause button's OnClickListener.
     */
    @Override
    public void setPauseButton() {
        findViewById(R.id.shop_pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPauseButtonClicked();
            }
        });
    }

    /**
     * Show pause dialog with list of choices given by options
     * @param options list of choices user can choose
     */
    @Override
    public void showPauseDialog(CharSequence[] options) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onPauseDialogOptionClicked(which);
            }
        };

        FrontEndUtility.listDialog("Pause", options, listener, this);
    }

    /**
     * Play music depending on user's choice.
     * @param choice user's choice of music
     */
    @Override
    public void setMusic(int choice) {
        MusicPlayer.playMusic(choice, getApplicationContext());
    }

    /**
     * Go to Main Menu.
     */
    @Override
    public void toMainMenu() {
        Intent receivedIntent = getIntent();
        String username = receivedIntent.getStringExtra("username");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);

        startActivity(intent);
    }

    /**
     * Set the next level button's OnClickListener.
     */
    @Override
    public void setNextLevelButton() {
        findViewById(R.id.shop_score_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNextLevelButtonClicked();
            }
        });
    }

    @Override
    public void toNextLevel(int resources, int timeSpent, double gambleScore, int exp) {
        Intent receivedIntent = getIntent();
        String username = receivedIntent.getStringExtra("username");
        UserDatabaseHelper db = new UserDatabaseHelper(this);
        User user = db.getUser(username);

        user.setResources(resources);
        user.addSecondsSpent(timeSpent);
        db.updateUser(user);

        Intent intent = new Intent(ShopActivity.this,
                ShopExitActivity.class);

        intent.putExtra("username", username);

        intent.putExtra(IntentKeys.GAMBLE_SCORE_KEY, gambleScore);

        intent.putExtra(IntentKeys.EXP_GAINED_KEY, exp);

        intent.putExtra(IntentKeys.TIME_SPENT_KEY, timeSpent);

        startActivity(intent);
    }

    /**
     * Don't do anything on back button pressed; User should not be able to go back screen
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * Check if the tab of shop and the tab of user's inventory is at the same one
     *
     * @return True if inventory's tab is the same as the shop's tab (Ex: Weapon and Weapon).
     * False otherwise
     */
    private boolean isAtSameTab() {
        int userTabPosition = ((TabLayout) findViewById(R.id.inventory_tab)).
                getSelectedTabPosition();
        int shopTabPosition = ((TabLayout) findViewById(R.id.shop_tab)).getSelectedTabPosition();

        return (userTabPosition == shopTabPosition);
    }
}
