package com.example.cs_game.ShopGame;

import android.view.View;

import com.example.cs_game.DisplayableAdapter;

public interface OnItemClickListener {
    void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position);
}
