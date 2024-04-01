package com.javaeducational.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    // Constructor
    public Character(String imagePath, int x, int y, int width, int height, int speed) {
        // Load the character texture using the provided imagePath
        texture = new Texture(imagePath);
    
        // Set initial position, dimensions, and speed
        this.x = x;
        this.y = y;
        this.width = width / 2;
        this.height = height / 2;
        this.speed = speed;
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    // Method to handle user input for character movement
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
}
