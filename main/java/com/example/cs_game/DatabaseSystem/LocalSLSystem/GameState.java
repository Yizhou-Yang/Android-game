package com.example.cs_game.DatabaseSystem.LocalSLSystem;

import com.example.cs_game.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;

import java.util.ArrayList;
import java.util.List;

class GameState {
    private User user;
    private Player player;
    private List<Item> items;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
