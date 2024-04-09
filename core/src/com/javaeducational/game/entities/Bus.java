package com.javaeducational.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bus {
    // Texture representing the bus
    private TextureRegion[][] busSpriteSheet;
    private Animation<TextureRegion>[] busRolls;
    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;
    private float stateTime;


    // Position and dimensions of the character
    private float x;
    private float y;

    // Speed of the character
    private final int BUS_SPEED = 200;
    private final int BUS_WIDTH = 150;
    private final int BUS_HEIGHT = 150;
    private final int BUS_WIDTH_PIXEL = 210;
    private final int BUS_HEIGHT_PIXEL = 210;

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

    // Constructor
    public Bus(float x, float y, TiledMapTileLayer solidLayer, float tileWidth, float tileHeight,
                     float mapWidthInTiles, float mapHeightInTiles, Vector2 startPoint, Vector2 endPoint) {
        // Load the character texture using the provided texturePath
        // Set initial position, dimensions, and speed
        this.x = x;
        this.y = y;

        // Initialize variables related to map and collision
        this.solidLayer = solidLayer;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidthInTiles = mapWidthInTiles;
        this.mapHeightInTiles = mapHeightInTiles;
        this.bounds = new Rectangle(x, y, BUS_WIDTH, BUS_HEIGHT);
        this.movingTowardsEnd = true;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.position = startPoint.cpy(); //use cpy to avoid reference issues
        stateTime = 0f;
        busRolls = new Animation[49];
        busSpriteSheet= TextureRegion.split(new Texture("Bus/BUS_CLEAN_ALLD0000-sheet.png"), BUS_WIDTH_PIXEL, BUS_HEIGHT_PIXEL);

        for (int i = 0; i < 7; i++) {
            for (int j = 1; j <= 7; j++) {
                busRolls[(j + (7 * i)) - 1] = new Animation<TextureRegion>(0.5f, busSpriteSheet[i][j - 1]);
            }
        }
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
    batch.draw(currentFrame, x - (BUS_WIDTH / 2), y - (BUS_HEIGHT / 2), BUS_WIDTH, BUS_HEIGHT);
    }

    public void update(float deltaTime) {
        Vector2 target = movingTowardsEnd ? endPoint : startPoint;
        Vector2 direction = new Vector2(target.x - position.x, target.y - position.y).nor();
        System.out.println(direction.x + " " + direction.y);
        velocity = new Vector2(direction.x * BUS_SPEED, direction.y * BUS_SPEED);

        position.add(velocity.scl(deltaTime));

        x = position.x;
        y = position.y;

        float distance = position.dst(target);
        if (distance < BUS_SPEED * deltaTime) {
            movingTowardsEnd = !movingTowardsEnd;
            position.set(target);
        }

        bounds.setPosition(x, y);

        updateDirection(velocity);
        currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        stateTime += deltaTime;
    }

    // checks collisions
    public Rectangle getBounds() {
        return bounds;
    }

    // Updates directions of bus by keeping track of x and y
    public void updateDirection(Vector2 direction) {
        if (direction.x > 0) {
            // Moving right
            currentAnimation = busRolls[0];
        } else if (direction.x < 0) {
            // Moving left
            currentAnimation = busRolls[24];
        }
        // Add conditions for moving up and down...
    }

    // Method to dispose of resources when they are no longer needed
    public void dispose() {
    }

    // Getter methods for position and dimensions
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return BUS_WIDTH;
    }

    public float getHeight() {
        return BUS_HEIGHT;
    }
}
