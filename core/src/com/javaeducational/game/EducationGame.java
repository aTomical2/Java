package com.javaeducational.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class EducationGame extends ApplicationAdapter {
    // Sprite batch for rendering
    private SpriteBatch batch;

    // Orthographic camera for viewing the world
    private OrthographicCamera camera;

    // Tiled map and renderer
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Character instance
    private Character character;

    // Define and initialize variables for character creation
    private int initialX = 100; // Example initial X position
    private int initialY = 100; // Example initial Y position
    private int characterWidth = 32; // Example character width
    private int characterHeight = 32; // Example character height
    private int characterSpeed = 200; // Example character speed
    private ArrayList<Gem> gems;
    private final int GEM_COUNT = 5; // Adjust as needed
    private final int MAP_WIDTH = 1000; // Adjust based on your map
    private final int MAP_HEIGHT = 1000;
    private final int GEM_WIDTH = 32;
    private final int GEM_HEIGHT = 32; 

    // Initialize resources
    @Override
    public void create() {
        // Create sprite batch
        batch = new SpriteBatch();
    
        // Create camera
        camera = new OrthographicCamera();
        // Adjust viewport width and height to zoom out
        camera.setToOrtho(false, Gdx.graphics.getWidth() * 1.5f, Gdx.graphics.getHeight() * 1.5f);
    
        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets\\Map\\tilemap1.tmx");
    
        // Initialize the renderer
        renderer = new OrthogonalTiledMapRenderer(map);

        initializeGems();
    
        // Initialize character
        character = new Character("assets/Character/testcharacter.png", 
                                  initialX, 
                                  initialY, 
                                  characterWidth, 
                                  characterHeight, 
                                  characterSpeed, 
                                  "Tiggy");
    
    }

    // Method to initialize gems
    private void initializeGems() {
        gems = new ArrayList<>();
    
        for (int i = 0; i < GEM_COUNT; i++) {
            float gemX = MathUtils.random(0, MAP_WIDTH - GEM_WIDTH); // Corrected
            float gemY = MathUtils.random(0, MAP_HEIGHT - GEM_HEIGHT); // Corrected
    
            gems.add(new Gem("assets/Gems/gemtexture.png", gemX, gemY, GEM_WIDTH, GEM_HEIGHT, 10)); // Corrected
        }
    }
    

    // Render the game
    @Override
    public void render() {
        // Handle user input for camera movement and character control
        handleInput();
    
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);
    
        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    
        // Render the map
        renderer.setView(camera);
        renderer.render();
    
        // Move the character based on user input
        character.handleInput();
    
        // Render the character without scaling
        batch.begin();
        character.render(batch);
        batch.end();

        batch.begin();
        for (Gem gem : gems) {
            gem.render(batch);
        }
        batch.end();
    }

    @Override
public void dispose() {
    batch.dispose();
    map.dispose();
    renderer.dispose();
    character.dispose();
    for (Gem gem : gems) {
        gem.dispose();
    }
}

    // Handle user input for camera movement and character control
    private void handleInput() {
        // Adjust camera speed based on your needs
        float cameraSpeed = 200 * Gdx.graphics.getDeltaTime();
    
        // Move camera left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-cameraSpeed, 0);
        }
        // Move camera right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(cameraSpeed, 0);
        }
        // Move camera up
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, cameraSpeed);
        }
        // Move camera down
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -cameraSpeed);
        }
    
        // Ensure camera follows the character
        camera.position.set(character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2, 0);
        camera.update();
}}
