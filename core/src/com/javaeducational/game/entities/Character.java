package com.javaeducational.game.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private int tileWidth; // Assuming tile width in pixels
    private int tileHeight; // Assuming tile height in pixels
    private int mapWidthInTiles; // Assuming map width in tiles
    private int mapHeightInTiles; // Assuming map height in tiles
    private Rectangle bounds; // Get bounds for collisions


    // Constructor
    public Character(String texturePath, int x, int y, int width, int height, int speed, String name,
                     TiledMapTileLayer solidLayer, int tileWidth, int tileHeight,
                     int mapWidthInTiles, int mapHeightInTiles) {
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

        // Initialize variables related to map and collision
        this.solidLayer = solidLayer;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidthInTiles = mapWidthInTiles;
        this.mapHeightInTiles = mapHeightInTiles;
        this.bounds = new Rectangle(x, y, width, height);
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        // Render the name above the character
        font.draw(batch, name, x, y + height + 20); // Adjust 20 according to your preference
    }

    // Method to handle user input for character movement
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

        // Check collision with map boundaries and solid tiles
        if (newX >= 0 && newX + width <= mapWidthInTiles * tileWidth &&
                newY >= 0 && newY + height <= mapHeightInTiles * tileHeight &&
                !collidesWithSolidTiles(newX, newY)) {
            // Update character position if no collision detected
            x = newX;
            y = newY;
        }
        // updates bounds for player
        this.bounds.setPosition(x, y);
    }

    // checks collisions
    public Rectangle getBounds() {
        return bounds;
    }

    // Method to check collision with solid tiles
    private boolean collidesWithSolidTiles(float newX, float newY) {
        // Convert character's potential new position to tile coordinates
        int tileX = (int) (newX / tileWidth);
        int tileY = (int) (newY / tileHeight);

        // Check if the tile is solid
        return solidLayer.getCell(tileX, tileY) != null;
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

    public void takeBus(float x, float y) {
        this.x = x;
        this.y = y;
        bounds.setPosition(x, y);
    }
}
