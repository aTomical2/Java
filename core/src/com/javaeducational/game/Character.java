package com.javaeducational.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Character {
    // Texture representing the character
    private Texture texture;

    // Position and dimensions of the character
    private float x;
    private float y;
    private float width;
    private float height;

    // Speed of the character
    private float speed;

    private String name;
    private BitmapFont font;
    private int gemCount; // Total number of gems collected
    private float carbonFootprint; // Total carbon footprint
    private HashMap<String, Float> transportCarbonValues; // Carbon values for different transports

    // Constructor
    public Character(String texturePath, int x, int y, int width, int height, int speed, String name) {
        // Load the character texture using the provided texturePath
        texture = new Texture(texturePath);
        this.name = name;
        font = new BitmapFont();
    
        // Set initial position, dimensions, and speed
        this.x = x;
        this.y = y;
        this.width = width / 2;
        this.height = height / 2;
        this.speed = speed;
        gemCount = 0;
        carbonFootprint = 0.0f;
        
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        // Render the name above the character
        font.draw(batch, name, x, y + height + 20); // Adjust 20 according to your preference        
    }

    // Method to handle user input for character movement
    public void handleInput() {
    // Get the time passed since the last frame
    float delta = Gdx.graphics.getDeltaTime();

    // Move the character based on user input
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        y += speed * delta;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        y -= speed * delta;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        x -= speed * delta;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        x += speed * delta;
    }
}


    // Method to dispose of resources when they are no longer needed
    public void dispose() {
        texture.dispose();
    }

    // Getter methods for position and dimensions
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    
        // Method to be called when a gem is collected
        public void collectGem(Gem gem) {
            gemCount++;
            // Optionally, perform any other action required when a gem is collected
        }
    
        // Method to update carbon footprint based on transport mode
        public void updateCarbonFootprint(String transportMode) {
            carbonFootprint += transportCarbonValues.getOrDefault(transportMode, 0.0f);
        }
    
        // Method to calculate the score
        public int calculateScore() {
            return gemCount * 100 - (int)carbonFootprint + (int)speed * 10;
        }
    
        // Existing methods...
    
        // Getters for gem count and carbon footprint
        public int getGemCount() {
            return gemCount;
        }
    
        public float getCarbonFootprint() {
            return carbonFootprint;
        }
}
