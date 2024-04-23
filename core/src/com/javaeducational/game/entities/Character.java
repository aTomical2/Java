package com.javaeducational.game.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.javaeducational.game.screens.GameMapScreen;


public class Character {
    // Texture representing the character
    private Texture texture;

    // Position and dimensions of the character
    private float x;
    private float y;
    private float width;
    private float height;
    private final int WIDTH_PIXEL = 40;
    private final int HEIGHT_PIXEL = 40;

    // Speed of the character
    private float speed;

    private String name;
    private BitmapFont font;

    private boolean onBike;
    private Bike bike;
    private MapObjects bikepaths;
    private MapLayer bikepathslayer;
    private GameMapScreen gameMapScreen;
    public boolean inBikeStandCollision = false;

    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private int tileWidth; // Assuming tile width in pixels
    private int tileHeight; // Assuming tile height in pixels
    private int mapWidthInTiles; // Assuming map width in tiles
    private int mapHeightInTiles; // Assuming map height in tiles
    private Rectangle bounds; // Get bounds for collisions
    private boolean canMove;

    //Textures for walking
    private TextureRegion[] upFrames;
    private TextureRegion[] downFrames;
    private TextureRegion[] leftFrames;
    private TextureRegion[] rightFrames;

    // Animations for walking
    private TextureRegion[][] characterSpriteSheet;
    private Animation<TextureRegion> upWalkingAnimation;
    private Animation<TextureRegion> downWalkingAnimation;
    private Animation<TextureRegion> leftWalkingAnimation;
    private Animation<TextureRegion> rightWalkingAnimation;

    // Animations for each direction when stopped
    private TextureRegion upStoppedFrame;
    private TextureRegion downStoppedFrame;
    private TextureRegion leftStoppedFrame;
    private TextureRegion rightStoppedFrame;

    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;

    private float stateTime;

    boolean isFacingUp = false;
    boolean isFacingDown = false;
    boolean isFacingLeft = false;
    boolean isFacingRight = true; // Start facing right by default

    // Constructor
    public Character(GameMapScreen gameMapScreen, TiledMapTileLayer solidLayer, int tileWidth, int tileHeight,
                     int mapWidthInTiles, int mapHeightInTiles) {
        this.x = 1800/2;
        this.y= 900 /2;
        this.width = 32;
        this.height = 32;
        this.speed = 250;
        this.name = "TigglyWigglyBigglyDiggly Purcell";
        this.solidLayer = solidLayer;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidthInTiles = mapWidthInTiles;
        this.mapHeightInTiles = mapHeightInTiles;
        this.bounds = new Rectangle(x, y, width, height);
        this.canMove = true;

        characterSpriteSheet = TextureRegion.split(new Texture("Character/character.png"), WIDTH_PIXEL, HEIGHT_PIXEL);

        this.gameMapScreen = gameMapScreen;
        this.onBike = false;
        bikepathslayer = GameMapScreen.map.getLayers().get("bikepaths");
        bikepaths = bikepathslayer.getObjects();

        upFrames = new TextureRegion[2];
        downFrames = new TextureRegion[2];
        leftFrames = new TextureRegion[2];
        rightFrames = new TextureRegion[2];

        upFrames[0] = characterSpriteSheet[2][0];
        upFrames[1] = characterSpriteSheet[2][2];

        downFrames[0] = characterSpriteSheet[0][0];
        downFrames[1] = characterSpriteSheet[0][2];

        leftFrames[0] = characterSpriteSheet[3][0];
        leftFrames[1] = characterSpriteSheet[3][2];

        rightFrames[0] = characterSpriteSheet[1][0];
        rightFrames[1] = characterSpriteSheet[1][2];

        upStoppedFrame = characterSpriteSheet[2][1];
        downStoppedFrame = characterSpriteSheet[0][1];
        leftStoppedFrame = characterSpriteSheet[3][1];
        rightStoppedFrame = characterSpriteSheet[1][1];

        upWalkingAnimation = new Animation<TextureRegion>(0.1f, upFrames);
        downWalkingAnimation = new Animation<TextureRegion>(0.1f, downFrames);
        leftWalkingAnimation = new Animation<TextureRegion>(0.1f, leftFrames);
        rightWalkingAnimation = new Animation<TextureRegion>(0.1f, rightFrames);

        // Set initial animation
        isFacingUp = false;
        isFacingDown = true;
        isFacingLeft = false;
        isFacingRight = false;

        currentAnimation = downWalkingAnimation;

        currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        // Initialize font
        font = new BitmapFont();
    }

    public Bike getBike() {
        return this.bike;
    }
    public boolean isOnBike() {
        return onBike;
    }
    public void setOnBike(boolean onBike) {
        this.onBike = onBike;
    }

    public void handleInput() {
        if (!canMove) {
            return;
        }

        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;

        // Store the character's potential new position
        float newX = x;
        float newY = y;

        // Move the character based on user input
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            isFacingUp = true;
            isFacingDown = false;
            isFacingLeft = false;
            isFacingRight = false;
            newY += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            isFacingUp = false;
            isFacingDown = true;
            isFacingLeft = false;
            isFacingRight = false;
            newY -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            isFacingUp = false;
            isFacingDown = false;
            isFacingLeft = true;
            isFacingRight = false;
            newX -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            isFacingUp = false;
            isFacingDown = false;
            isFacingLeft = false;
            isFacingRight = true;
            newX += speed * delta;
        }
        if (isOnBike()) {
            speed = 500;  // Increased speed when on the bike
        } else {
            speed = 250;  // Normal walking speed when not on the bike
        }
        if (gameMapScreen.bikemovepath(newX, newY, width, height)) {
            // Update position if the new position is on a valid path or if not restricted
            x = newX;
            y = newY;
        }
        // Check collision with map boundaries and solid tiles
        if (newX >= 0 && newX + width <= mapWidthInTiles * tileWidth &&
                !collidesWithSolidTiles(newX, newY)) {
            // Update character position if no collision detected
            x = newX;
            y = newY;
        }
        // updates bounds for player
        this.bounds.setPosition(x, y);
    }

    // Method to render the character
    public void render(SpriteBatch batch) {
        if (isFacingUp) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                currentFrame = upWalkingAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = upStoppedFrame;
            }
        } else if (isFacingDown) {
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                currentFrame = downWalkingAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = downStoppedFrame;
            }
        } else if (isFacingLeft) {
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                currentFrame = leftWalkingAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = leftStoppedFrame;
            }
        } else if (isFacingRight) {
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                currentFrame = rightWalkingAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = rightStoppedFrame;
            }
        }
        if (onBike && bike != null) {
            Texture bikeTexture = bike.getTexture();
            if (bikeTexture != null) {
                batch.draw(bikeTexture, x, y, width, height);
            }
        } else {
            batch.setColor(1, 1,1,1);
            batch.draw(currentFrame, x, y, width, height);
        }
        font.draw(batch, name, x, y + height + 20);

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
        font.dispose();
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

    public Rectangle getBounds() {
        return bounds;
    }

    public void takePublicTransport(float x, float y) {
        this.x = x;
        this.y = y;
        bounds.setPosition(x, y);
        isFacingUp = false;
        isFacingDown = true;
        isFacingLeft = false;
        isFacingRight = false;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
}

