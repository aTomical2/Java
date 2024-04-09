package com.javaeducational.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Car {
    // Texture representing the car
    private Texture texture;

    // Position and dimensions of the car
    private float x;
    private float y;
    private float width;
    private float height;

    // Speed of the car
    private float speed;

    private String name;
    private BitmapFont font;

    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private float tileWidth; // Assuming tile width in pixels
    private float tileHeight; // Assuming tile height in pixels
    private float mapWidthInTiles; // Assuming map width in tiles
    private float mapHeightInTiles; // Assuming map height in tiles
    private Rectangle bounds; // Get bounds for collisions
    private boolean movingTowardsEnd;
    Vector2 velocity;
    Vector2 startPoint;
    Vector2 endPoint;
    Vector2 position;

    // Add a new texture representing the car
    private Texture carTexture;

    // Constructor
    public Car(float x, float y, float width, float height, float speed,
               TiledMapTileLayer solidLayer, float tileWidth, float tileHeight,
               float mapWidthInTiles, float mapHeightInTiles, Vector2 startPoint, Vector2 endPoint) {
        try {
            // Load the car texture using the provided texturePath
            carTexture = new Texture("Car/UniqueCarTexture.png"); // Load a different texture for the car
        } catch (Exception e) {
            // If an exception occurs (e.g., file not found), handle it gracefully
            System.err.println("Failed to load car texture: " + e.getMessage());
            // Load a default texture or simply log the error and continue
            carTexture = null; // Assigning null texture to indicate failure
        }

        // Set initial position, dimensions, and speed
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;

        // Initialize variables related to map and collision
        this.solidLayer = solidLayer;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidthInTiles = mapWidthInTiles;
        this.mapHeightInTiles = mapHeightInTiles;
        this.bounds = new Rectangle(x, y, width, height);
        this.movingTowardsEnd = true;
        this.velocity = new Vector2(1, 1);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.position = startPoint.cpy(); // use cpy to avoid reference issues
    }

    // Method to render the car
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void update(float dt) {
        if (movingTowardsEnd) {
            position.lerp(endPoint, 0.01f * speed * dt);
            if (position.epsilonEquals(endPoint, 0.1f)) {
                movingTowardsEnd = false;
            }
        } else {
            position.lerp(startPoint, 0.01f * speed * dt);
            if (position.epsilonEquals(startPoint, 0.1f)) {
                movingTowardsEnd = true;
            }
        }
        x = position.x;
        y = position.y;
    }

    // checks collisions
    public Rectangle getBounds() {
        return bounds;
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