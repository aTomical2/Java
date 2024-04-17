package com.javaeducational.game.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.javaeducational.game.screens.GameMapScreen;

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

    private boolean onBike;
    private Bike bike;


    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private int tileWidth; // Assuming tile width in pixels
    private int tileHeight; // Assuming tile height in pixels
    private int mapWidthInTiles; // Assuming map width in tiles
    private int mapHeightInTiles; // Assuming map height in tiles
    private Rectangle bounds; // Get bounds for collisions
    private MapObjects bikepaths;
    private MapLayer bikepathslayer;
    private GameMapScreen gameMapScreen;

    public boolean inBikeStandCollision = false;


    // Constructor
    public Character(GameMapScreen gameMapScreen, String texturePath, int x, int y, int width, int height, int speed, String name,
                     TiledMapTileLayer solidLayer, int tileWidth, int tileHeight,
                     int mapWidthInTiles, int mapHeightInTiles) {
        // Load the character texture using the provided texturePath
        texture = new Texture(texturePath);
        this.name = name;
        font = new BitmapFont();
        this.gameMapScreen = gameMapScreen;

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
        this.onBike = false;
        bikepathslayer = GameMapScreen.map.getLayers().get("bikepaths");
        bikepaths = bikepathslayer.getObjects();

    }

    public void setOnBikepath(boolean onBike) {
        this.onBike = onBike;
        if (!onBike) {
            // Handle additional logic for when the character dismounts the bike, if necessary
            this.bike = null;  // Assuming you have a 'bike' object or similar
        }
    }

    // Method to get the character's bike
    public Bike getBike() {
        return this.bike;
    }
    public boolean isOnBike() {
        return onBike;
    }

    public void setOnBike(boolean onBike) {
        this.onBike = onBike;
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        if (onBike && bike != null) {
            Texture bikeTexture = bike.getTexture();
            if (bikeTexture != null) {
                batch.draw(bikeTexture, x, y, width, height);
            }
        } else {
            batch.draw(texture, x, y, width, height);
        }
        font.draw(batch, name, x, y + height + 20);
    }


    // Method to handle user input for character movement
    public void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();

        // Store the character's potential new position
        float newX = x;
        float newY = y;
        if (isOnBike()) {
            speed = 500;  // Increased speed when on the bike
        } else {
            speed = 250;  // Normal walking speed when not on the bike
        }



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
        if (gameMapScreen.bikemovepath(newX, newY, width, height)) {
            // Update position if the new position is on a valid path or if not restricted
            x = newX;
            y = newY;
        } else {

            System.out.println("Movement restricted: Off path");
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

    public void toggleBikeState() {
        this.onBike = !this.onBike;
        // Initialize the bike when the state is toggled
        Texture bikeTexture;
        if (onBike) {
            if (bike == null) {
                bike = new Bike("Bike/bike.png", x, y, width, height);
            }
            bikeTexture = bike.getTexture();
        } else {
            bike = null;
            bikeTexture = null;
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
