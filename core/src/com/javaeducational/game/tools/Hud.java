package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaeducational.game.EducationGame;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.javaeducational.game.entities.Character;
import com.javaeducational.game.screens.GameMapScreen;
import com.javaeducational.game.screens.GameOverScreen;
import com.javaeducational.game.screens.LevelChangeScreen;
import com.javaeducational.game.tools.PopupBox;


public class Hud {
    private EducationGame game;
    public Stage stage;
    private Viewport viewport;
    private TiledMap map;

    private static int score;
    private float timeCount;
    private int worldTimer;
    private boolean timerExpired;

    private PopupBox popupBox;
    private Skin skin;

    // Import Screens
    private GameMapScreen gameMapScreen;
    private LevelChangeScreen levelChangeScreen;

    private static Label scoreLabel;
    private Label CarbonCrunchersLabel;
    private Label timeLabel;
    private Label countdownLabel;
    private Label WorldLabel;
    private Label levelLabel;
    private int carbonFootprint;

    Character character;
    public boolean active;

    public Hud (EducationGame game, TiledMap map, Character character, GameMapScreen gameMapScreen) {
        this.game = game;
        this.score = 0;
        timeCount = 0;
        worldTimer = 50;
        timerExpired = false;
        this.gameMapScreen = gameMapScreen;

        this.active = false;
        this.character = character;
        this.map = map;

        viewport = new FitViewport(EducationGame.WIDTH, EducationGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport , game.batch);

        this.skin = new Skin(Gdx.files.internal("popup/uiskin.json"));

        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt")); // Default font
//        font.getData().setScale(2); // Scale the font size by a factor of 2

        //scoreLabel = new Label (String.format("%06d", score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //CarbonCrunchersLabel= new Label ("Carbon Crunchers",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Declare and initialize world coordinates
        float worldX = 100; // Example value
        float worldY = 200; // Example value

        // Create a vector representing the world coordinates
        Vector3 worldCoordinates = new Vector3(worldX, worldY, 0);

        // Convert world coordinates to screen coordinates
        //Camera.unproject(worldCoordinates);

        // Extract screen coordinates
        float screenX = worldCoordinates.x;
        float screenY = worldCoordinates.y;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // initialising the widgets as int or str
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        countdownLabel = new Label(String.format("%01d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%03d", score), labelStyle);
        CarbonCrunchersLabel = new Label("Score\n", labelStyle);
        timeLabel= new Label("Time\n", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("" + gameMapScreen.getLevel(), new Label.LabelStyle (font, Color.WHITE));
        WorldLabel = new Label("Level\n", new Label.LabelStyle (font, Color.WHITE));


        // using tables structures the hud on the screen
        // the tables will expand to fit the whole screen and the padding will be equal
        table.add(CarbonCrunchersLabel).expandX().padTop(10);
        table.add(WorldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt, int gemsCollected) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%01d", worldTimer));
            timeCount = 0;
        }
        if (worldTimer <= 0){
            character.setCanMove(false);
            timerExpired = true;
        }
        // Update the score label with the current gems collected
        scoreLabel.setText(String.format("%03d", score));
    }

    public static void addScore(int  value) {
        score+=value;
        scoreLabel.setText((String.format("%03d", score)));
    }

    public boolean isTimerExpired() {
        return timerExpired;
    }

    public void takePublicTransport(String type, String stationName, MapObjects stations) {
        active = true;
        Gdx.input.setInputProcessor(stage);
        popupBox = new PopupBox(stationName, skin, "Do you want to take a " + type + "?", stage);
        stage.addActor(popupBox);
        popupBox.show(stage);

        TextButton yesButton = (TextButton) popupBox.getButtonTable().getCells().get(0).getActor();
        TextButton noButton = (TextButton) popupBox.getButtonTable().getCells().get(1).getActor();

        // Add event listener to "Yes" button
        yesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                popupBox.hide(); // Dispose the popup box;
                popupBox.remove();

                int numStations = 8;

                if (type == "train") {
                    numStations = 4;
                }

                String[] stationNames = new String[numStations];

                int count = 0;
                // Get Station Names and Coordinates
                for (RectangleMapObject rectangleObject : stations.getByType(RectangleMapObject.class)) {
                    if (stationName == rectangleObject.getName()) {
                        continue;
                    }
                    stationNames[count] = rectangleObject.getName();
                    count++;
                }

                StationSelectPopup stationPopup = new StationSelectPopup("Select Station", skin, stationNames, stage);
                stage.addActor(stationPopup);
                stationPopup.show(stage);
                stationPopup.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String selectedStation = stationPopup.getSelectedStation();
                        // Get Station Names and Coordinates
                        for (RectangleMapObject rectangleBusObject : stations.getByType(RectangleMapObject.class)) {
                            if (selectedStation.equals(rectangleBusObject.getName())) {
                                if (type == "bus") {
                                    gameMapScreen.setCarbonFootprint(50);
                                    carbonFootprint += 50;
                                    worldTimer -= 10;
                                }
                                if (type == "train") {
                                    gameMapScreen.setCarbonFootprint(25);
                                    worldTimer -= 5;
                                }

                                System.out.println("Your Carbon Footprint is now: " + carbonFootprint);
                                stationPopup.hide();
                                stationPopup.remove();
                                character.takePublicTransport(rectangleBusObject.getRectangle().getX(), rectangleBusObject.getRectangle().getY());
                                character.setCanMove(true);
                                active = false;
                                return;
                            }
                        }
                    }
                });
            }
        });

        // Add event listener to "No" button
        noButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                popupBox.hide();
                popupBox.remove();
                character.setCanMove(true);
                active = false;
            }
        });
    }

    public Skin getSkin() {
        return skin;
    }

    public static int getScore() {
        return score;
    }
}