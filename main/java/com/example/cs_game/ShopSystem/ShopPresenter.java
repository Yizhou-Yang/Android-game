package com.example.cs_game.ShopGame;

import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CombatSystemManager.GameManager;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.GenerationSystem.BasicGenerator;
import com.example.cs_game.GenerationSystem.StartupGenerator;
import com.example.cs_game.TimeManager;
import com.example.cs_game.Utilities.Validator;

import java.util.List;

class ShopPresenter {

    // The view this presenter controls
    private ShopView shopView;

    // The model this presenter interacts with
    private User user;

    private ShopItemsManager shopItemsManager = new ShopItemsManager();
    private TimeManager timeManager = new TimeManager();

    private ItemPurchaseManager purchaseManager = new ItemPurchaseManager();
    private ItemSellingManager sellingManager = new ItemSellingManager();

    private String hiddenPattern = "22100122";
    private StringBuilder userPatternAttempt = new StringBuilder();

    ShopPresenter(ShopView shopView, User user) {
        StartupGenerator startupGenerator = BasicGenerator.buildGenerator();
        if (GameManager.getPlayer() == null) {
            GameManager.setPlayer(startupGenerator.generatePlayer());
        }
        this.shopView = shopView;
        this.user = user;
        GameManager.getPlayer().setResources(user.getResources());
    }

    /**
     * Called when the presenter is created (which is when View is created)
     */
    void onCreate() {
        setShop();

        timeManager.beginCount();
    }

    /**
     * Initialize the shop
     */
    private void setShop() {
        shopView.updatePlayerResourcesDisplay(GameManager.getPlayer().getResources());

        shopView.setShopItemOnClickListener();
        shopView.setInventoryItemOnClickListener();

        shopView.setShopItemList(shopItemsManager.getWeapons());
        shopView.setInventoryItemList(GameManager.getPlayer().getUnequippedWeapons());

        shopView.setShopTabs();
        shopView.setInventoryTabs();

        shopView.setShopTabsOnClickListener();
        shopView.setInventoryTabsOnClickListener();

        shopView.setNextLevelButton();

        shopView.setPauseButton();

        int choice = user.getAudioCustomization();
        shopView.setMusic(choice);
    }

    /**
     * When user attempts to buy the item, check if they have enough resources and
     * update shop display. Should be called when purchaseManager has an item.
     * Reset purchaseManager regardless of success or fail of user buying the item.
     * Reset Item's price to original price regardless of success or fail of user buying the item.
     */
    void onBuy() {
        if (purchaseManager.buyItem()) {
            // Need an extra call to show the item
            shopView.showBoughtItem(purchaseManager.getCurrentItem());
            shopView.updatePlayerResourcesDisplay(GameManager.getPlayer().getResources());
        }

        purchaseManager.revertPrice();
        purchaseManager.reset();
    }

    /**
     * Called when gamble option is chosen. Initiate beginning of gamble and show gamble dialog.
     */
    void onGambleOption() {
        purchaseManager.beginGamble();

        shopView.updatePlayerResourcesDisplay(GameManager.getPlayer().getResources());
        shopView.showGambleDialog(purchaseManager.getCurrentItem().getPrice(),
                purchaseManager.getGambleAnswer());
    }

    /**
     * Called when user cancels buying item.
     * Revert to original price and reset the purchase manager.
     */
    void onCancel() {
        purchaseManager.revertPrice();
        purchaseManager.reset();
    }

    /**
     * Called when user performs a gamble with given input. If input is valid, process gamble and
     * go back to ShopItemOptionsDialog with updated price. Otherwise, notify it is invalid.
     *
     * @param input the guess the user makes; It's valid iff it is numeric;
     */
    void onGamble(String input) {
        if (Validator.isNumeric(input)) {
            purchaseManager.assessGamble(Integer.parseInt(input));
            String description = purchaseManager.getCurrentItem().getDescription();
            int price = purchaseManager.getCurrentItem().getPrice();
            shopView.showShopItemOptionsDialog(description, price, purchaseManager.getGambleCost());
        } else {
            shopView.showPopUp("Invalid input");
        }
    }

    /**
     * Sell the currently focused item and let player gain the price of item
     */
    void onSell() {
        sellingManager.sellItem();

        shopView.onItemSold(sellingManager.getCurrentItem());
        shopView.updatePlayerResourcesDisplay(GameManager.getPlayer().getResources());

        sellingManager.reset();
    }

    /**
     * When a shop tab is pressed, show appropriate list of items depending on the tab.
     * Then update the hidden patter attempt.
     *
     * @param tabPosition integer value indicating which tab was pressed
     */
    void onShopTabSelected(int tabPosition) {
        List<? extends Item> items;
        switch (tabPosition) {
            case ShopView.WEAPON_TAB:
                items = shopItemsManager.getWeapons();
                break;
            case ShopView.ARMOR_TAB:
                items = shopItemsManager.getArmors();
                break;
            default:
                items = shopItemsManager.getConsumables();
        }

        shopView.onShopTabChange(items);

        updateHiddenPatternAttempt(tabPosition);
    }

    /**
     * When an inventory tab is pressed, show appropriate list of items depending on the tab
     * Then update the hidden patter attempt.
     *
     * @param tabPosition integer value indicating which tab was pressed
     */
    void onInventoryTabSelected(int tabPosition) {
        List<? extends Item> items;
        switch (tabPosition) {
            case ShopView.WEAPON_TAB:
                items = GameManager.getPlayer().getUnequippedWeapons();
                break;
            case ShopView.ARMOR_TAB:
                items = GameManager.getPlayer().getUnequippedArmors();
                break;
            default:
                items = GameManager.getPlayer().getConsumables();
        }

        shopView.onInventoryTabChange(items);

        updateHiddenPatternAttempt(tabPosition);
    }

    /**
     * Called when shop item is clicked. Set the item as purchaseManager's focus and display the
     * Shop Item options dialog.
     *
     * @param item the Item that was clicked
     */
    void onShopItemClicked(Item item) {
        purchaseManager.setCurrentItem(item);
        String description = purchaseManager.getCurrentItem().getDescription();
        int price = purchaseManager.getCurrentItem().getPrice();
        shopView.showShopItemOptionsDialog(description, price, purchaseManager.getGambleCost());
    }

    /**
     * Called when inventory item is clicked. Show selling item dialog
     *
     * @param item the Item that was clicked
     */
    void onInventoryItemClicked(Item item) {
        sellingManager.setCurrentItem(item);
        String description = sellingManager.getCurrentItem().getDescription();
        int price = sellingManager.getCurrentItem().getPrice();
        shopView.showSellItemDialog(description, price);
    }

    /**
     * Proceed to show pause dialog
     */
    void onPauseButtonClicked() {
        CharSequence[] options = {"Main Menu", "Save"};
        shopView.showPauseDialog(options);
    }

    /**
     * Proceed appropriately depending on the option chose, which is represented by parameter which
     *
     * @param which integer indicator of which option was chosen
     */
    void onPauseDialogOptionClicked(int which) {
        if (which == shopView.MAIN_MENU_OPTION) shopView.toMainMenu();
        else if (which == shopView.SAVE_OPTION) {
            // TODO: implement later when save/load is implemented
        }
    }

    /**
     * Fetch all data collected in this stage and send the player to next level.
     */
    void onNextLevelButtonClicked() {
        int timeSpent = timeManager.getDifference();
        double gambleScore = purchaseManager.getGambleScore();
        int exp = 3 * (int) gambleScore;

        GameManager.getPlayer().gainExp(exp);

        toNextLevel(timeSpent, gambleScore, exp);
    }

    /**
     * Updates current user's attempt for hidden pattern, and checks if it matches; if it does,
     * then go to next level with bonus resources.
     *
     * @param input the input user chose
     */
    private void updateHiddenPatternAttempt(int input) {
        userPatternAttempt.append(input);
        System.out.println(userPatternAttempt.toString());
        if (userPatternAttempt.toString().contains(hiddenPattern)) {
            int timeSpent = timeManager.getDifference();
            double gambleScore = 100;
            int exp = 300;
            toNextLevel(timeSpent, gambleScore, exp);
        } else if (userPatternAttempt.length() > hiddenPattern.length() * 2) {
            userPatternAttempt.delete(0, userPatternAttempt.length());
        }
    }

    /**
     * Go to next level.
     *
     * @param timeSpent   time spent on this stage in seconds
     * @param gambleScore the total score of gamble user has done during Shop stage
     * @param exp         amount of exp user received in this stage
     */
    private void toNextLevel(int timeSpent, double gambleScore, int exp) {
        shopView.toNextLevel(GameManager.getPlayer().getResources(), timeSpent, gambleScore, exp);
    }
}
