package com.example.cs_game.ShopGame.EquippingStage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Equipment;
import com.example.cs_game.CombatGame.CombatSystem.CombatView.CombatActivity;
import com.example.cs_game.CombatGame.CombatSystem.CombatView.CombatDisplayableAdapter;
import com.example.cs_game.CombatGame.CombatSystem.CombatView.OnItemClickedInFragmentListener;
import com.example.cs_game.Utilities.DisplayableAdapter;
import com.example.cs_game.Utilities.ItemListFragment;
import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.R;
import com.example.cs_game.ShopGame.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EquipmentActivity extends AppCompatActivity implements EquipView {

    private DisplayableAdapter<Equipment> equipmentAdapter = new CombatDisplayableAdapter<>();

    // fragment for showing equipped items
    private ItemListFragment<Equipment> fragment =
            new ItemListFragment<>(new ArrayList<Equipment>());

    EquipmentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        setRecyclerView();
        setEquippedItemFragment();

        presenter = new EquipmentPresenter(this);
        presenter.onCreate();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.equipment_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipmentAdapter);
    }

    private void setEquippedItemFragment() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.equipped_items_container, fragment).commit();
    }

    /**
     * Configure the initial tabs
     */
    @Override
    public void setTabs() {
        TabLayout tabs = findViewById(R.id.equipment_options);
        tabs.addTab(tabs.newTab().setText("Weapon"));
        tabs.addTab(tabs.newTab().setText("Armor"));

        // Begin at the Weapon tab
        TabLayout.Tab tab = tabs.getTabAt(EquipView.WEAPON_TAB);
        if (tab != null) {
            tab.select();
        }

    }

    /**
     * Show different type of items depending on the tab chosen.
     */
    @Override
    public void setOnTabClickedListener() {
        TabLayout tabs = findViewById(R.id.equipment_options);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.updateDisplay(tab.getPosition());
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
     * Set the listener for whenever equipped items are clicked
     */
    @Override
    public void setOnEquippedItemClickListener() {
        fragment.setOnItemClickedListener(new OnItemClickedInFragmentListener<Equipment>() {
            @Override
            public void onItemClick(Equipment equipment) {
                presenter.onEquippedItemClick(equipment);
            }
        });
    }

    /**
     * Set dialogs to show once items are clicked.
     */
    @Override
    public void setOnUnequippedItemClickListener() {
        equipmentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
                Equipment equipment = equipmentAdapter.getItem(position);
                presenter.onUnequippedItemClicked(equipment);
            }
        });
    }

    /**
     * Display the given list of equipments
     *
     * @param equipments the list of equipments to display
     */
    @Override
    public void showEquippedItems(List<? extends Equipment> equipments) {
        fragment.replaceItems(equipments);
    }

    /**
     * Show the list of given unequipped equipments
     *
     * @param equipments list of unequipped equipments to display
     */
    @Override
    public void showUnequippedItems(List<? extends Equipment> equipments) {
        equipmentAdapter.removeAllItems();
        equipmentAdapter.addAllItems(equipments);
        equipmentAdapter.notifyDataSetChanged();
    }

    /**
     * Show a dialog of equipment slots for user to choose
     *
     * @param options   the list of options the dialog will provide
     * @param equipment the Weapon object that was chosen by the user to onEquipLocationClicked
     */
    @Override
    public void showDialogOnItemClick(CharSequence[] options, final Equipment equipment) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onEquipLocationClicked(which, equipment);
            }
        };

        FrontEndUtility.listDialog("Equip", options, listener, this);


    }

    /**
     * Configure the button so that it sends to the next activity, CombatActivity, once pressed.
     */
    @Override
    public void setNextLevelButtonOnClick() {
        findViewById(R.id.next_level_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipmentActivity.this,
                        CombatActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));

                startActivity(intent);
            }
        });
    }

    /**
     * Update display of items at tabPosition
     *
     * @param tabPosition the position of the currently chosen tab, which tells, which items to show
     */
    @Override
    public void updateDisplay(int tabPosition) {
        presenter.updateDisplay(tabPosition);
    }
}
