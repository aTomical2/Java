package com.javaeducational.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javaeducational.game.entities.Bus;
import com.javaeducational.game.entities.Character;
import com.javaeducational.game.EducationGame;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.javaeducational.game.entities.Gem;
import com.javaeducational.game.tools.Hud;

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

    // Gem instance
    private Gem gem;

    // Gem counter
    private int gemsCollected = 0;

    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private int tileWidth; // Assuming tile width in pixels
    private int tileHeight; // Assuming tile height in pixels
    private int mapWidthInTiles; // Assuming map width in tiles
    private int mapHeightInTiles; // Assuming map height is in tiles
    private MapLayer objectLayer;
    private MapObjects objects;

    // Bus Section
    private MapLayer busLayer;
    private MapObjects busStations;

    // Train Section
    private MapLayer trainLayer;
    private MapObjects trainStations;

    // Import bus class
    Bus bus;
    Vector2 startPoint;
    Vector2 endPoint;

    private Hud hud;

    // Gem position and dimensions
    private int gemX = 900 / 2;
    private int gemY = 100 / 2;
    private int gemWidth = 32;
    private int gemHeight = 32;

    public GameMapScreen(EducationGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Create camera
        camera = new OrthographicCamera();
        // Adjust viewport width and height to zoom out
        camera.setToOrtho(false, Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .5f);

        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/MapActual.tmx");

        // Initialize the renderer
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialize solidLayer - Assuming you have a reference to the solid layer
        solidLayer = (TiledMapTileLayer) map.getLayers().get("solid2");

        // Initialize other map-related variables
        tileWidth = (int) solidLayer.getTileWidth();
        tileHeight = (int) solidLayer.getTileHeight();
        mapWidthInTiles = solidLayer.getWidth();
        mapHeightInTiles = solidLayer.getHeight();

        // Initialize character
        character = new Character(solidLayer,
                tileWidth,
                tileHeight,
                mapWidthInTiles,
                mapHeightInTiles);

        hud = new Hud(game.batch, map, character);


        objectLayer = map.getLayers().get("solid2");
        // Check if the objectLayer is not null before accessing its objects
        if (objectLayer != null) {
            objects = objectLayer.getObjects();

        // Initialize gem
        gem = new Gem("Map/blueheart.png", gemX, gemY, gemWidth, gemHeight);
        }

        busLayer = map.getLayers().get("bus_stops");
        busStations = busLayer.getObjects();

        trainLayer = map.getLayers().get("train_stations");
        trainStations = trainLayer.getObjects();
}

    private void relocateGem() {
        // Example random positions, adjust as needed
        gem.setX((float) Math.random() * (1600 - gem.getWidth())); // mapWidth needs to be defined
        gem.setY((float) Math.random() * (1600 - gem.getHeight())); // mapHeight needs to be defined
    }

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

        // Start batch for rendering sprites
        game.batch.begin();

        // Render the character
        character.render(game.batch);

        // Render the gem
        gem.render(game.batch);

    // Check for collision between character and gem
    if (character.getBounds().overlaps(gem.getBounds())) {
    // Increment gems collected
    gemsCollected++;
    System.out.println("Gem collected! Total gems: " + gemsCollected);
    // Increment score by the gem's value
    Hud.addScore(gem.getValue());
    // Relocate gem to a new position
    relocateGem();
    }


        game.batch.end();  // Ensure all sprites are rendered before ending the batch

        // Handle bus station collision and interaction logic
        for (RectangleMapObject rectangleBusObject : busStations.getByType(RectangleMapObject.class)) {
            Rectangle busStationRect = rectangleBusObject.getRectangle();
            if (character.getBounds().overlaps(busStationRect)) {
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (!hud.active) {
                        character.setCanMove(false);
                        hud.takePublicTransport("bus", rectangleBusObject.getName(), busStations);
                    }
                }
            }
        }

        // Handle train station collision and interaction logic
        for (RectangleMapObject rectangleBusObject : trainStations.getByType(RectangleMapObject.class)) {
            Rectangle trainStationRect = rectangleBusObject.getRectangle();
            if (character.getBounds().overlaps(trainStationRect)) {
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (!hud.active) {
                        character.setCanMove(false);
                        hud.takePublicTransport("train", rectangleBusObject.getName(), trainStations);
                    }
                }
            }
        }

        // Update the HUD
        float deltaTime = Gdx.graphics.getDeltaTime();
        hud.stage.act();
        hud.update(deltaTime, gemsCollected);

        // Render the HUD stage
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
