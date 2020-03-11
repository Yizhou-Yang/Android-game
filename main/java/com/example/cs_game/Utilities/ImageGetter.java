package com.example.cs_game.Utilities;

import com.example.cs_game.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Class providing static methods to fetch the image id corresponding to class names
 */
public class ImageGetter {
    // Map which has string (names of objects) to integer IDs that
    // represent an image (for the objects)
    private static Map<String, Integer> imageIdMap;

    /**
     * Initialize the map.
     */
    public static void initialize() {
        if (imageIdMap == null) {
            imageIdMap = new HashMap<>();

            // Can simplify this in far future with database maybe
            imageIdMap.put("Weak Health Potion", R.drawable.red_potion);
            imageIdMap.put("Strong Health Potion", R.drawable.red_potion);
            imageIdMap.put("Light", R.drawable.armor);
            imageIdMap.put("Medium", R.drawable.armor);
            imageIdMap.put("Heavy", R.drawable.armor);
            imageIdMap.put("Super Heavy", R.drawable.armor);
            imageIdMap.put("Axe", R.drawable.sword);
            imageIdMap.put("Sword", R.drawable.sword);
            imageIdMap.put("Spear", R.drawable.sword);
            imageIdMap.put("Pistol", R.drawable.sword);
            imageIdMap.put("Rifle", R.drawable.sword);

        }
    }

    /**
     * Get the image ID for a given key, which should be the name of the item
     * @param key key for the map
     * @return the value corresponding to the key if key is in the map; otherwise, default image
     */
    public static int getImageId(String key) {
        if (imageIdMap != null && imageIdMap.containsKey(key)) {
             return imageIdMap.get(key);
        } else return R.drawable.knight;
    }
}
