package com.javaeducational.game.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameResultManager {

    private static class LevelResults {
        private int level;
        private int score;
        private int gemsCollected;
        private int carbonFootprint;

        public LevelResults(int level, int score, int gemsCollected, int carbonFootprint) {
            this.level = level;
            this.score = score;
            this.gemsCollected = gemsCollected;
            this.carbonFootprint = carbonFootprint;
        }
    }

    public static void saveLevelResults(int level, int score, int gemsCollected, int carbonFootprint, String filename) {
        LevelResults levelResults = new LevelResults(level, score, gemsCollected, carbonFootprint);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Level " + levelResults.level + ":\n");
            writer.write("Score: " + levelResults.score + "\n");
            writer.write("Gems Collected: " + levelResults.gemsCollected + "\n");
            writer.write("Carbon Footprint: " + levelResults.carbonFootprint + "\n");
            writer.write("\n"); // Add a newline to separate the results for each level
            if (level == 2) {
                writer.write("=================\n"); // Add a line to separate levels
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}