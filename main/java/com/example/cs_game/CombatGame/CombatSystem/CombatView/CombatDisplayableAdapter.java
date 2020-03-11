package com.example.cs_game.CombatGame.CombatSystem.CombatView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cs_game.Utilities.Displayable;
import com.example.cs_game.R;
import com.example.cs_game.Utilities.DisplayableAdapter;
import com.example.cs_game.ShopGame.OnItemClickListener;

public class CombatDisplayableAdapter<T extends Displayable> extends DisplayableAdapter<T> {

    static class CombatViewHolder extends DisplayableAdapter.ViewHolder {

        TextView name;
        TextView description;

        CombatViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView, listener);

            name = itemView.findViewById(R.id.item_name);
            description = itemView.findViewById(R.id.item_description);
        }

        @Override
        public void setItem(Displayable item) {
            String nameString;
            String descriptionString;
            if (item == null) {
                nameString = "Empty";
                descriptionString = "Empty";
            } else {
                nameString = item.getName();
                descriptionString = item.getDescription();
            }
            name.setText(nameString);
            description.setText(descriptionString);
        }
    }

    @NonNull
    @Override
    public CombatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.combat_item, parent, false);

        return new CombatViewHolder(itemView, getListener());
    }
}
