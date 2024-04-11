package com.javaeducational.game.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Bike {
    private Texture texture; // Texture for the bike
    private float x, y; // Position of the bike
    private float width, height; // Dimensions of the bike

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


    public Bike(String texturePath, float x, float y, float width, float height) {
        this.texture = new Texture("Bike/bike.png");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();

        // Store the character's potential new position
        float newX = x;
        float newY = y;

        // Move the character based on user input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * delta;
        }
    }
}
