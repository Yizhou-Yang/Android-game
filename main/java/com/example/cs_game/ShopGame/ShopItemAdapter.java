package com.example.cs_game.ShopGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Item;
import com.example.cs_game.Utilities.Displayable;
import com.example.cs_game.Utilities.DisplayableAdapter;
import com.example.cs_game.R;
import com.example.cs_game.Utilities.ImageGetter;

import java.util.Locale;

class ShopItemAdapter<T extends Item> extends DisplayableAdapter<T> {

    private static class ShopViewHolder extends DisplayableAdapter.ViewHolder {

        TextView name;
        TextView price;
        ImageView image;

        ShopViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView, listener);

            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_image);
        }


        @Override
        public void setItem(Displayable item) {
            name.setText(item.getName());
            image.setImageResource(ImageGetter.getImageId(item.getName()));

            // Casting here is safe because this adapter is only for Items,
            // which implement Displayable.
            setItemPrice((Item) item);
        }

        private void setItemPrice(Item item) {
            price.setText(String.format(Locale.CANADA, "%d Gold", item.getPrice()));
        }
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(itemView, getListener());
    }
}
