package com.example.cs_game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CombatSystem.CombatDisplayableAdapter;
import com.example.cs_game.CombatSystem.OnItemClickedInFragmentListener;
import com.example.cs_game.ShopGame.OnItemClickListener;

import java.util.List;

public class ItemListFragment<T extends Item> extends Fragment {

    private List<T> items;
    private DisplayableAdapter<T> displayableAdapter;
    private OnItemClickedInFragmentListener<T> listener;

    public ItemListFragment(List<T> array) {
        this.items = array;
        displayableAdapter = new CombatDisplayableAdapter<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.item_list, container,
                false);
        setRecyclerView(rootView);

        return rootView;
    }

    public void setOnItemClickedListener(OnItemClickedInFragmentListener<T> listener) {
        this.listener = listener;
    }

    /**
     * Create Adapter and LayoutManager to configure RecyclerView, which is in rootView
     *
     * @param rootView The ViewGroup which contains all items in the fragment, and hence, the
     *                 RecyclerView as well
     */
    private void setRecyclerView(ViewGroup rootView) {
        RecyclerView itemList = rootView.findViewById(R.id.item_list);
        displayableAdapter.addAllItems(items);

        displayableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
                T item = displayableAdapter.getItem(position);
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });

        itemList.setAdapter(displayableAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        itemList.setLayoutManager(layoutManager);
    }

    /**
     * Replace the original items with newList
     *
     * @param newList the newList to be shown in the RecyclerView
     */
    public void replaceItems(List<? extends T> newList) {
        displayableAdapter.removeAllItems();
        displayableAdapter.addAllItems(newList);
        displayableAdapter.notifyDataSetChanged();
    }
}
