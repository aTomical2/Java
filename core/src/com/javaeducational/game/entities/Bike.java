package com.javaeducational.game.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Bike {
    private Texture texture; // Texture for the bike
    private float x, y; // Position of the bike
    public float width;
    public float height;// Dimensions of the bike

    private boolean onBike;

    private float speed;
    private TiledMapTileLayer bikeStopsLayer; // Layer for bike stops
    private TiledMapTileLayer roadLayer;      // Layer for roads
    private TiledMapTileLayer bikepathLayer;  // Layer for bike paths

    private int tileWidth;            // Assuming tile width in pixels
    private int tileHeight;           // Assuming tile height in pixels
    private int mapWidthInTiles;      // Assuming map width in tiles
    private int mapHeightInTiles;     // Assuming map height in tiles
    private Rectangle bounds;         // Get bounds for collisions
    private MapObjects bikepaths;
    private MapLayer bikepathslayer;


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


    public void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();

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
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }
}
