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
import com.badlogic.gdx.math.Intersector;
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
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Character instance
    private Character character;

    // Gem instance
    private Gem gem;

    //Gem counter
    private int gemsCollected = 0;

    // Define and initialize variables for character creation
    private int initialX = 1800 / 2; // Example initial X position
    private int initialY = 900 / 2; // Example initial Y position
    private int characterWidth = 32; // Example character width
    private int characterHeight = 32; // Example character height
    private int characterSpeed = 250; // Example character speed

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
    // Import bus class
    Bus bus;
    Vector2 startPoint;
    Vector2 endPoint;

    private MapObjects bikeStands;
    private MapLayer bikeStandsLayer;

    private MapObjects bikepaths;
    private MapLayer bikepathslayer;


    // private CollisionRect rect;
    private Hud hud;

    // Gem position and dimensions
    private int gemX = 900 / 2;
    private int gemY = 100 / 2;
    private int gemWidth = 32;
    private int gemHeight = 32;



    public GameMapScreen(EducationGame game) {
        this.game = game;
        hud = new Hud (game.batch);
    }

    @Override
    public void show() {
        // Create camera
        camera = new OrthographicCamera();
        // Adjust viewport width and height to zoom out
        camera.setToOrtho(false, Gdx.graphics.getWidth() * 1.5f, Gdx.graphics.getHeight() * 1.5f);

        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/MapActual.tmx");
        for (MapLayer maplayer : map.getLayers()) {
            System.out.println(maplayer.getName() + "test");

        }

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
        character = new Character(this,"Character/testcharacter.png",

                initialX,
                initialY,
                characterWidth,
                characterHeight,
                characterSpeed,
                "Tiggy",
                solidLayer,
                tileWidth,
                tileHeight,
                mapWidthInTiles,
                mapHeightInTiles);


        objectLayer = map.getLayers().get("solid2");
        // Check if the objectLayer is not null before accessing its objects
        if (objectLayer != null) {
            objects = objectLayer.getObjects();

        // Initialize gem
        gem = new Gem("Map/blueheart.png",
                gemX,
                gemY,
                gemWidth,
                gemHeight);

    }
        busLayer = map.getLayers().get("bus_stops");
        busStations = busLayer.getObjects();
        bikeStandsLayer = map.getLayers().get("bike_stops");
        bikeStands = bikeStandsLayer.getObjects();
        bikepathslayer = map.getLayers().get("bikepaths");
        bikepaths = bikepathslayer.getObjects();
}
    private void relocateGem() {
        // Example random positions, adjust as needed
        gem.setX((float) Math.random() * (1600 - gem.getWidth())); // mapWidth needs to be defined
        gem.setY((float) Math.random() * (1600 - gem.getHeight())); // mapHeight needs to be defined
    }

    @Override
    public void render(float delta) {
        // Handle user input for camera movement and character control
        handleInput();
        checkCollisionWithBikeStand();
        bikemovepath(character.getX(), character.getY(), character.getWidth(), character.getHeight());

        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Render the map
        renderer.setView(camera);
        renderer.render();
        game.batch.begin();

        if (character.getBike() != null && character.getBike().isOnBike()) {
            character.getBike().render(game.batch);

        } else {
            character.handleInput();
            character.render(game.batch);
        }



        // Render the character and gem without scaling


//        bus.update(delta); // update position
//        bus.render(game.batch); // then render

        gem.render(game.batch);


        // Check collision with gem
        if (character.getBounds().overlaps(gem.getBounds())) {
            gemsCollected++;
            relocateGem();
            Hud.addScore(200);
            System.out.println("Gems Collected: " + gemsCollected);

        }

        game.batch.end();
        // Collision bus station
        for (RectangleMapObject rectangleBusObject : busStations.getByType(RectangleMapObject.class)) {
            Rectangle busStationRect = rectangleBusObject.getRectangle();
            if (character.getBounds().overlaps(busStationRect)) {
                System.out.println("Character/Bus Station Collision");
            }
        }
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                if (rect.overlaps(character.getBounds())) {
                    System.out.println("OMG YES BOY");
                }
            }
        }


        // render score hud
       // hud.update(dt);
        float deltaTime = Gdx.graphics.getDeltaTime(); // Assuming you're using libGDX
        hud.update(deltaTime);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }
    public boolean bikemovepath(float newX, float newY, float width, float height) {
        Rectangle newRect = new Rectangle(newX, newY, width, height);
        boolean isOnPath = false;
        if (character.isOnBike()) {
            for (MapObject object : bikepaths) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    if (Intersector.overlaps(newRect, rect)) {
                        isOnPath = true; // At least part of the bike is on the path
                        break;
                    }
                }
            }
            if (!isOnPath) {
                character.setOnBike(false);
                System.out.println("You are off the path! Dismounting bike...");
            }
        }
        return isOnPath; // Return true only if the new position overlaps with a bike path
    }
    private void checkCollisionWithBikeStand() {
        Rectangle characterBounds = character.getBounds();
        boolean collisionDetected = false;

        for (RectangleMapObject rectangleBikeStandObject : bikeStands.getByType(RectangleMapObject.class)) {
            Rectangle bikeStandRect = rectangleBikeStandObject.getRectangle();
            if (characterBounds.overlaps(bikeStandRect)) {
                collisionDetected = true;
                if (!character.inBikeStandCollision) {
                    character.toggleBikeState();
                    character.inBikeStandCollision = true;
                    System.out.println("Collision with Bike Stand Detected. Bike state toggled to: " + character.isOnBike());
                }
                break;
            }
        }


        if (!collisionDetected && character.inBikeStandCollision) {
            character.inBikeStandCollision = false;  // Reset the collision flag when no longer colliding
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

