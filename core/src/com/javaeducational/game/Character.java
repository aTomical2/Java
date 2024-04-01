package com.javaeducational.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Character {
    // Texture representing the character
    private Texture texture;

    // Position of the character
    private float x;
    private float y;

    // Speed of the character
    private float speed;

    // Constructor
    public Character(String imagePath, float x, float y, float speed) {
        // Load the character texture using the provided imagePath
        texture = new Texture(imagePath);

        // Set initial position and speed
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
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
}
