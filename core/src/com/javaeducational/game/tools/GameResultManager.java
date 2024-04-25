package com.javaeducational.game.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameResultManager {
    private static class GameResults {
        private int score;
        private int gemsCollected;
        private int carbonFootprint;

        public GameResults(int score, int gemsCollected, int carbonFootprint) {
            this.score = score;
            this.gemsCollected = gemsCollected;
            this.carbonFootprint = carbonFootprint;
        }
    }

    public static void saveGameResults(int score, int gemsCollected, int carbonFootprint, String filename) {
        GameResults gameResults = new GameResults(score, gemsCollected, carbonFootprint);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Score: " + gameResults.score + "\n");
            writer.write("Gems Collected: " + gameResults.gemsCollected + "\n");
            writer.write("Carbon Footprint: " + gameResults.carbonFootprint + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
