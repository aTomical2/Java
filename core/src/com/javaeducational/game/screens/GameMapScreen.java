package com.javaeducational.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.javaeducational.game.entities.Character;
import com.javaeducational.game.EducationGame;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.javaeducational.game.entities.Gem;
import com.javaeducational.game.tools.Hud;
import com.javaeducational.game.tools.GameResultManager;

import java.util.ArrayList;

public class GameMapScreen implements Screen {
    // Sprite batch for rendering
    private EducationGame game;

    private OrthographicCamera camera;    // Orthographic camera for viewing the world

    // Tiled map and renderer
    public static TiledMap map;
    private MapObjects bikeStands;
    private MapLayer bikeStandsLayer;
    private MapObjects bikepaths;
    private MapLayer bikepathslayer;

    private OrthogonalTiledMapRenderer renderer;

    private Character character; // Character instance

    private Gem gem, gem2; // Gem instance

    // Gem counter
    private int gemsCollected = 0;

    // CarbonFootprint
    private int carbonFootprint = 0;

    // Variables related to map and collision
    private TiledMapTileLayer solidLayer; // Assuming solid layer is available
    private int tileWidth; // Assuming tile width in pixels
    private int tileHeight; // Assuming tile height in pixels
    private int mapWidthInTiles; // Assuming map width in tiles
    private int mapHeightInTiles; // Assuming map height is in tiles
    private MapLayer objectLayer;
    private MapObjects objects;
    private MapLayer busLayer; // Bus Section
    private MapObjects busStations;
    private MapLayer trainLayer;   // Train Section
    private MapObjects trainStations;
    private MapLayer eduPopsLayer; // edupopups
    private MapObjects eduPopsObjects;

    public Sprite arrowSprite, arrowSprite2;
    private Texture arrowTexture;

    private Hud hud;

    // Gem position and dimensions
    private int gemX = 1000;
    private int gemY = 100 / 2;
    private int gemWidth = 32;
    private int gemHeight = 32;

    //Set Level + track score
    private int level;
    private int level1Score;

    private String lastAccessedPopup = null;

    private GameMapScreen gameMapScreen = this;

    public GameMapScreen(EducationGame game, int level) {
        this.game = game;
        this.level = level;
    }

    public GameMapScreen(EducationGame game, int level, int level1Score) {
        this.game = game;
        this.level = level;
        this.level1Score = level1Score;
    }

    public Sprite createArrowSprite() {
        Sprite arrow = new Sprite(arrowTexture);
        arrow.setSize(32, 32);
        arrow.setOriginCenter();
        return arrow;
    }
    public void updateArrow(Sprite arrow, Gem targetGem) {
        if (targetGem == null || arrow == null) return; // Ensure both gem and arrow are not null

        Vector2 characterPosition = new Vector2(character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2);
        Vector2 gemPosition = new Vector2(targetGem.getX() + targetGem.getWidth() / 2, targetGem.getY() + targetGem.getHeight() / 2);
        Vector2 directionToGem = new Vector2(gemPosition).sub(characterPosition);
        float angleToGem = directionToGem.angleDeg();

        arrow.setRotation(angleToGem);
        arrow.setPosition(characterPosition.x - arrow.getWidth() / 2, characterPosition.y - arrow.getHeight() / 2 + 60);
    }
    public void updateArrow2(Sprite arrow, Gem targetGem) {
        if (targetGem == null || arrow == null) return; // Ensure both gem and arrow are not null

        Vector2 characterPosition = new Vector2(character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2);
        Vector2 gemPosition = new Vector2(targetGem.getX() + targetGem.getWidth() / 2, targetGem.getY() + targetGem.getHeight() / 2);
        Vector2 directionToGem = new Vector2(gemPosition).sub(characterPosition);
        float angleToGem = directionToGem.angleDeg();

        arrow.setRotation(angleToGem);
        arrow.setPosition(characterPosition.x - arrow.getWidth() / 2, characterPosition.y - arrow.getHeight() / 2 - 60);
    }

    @Override
    public void show() {
        // Create camera
        camera = new OrthographicCamera();
        // Adjust viewport width and height to zoom out
        camera.setToOrtho(false, Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .5f);

        // Load the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        if (level == 1) {
            map = mapLoader.load("Map/MapActual.tmx");
        }
        if (level == 2) {
            map = mapLoader.load("Map/MapActual2.tmx");
        }

        // Initialize the renderer
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialize solidLayer
        if (level == 1) {
            solidLayer = (TiledMapTileLayer) map.getLayers().get("solidLevel1");
        }
        if (level == 2) {
            solidLayer = (TiledMapTileLayer) map.getLayers().get("solid2");
        }

        // Initialize other map-related variables
        tileWidth = (int) solidLayer.getTileWidth();
        tileHeight = (int) solidLayer.getTileHeight();
        mapWidthInTiles = solidLayer.getWidth();
        mapHeightInTiles = solidLayer.getHeight();

        // Initialize character
        character = new Character(this, solidLayer,
                tileWidth,
                tileHeight,
                mapWidthInTiles,
                mapHeightInTiles);

        hud = new Hud(game, map, character, gameMapScreen);

        objectLayer = map.getLayers().get("solid2");
        // Check if the objectLayer is not null before accessing its objects
        if (objectLayer != null) {
            objects = objectLayer.getObjects();

        // Initialize gem
        gem = new Gem("Map/blueheart.png", gemX, gemY, gemWidth, gemHeight);
        }
        if (level == 2) {
             gem2 = new Gem("Map/blueheart.png", gemX - 500, gemY + 1000, gemWidth, gemHeight);
        }
        if (arrowTexture == null) {
            arrowTexture = new Texture(Gdx.files.internal("Character/arrow.png"));
        }
        arrowSprite = createArrowSprite(); // Always create the first arrow
        if (level == 2) {
            arrowSprite2 = createArrowSprite(); // Correctly create second arrow for level 2
        }
        if (level == 2) {
            hud.displayedPopups.clear();
        }

        trainLayer = map.getLayers().get("train_stations");
        trainStations = trainLayer.getObjects();
        busLayer = map.getLayers().get("bus_stops");
        busStations = busLayer.getObjects();
        bikeStandsLayer = map.getLayers().get("bike_stops");
        bikeStands = bikeStandsLayer.getObjects();
        bikepathslayer = map.getLayers().get("bikepaths");
        bikepaths = bikepathslayer.getObjects();
        eduPopsLayer = map.getLayers().get("edupops");
        eduPopsObjects = eduPopsLayer.getObjects();
    }

    private void relocateGem() {
        // Example random positions, adjust as needed
        if (level == 1) {
            gem.setX((float) Math.random() * (1600 - gem.getWidth() - 820) + 820);
            gem.setY((float) Math.random() * (1600 - gem.getHeight() - 780));
        }
        if (level == 2) {
            gem.setX((float) Math.random() * (1600 - gem.getWidth()));
            gem.setY((float) Math.random() * (1600 - gem.getHeight()));
        }
    }

    public void render(float delta) {
        handleInput();        // Handle user input for camera movement and character control
        checkCollisionWithBikeStand();
        bikemovepath(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        checkCollisionWithEduPopsObjects();

        // Clear screen
        ScreenUtils.clear(0.22f, 0.53f, 0,1f);

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
        if (character.getBike() != null && character.getBike().isOnBike()) {
            character.getBike().render(game.batch);

        } else {
            character.handleInput();
            character.render(game.batch);
        }
        // Render the gem
        gem.render(game.batch);
        if (level == 2) {
            gem2.render(game.batch);
        }
        if (arrowSprite != null) {
            updateArrow(arrowSprite, gem);
            if (!isVisible(gem)) {
                arrowSprite.draw(game.batch);
            }
        }

        if (level == 2 && arrowSprite2 != null) {
            updateArrow2(arrowSprite2, gem2);
            if (!isVisible(gem2)) {
                arrowSprite2.draw(game.batch);
            }
        }
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
        // Check for collision between character and gem for level 2
        if (level == 2) {
            if (character.getBounds().overlaps(gem2.getBounds())) {
                // Increment gems collected
                gemsCollected++;
                System.out.println("Gem collected! Total gems: " + gemsCollected);
                // Increment score by the gem's value
                Hud.addScore(gem2.getValue());
                // Relocate gem to a new position
                gem2.setX((float) Math.random() * (1600 - gem.getWidth()));
                gem2.setY((float) Math.random() * (1600 - gem.getHeight()));
            }
        }

        game.batch.end();  // Ensure all sprites are rendered before ending the batch

        // Handle bus station collision and interaction logic
        if (level == 2) {
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
        }

        // Handle train station collision and interaction logic
        if (level == 2) {
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
        }

        // Update the HUD
        float deltaTime = Gdx.graphics.getDeltaTime();
        hud.stage.act();
        hud.update(deltaTime, gemsCollected);

        // Render HUD stage
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (hud.isTimerExpired()) {
            saveGameResultsToFile(level, hud.getScore(), gemsCollected, carbonFootprint, "assets/Saves/game_results.txt");

            if (level == 1) {
                game.setScreen(new LevelChangeScreen(game, gemsCollected, carbonFootprint, hud.getScore()));
            }
            if (level == 2) {
                game.setScreen(new GameOverScreen(game, gemsCollected, carbonFootprint, hud.getScore(), level1Score));
            }
        }
    }
    private boolean isVisible(Gem gem) {
        float cameraLeftX = camera.position.x - camera.viewportWidth / 2;
        float cameraRightX = camera.position.x + camera.viewportWidth / 2;
        float cameraBottomY = camera.position.y - camera.viewportHeight / 2;
        float cameraTopY = camera.position.y + camera.viewportHeight / 2;

        return gem.getX() >= cameraLeftX && gem.getX() <= cameraRightX &&
                gem.getY() >= cameraBottomY && gem.getY() <= cameraTopY;
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
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { // Move camera left
                camera.translate(-cameraSpeed, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // Move camera right
                camera.translate(cameraSpeed, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // Move camera up
                camera.translate(0, cameraSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { // Move camera down
                camera.translate(0, -cameraSpeed);
            }
            // Ensure camera follows the character
            camera.position.set(character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2, 0);
            camera.update();
        }

    private void checkCollisionWithEduPopsObjects() {
        Rectangle characterBounds = character.getBounds();
        for (RectangleMapObject eduPopsObject : eduPopsObjects.getByType(RectangleMapObject.class)) {
            Rectangle eduPopsObjectBounds = eduPopsObject.getRectangle();
            if (characterBounds.overlaps(eduPopsObjectBounds)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (!hud.active) {
                        String currentPopupName = eduPopsObject.getName();
                        if (!currentPopupName.equals(lastAccessedPopup)) {
                            // Reset the active flag and show the popup
                            hud.active = true;
                            hud.eduPops(currentPopupName);
                            Hud.addScore(5);
                            lastAccessedPopup = currentPopupName; // Update the last accessed popup
                        }
                        character.setCanMove(true);
                    }
                }
            }
        }
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
            arrowTexture.dispose(); // Dispose of arrow texture
            if (level == 2 && gem2 != null) {
                gem2.dispose();}
        }
    public int getGemsCollected() {
        return gemsCollected;
    }
    public void setGemsCollected(int gemsCollected) {
        this.gemsCollected += gemsCollected;
    }
    public int getCarbonFootprint() {
        return carbonFootprint;
    }
    public void setCarbonFootprint(int carbonFootprint) {
        this.carbonFootprint += carbonFootprint;
    }
    public int getLevel() {
        return level;
    }
    public void saveGameResultsToFile(int level, int score, int gemsCollected, int carbonFootprint, String filename) {
        GameResultManager.saveLevelResults(level, score, gemsCollected, carbonFootprint, filename);
    }
}