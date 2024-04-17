package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
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

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private TiledMap map;

    private static int score;
    private float timeCount;
    private int worldTimer;
    private boolean timerExpired;

    private PopupBox popupBox;
    private Skin skin;

    private static Label scoreLabel;
    private Label CarbonCrunchersLabel;
    private Label timeLabel;
    private Label countdownLabel;
    private Label WorldLabel;
    private Label levelLabel;
    private int carbonFootprint;

    Character character;
    public boolean active;

    public Hud (SpriteBatch sb, TiledMap map, Character character) {
        score = 0;
        timeCount = 0;
        worldTimer = 100;
        timerExpired = false;
        this.carbonFootprint = 0;

        this.active = false;
        this.character = character;
        this.map = map;

        viewport = new FitViewport(EducationGame.WIDTH, EducationGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport , sb);

        this.skin = new Skin(Gdx.files.internal("popup/uiskin.json"));

        BitmapFont font = new BitmapFont(); // Default font
        font.getData().setScale(2); // Scale the font size by a factor of 2

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
        countdownLabel = new Label(String.format("%06d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        CarbonCrunchersLabel = new Label("Carbon Crunchers", labelStyle);
        timeLabel= new Label("Time", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle (font, Color.WHITE));
        WorldLabel = new Label("Level", new Label.LabelStyle (font, Color.WHITE));


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
            countdownLabel.setText(String.format("%06d", worldTimer));
            timeCount = 0;
        }
    
    }
    public static void addScore(int  value) {
        score+=value;
        scoreLabel.setText((String.format("%06d", score)));
    }

    public boolean isTimerExpired() {
        return timerExpired;
    }

    public void levelEnd() {
        popupBox = new PopupBox("Time's up!", skin, "Level-1 Complete. Ready for Level-2?", stage);
        popupBox.show(stage);

        // Add event listeners to the "Yes" and "No" buttons
        TextButton yesButton = (TextButton) popupBox.getButtonTable().getCells().get(0).getActor(); // Assuming "Yes" button is added first
        yesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Handle "Yes" button click
//                boolean continueGame = popupBox.getResult();
//                if (!continueGame) {
//                    // Player chose not to continue, so exit the game
//                    Gdx.app.exit();
//                } else {
//                    // Player chose to continue, handle it accordingly
//                    // For example, reset the timer or start the next level
//                }
            }
        });

        TextButton noButton = (TextButton) popupBox.getButtonTable().getCells().get(1).getActor(); // Assuming "No" button is added second
        noButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Handle "No" button click
                popupBox.hide(); // Close the popup box
                // You can add any other actions here if needed
            }
        });
    }

    public void takeBus(String stationName, MapObjects busStations) {
        active = true;
        Gdx.input.setInputProcessor(stage);
        popupBox = new PopupBox(stationName, skin, "Do you want to take a bus", stage);
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

                String[] busStationNames = new String[8];
                int count = 0;
                // Get Station Names and Coordinates
                for (RectangleMapObject rectangleBusObject : busStations.getByType(RectangleMapObject.class)) {
                    if (stationName == rectangleBusObject.getName()) {
                        continue;
                    }
                    busStationNames[count] = rectangleBusObject.getName();
                    count++;
                }

                StationSelectPopup stationPopup = new StationSelectPopup("Select Station", skin, busStationNames, stage);
                stage.addActor(stationPopup);
                stationPopup.show(stage);
                stationPopup.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String selectedStation = stationPopup.getSelectedStation();
                        // Get Station Names and Coordinates
                        for (RectangleMapObject rectangleBusObject : busStations.getByType(RectangleMapObject.class)) {
                            if (selectedStation.equals(rectangleBusObject.getName())) {
                                carbonFootprint += 50;
                                System.out.println("Your Carbon Footprint is now: " + carbonFootprint);
                                stationPopup.hide();
                                stationPopup.remove();
                                character.takeBus(rectangleBusObject.getRectangle().getX(), rectangleBusObject.getRectangle().getY());
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
}