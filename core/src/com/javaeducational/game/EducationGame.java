package com.javaeducational.game;

import com.badlogic.gdx.ApplicationAdapter;
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

    // Initialize resources
    @Override
    public void create() {
        // Create sprite batch
        batch = new SpriteBatch();

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets\\Map\\tilemap1.tmx");;

        // Initialize the renderer
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    // Render the game
    @Override
    public void render() {
        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render the map
        renderer.setView(camera);
        renderer.render();
    }

    // Dispose of resources
    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
    }
}
