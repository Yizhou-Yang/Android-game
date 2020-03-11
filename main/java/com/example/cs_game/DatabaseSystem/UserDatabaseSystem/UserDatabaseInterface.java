package com.example.cs_game.DatabaseSystem.UserDatabaseSystem;

import android.database.Cursor;

import java.util.List;

public interface UserDatabaseInterface {
    String DATABASE_NAME = "UserDatabase";
    String TABLE_NAME = "UserTable";
    String COL_1 = "Username";
    String COL_2 = "Resources";
    String COL_3 = "SecondsSpent";
    String COL_4 = "Experience";
    String COL_5 = "Level";
    String COL_6 = "AudioCustomization";
    String COL_7 = "SpriteCustomization";
    String COL_8 = "DifficultyCustomization";

    User getUser(String username);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean createUser(String name);
    User decodeSingleUserCursor(Cursor result);
    List<User> decodeUserListCursor(Cursor result);
    void close();

}
