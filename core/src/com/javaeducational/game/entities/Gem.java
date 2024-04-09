package com.javaeducational.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gem {
    private Texture texture; // Texture for the gem
    private float x, y; // Position of the gem
    private float width, height; // Dimensions of the gem
    private int value; // The value of the gem

    public Gem(String texturePath, float x, float y, float width, float height) {
        this.texture = new Texture("assets/Map/blueheart.png");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }

    // Getters
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

    public int getValue() {
        return value;
    }
}
