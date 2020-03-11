package com.example.cs_game.ShopGame.TradeManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

// Manages the gamble mechanism
class GambleManager {
    // sale percentage; 0.3 <= salesRatio
    private double salesRatio = 1.0;
    private int correctNum;
    private int maximum;
    private Random random = new Random();

    private ArrayList<Double> gambleScores = new ArrayList<>();

    void calculateSalesRatio(int guess) {
        double inaccuracy = Math.abs(correctNum - guess);
        salesRatio  = 1.5 * inaccuracy / maximum + 0.3;

        gambleScores.add(1 / salesRatio);

    }

    void setNewMaximum(int maximum) {
        this.maximum = maximum;
        correctNum = random.nextInt(maximum);
    }

    int getNewPrice(int oldPrice) {
        int newPrice = (int) (oldPrice * salesRatio);

        salesRatio = 1.0;

        return newPrice;
    }

    int getCorrectNum() {
        return correctNum;
    }

    double getGambleScore() {
        double total = 0;
        gambleScores.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
        for (Double num : gambleScores) {
            total += num;
        }

        double attempts = gambleScores.size();

        return Math.min(((6 / Math.PI) * Math.atan(attempts / 2)) * 10 * total, 500);
    }

    int getAttempts() {
        return gambleScores.size();
    }

    static int getGambleCost(int itemPrice) {
        return (int) Math.ceil(itemPrice * 0.1);
    }
}
