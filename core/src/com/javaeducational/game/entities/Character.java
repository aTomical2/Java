package com.javaeducational.game.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


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
    private boolean canMove;

    // Textures for each direction
    private Texture frontTexture;
    private Texture backTexture;
    private Texture leftTexture;
    private Texture rightTexture;

    // Animations for each direction
    private Animation<TextureRegion> frontAnimation;
    private Animation<TextureRegion> backAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;

    TextureRegion currentFrame;

    private float stateTime;

    // Current animation
    private Animation<TextureRegion> currentAnimation;

    boolean isFacingUp = false;
    boolean isFacingDown = false;
    boolean isFacingLeft = false;
    boolean isFacingRight = true; // Start facing right by default

    // Constructor
    public Character(TiledMapTileLayer solidLayer, int tileWidth, int tileHeight,
                     int mapWidthInTiles, int mapHeightInTiles) {
        this.x = 1800/2;
        this.y= 900 /2;
        this.width = 32;
        this.height = 32;
        this.speed = 250;
        this.name = "tiggy";
        this.solidLayer = solidLayer;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidthInTiles = mapWidthInTiles;
        this.mapHeightInTiles = mapHeightInTiles;
        this.bounds = new Rectangle(x, y, width, height);
        this.canMove = true;

        // Load textures
         leftTexture = new Texture(Gdx.files.internal("Character/leftC.png"));
         rightTexture = new Texture(Gdx.files.internal("Character/rightC.png"));
         frontTexture = new Texture(Gdx.files.internal("Character/frontC.png"));
         backTexture = new Texture(Gdx.files.internal("Character/backC.png"));
         leftTexture = new Texture(Gdx.files.internal("Character/leftC.png"));
         rightTexture = new Texture(Gdx.files.internal("Character/rightC.png"));
         frontTexture = new Texture(Gdx.files.internal("Character/frontC.png"));
         backTexture = new Texture(Gdx.files.internal("Character/backC.png"));


        // Create animations
        frontAnimation = new Animation<>(0.1f, new TextureRegion(frontTexture));
        backAnimation = new Animation<>(0.1f, new TextureRegion(backTexture));
        leftAnimation = new Animation<>(0.1f, new TextureRegion(leftTexture));
        rightAnimation = new Animation<>(0.1f, new TextureRegion(rightTexture));

        // Set initial animation
        //currentAnimation = frontAnimation;
        isFacingUp = false;
        isFacingDown = false;
        isFacingLeft = false;
        isFacingRight = true; // Start facing right by default

        currentAnimation = frontAnimation;

        currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        // Initialize font
        font = new BitmapFont();
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
        // Render the appropriate animation based on the current animation state

        //TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
       // TextureRegion currentFrame = currentAnimation.getKeyFrame(0, true); // State time doesn't matter

       // if (currentAnimation != null) {
          //  TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        if (isFacingUp) {
            currentAnimation = backAnimation;
        } else if (isFacingDown) {
            currentAnimation = frontAnimation;
        } else if (isFacingLeft) {
            currentAnimation = leftAnimation;
        } else if (isFacingRight) {
            currentAnimation = rightAnimation;
        }

        currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.setColor(1, 1,1,1);
        batch.draw(currentFrame, x, y, width, height);

        // Render the name above the character
        font.draw(batch, name, x, y + height + 20); // Adjust 20 according to your preference
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
        frontTexture.dispose();
        backTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
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

    public void takeBus(float x, float y) {
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

