package com.javaeducational.game.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Bike {
    private Texture texture; // Texture for the bike
    private float x, y; // Position of the bike
    public float width;
    public float height;// Dimensions of the bike
    private boolean onBike;
    private float speed;

    public Bike(String texturePath, float x, float y, float width, float height) {
        this.texture = new Texture("Bike/bike.png");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = 1000;
        this.onBike = false;
    }
    public Texture getTexture() {
        return texture;
    }

    public boolean isOnBike() {
        return this.onBike;  }
    public float getbikewidth() {
        return width;
    }
    public float getbikeheight(){
        return height;
    }
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }
}
