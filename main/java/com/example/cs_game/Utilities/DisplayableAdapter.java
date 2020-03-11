package com.example.cs_game.Utilities;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_game.ShopGame.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class DisplayableAdapter<T extends Displayable> extends
        RecyclerView.Adapter<DisplayableAdapter.ViewHolder>
        implements OnItemClickListener {

    // The Items this adapter is holding
    private List<T> items = new ArrayList<>();

    // Listener for when items are clicked
    private OnItemClickListener listener;

    protected OnItemClickListener getListener() {
        return listener;
    }

    /**
     * Update the view that holder is holding with shop_item at position
     *
     * @param holder   ViewHolder holding view that inflated shop_item_item.xml
     * @param position the position at which the shop_item ViewHolder's view is trying to display
     */
    @Override
    public void onBindViewHolder(@NonNull DisplayableAdapter.ViewHolder holder, int position) {
        T item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void addAllItems(List<? extends T> itemsList) {
        items.addAll(itemsList);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public void removeAllItems() {
        items.clear();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    /**
     * Set the listener for when ViewHolder holding a View of Item is clicked
     *
     * @param listener implemented OnItemClickListener interface
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * If listener was set i.e. not null, then call its onItemClick
     *
     * @param holder   ViewHolder of the view clicked
     * @param v        the View clicked
     * @param position the position of the ViewHolder (Item) in the RecyclerView (list)
     */
    @Override
    public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
        if (listener != null) listener.onItemClick(holder, v, position);
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        /**
         * Set the information that ViewHolder's View is displaying with the given shop_item
         *
         * @param item the Item object that the View will display (with shop_item_item.xml layout)
         */
        public abstract void setItem(Displayable item);
    }
}
