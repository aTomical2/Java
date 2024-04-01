package com.javaeducational.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
    
        // Initialize character
        character = new Character("assets\\Character\\testcharacter.png", 
                                  Gdx.graphics.getWidth() / 2, 
                                  Gdx.graphics.getHeight() / 2, 
                                  100, 100, 200); // Adjusted width and height
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
    }

    // Dispose of resources
    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
        character.dispose();
    }
}
