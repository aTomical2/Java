package com.javaeducational.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javaeducational.game.entities.Character;
import com.javaeducational.game.EducationGame;
import com.javaeducational.game.entities.Gem;

public class GameMapScreen implements Screen {
    // Sprite batch for rendering
    private EducationGame game;

    // Orthographic camera for viewing the world
    private OrthographicCamera camera;

    // Tiled map and renderer
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Character instance
    private Character character;

    //Gem instance
    private Gem gem;

    // Define and initialize variables for character creation
    private int initialX = 1800 / 2; // Example initial X position
    private int initialY = 900 /2 ; // Example initial Y position
    private int characterWidth = 32; // Example character width
    private int characterHeight = 32; // Example character height
    private int characterSpeed = 200; // Example character speed
    private int GemX = 900 / 2;

    private int GemY = 100/2;

    private int Gem_Width = 32;
    private int Gem_Height = 32;
    private MapLayer objectLayer;
    private MapObjects objects;
    public GameMapScreen(EducationGame game) {
        this.game = game;
    }
    @Override
    public void show() {
        // Create camera
        camera = new OrthographicCamera();
        // Adjust viewport width and height to zoom out
        camera.setToOrtho(false, Gdx.graphics.getWidth() * 1.5f, Gdx.graphics.getHeight() * 1.5f);

        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/tilemap1.tmx");

        // Initialize the renderer
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialize character
        character = new Character("Character/testcharacter.png",
                initialX,
                initialY,
                characterWidth,
                characterHeight,
                characterSpeed,
                "Tiggy");
          objectLayer = map.getLayers().get("trial-transport");
          objects = objectLayer.getObjects();

         gem = new Gem("Map/blueheart.png",
                 GemX,
                 GemY,
                 Gem_Width,
                 Gem_Height);
    }

    @Override
    public void render(float delta) {
        // Handle user input for camera movement and character control
        handleInput();

        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Render the map
        renderer.setView(camera);
        renderer.render();

        // Move the character based on user input
        character.handleInput();

        // Render the character without scaling
        game.batch.begin();
        character.render(game.batch);
        gem.render(game.batch);
        game.batch.end();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                if (rect.overlaps(character.getBounds())) {
                    System.out.println("OMG YES BOY");
                }
            }
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
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        map.dispose();
        renderer.dispose();
        character.dispose();
        gem.dispose();
    }
}
