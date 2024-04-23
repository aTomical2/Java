package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaeducational.game.EducationGame;
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
        countdownLabel = new Label(String.format("%01d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%02d", score), labelStyle);
        CarbonCrunchersLabel = new Label("Carbon Crunchers", labelStyle);
        timeLabel= new Label("Time", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle (font, Color.WHITE));
        WorldLabel = new Label("Level", new Label.LabelStyle (font, Color.WHITE));

        // using tables structures the hud on the screen; the tables will expand to fit the whole screen and the padding will be equal
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
        if (worldTimer < 1){
            character.setCanMove(false);
            levelEnd();
        }
        // Update the score label with the current gems collected
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void addScore(int  value) {
        score+=value;
        scoreLabel.setText((String.format("%06d", score)));
    }

    public boolean isTimerExpired() {
        return timerExpired;
    }

    public void levelEnd() {
        popupBox = new PopupBox("Time's up!", skin, "Level 1 Complete. Ready for Level 2?", stage);
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
                    System.out.println(rectangleObject.getName());
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
                                    carbonFootprint += 50;
                                    worldTimer -= 10;

                                }
                                if (type == "train") {
                                    carbonFootprint += 25;
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

    public void eduPops(String objectName) {
        String factText = ""; // Initialize with empty text

        // Determine the fact text based on the object name
        if (objectName.equals("Stadium")) {
            factText = "The Men's FIFA World Cup in Qatar (2022) produced 3.63 Million Tonnes of CO2. This is the equivalent of powering 417,450 homes for a year (https://www.statista.com/statistics/1454802/carbon-emissions-world-cups/#:~:text=FIFA%20World%20Cup%20carbon%20emissions%202010%2D2022&text=The%202022%20FIFA%20World%20Cup,2018%20World%20Cup%20in%20Russia.)";
        } else if (objectName.equals("Houses")) {
            factText = "The construction of modern house costs 15-100 tonnes of CO2 (https://climate.mit.edu/ask-mit/how-much-co2-emitted-building-new-house#:~:text=Depending%20on%20size%2C%20materials%2C%20and,might%20emit%20over%20its%20lifetime.)";
        } else if (objectName.equals("Tree")) {
            factText = "Trees take carbon dioxide from the air and store it as carbon in the timber, so big trees are carbon sinks!";
        } else if (objectName.equals("Forest")) {
            factText = "Annual removal of carbon dioxide from the atmosphere by Irish forests exceeds 6 million tonnes per year (https://www.treecouncil.ie/carbon-footprint)";
        } else if (objectName.equals("Ind_Estate")) {
            factText = "The commercial sector in Ireland produced 374,000 tonnes of CO2 in 2022 (https://www.seai.ie/data-and-insights/seai-statistics/key-statistics/co2/)";
        } else if (objectName.equals("River")) {
            factText = "Rivers contain up to 3x the concentrated amount of CO2 compared to the atmosphere (https://www.bbc.com/future/article/20210323-climate-change-the-rivers-that-breathe-greenhouse-gases)";
        } else if (objectName.equals("Lake")) {
            factText = "Freshwater lakes account for ~20% of all global CO2 fossil fuel emissions the atmosphere (https://www.weforum.org/agenda/2022/12/small-lakes-increased-what-to-know-climate-change/)";
        } else if (objectName.equals("Road")) {
            factText = "Transport in the EU is responsible for ~18% of all emissions in the EU (https://www.europarl.europa.eu/topics/en/article/20190313STO31218/co2-emissions-from-cars-facts-and-figures-infographics)";
        } else if (objectName.equals("Garden")) {
            factText = "When maintaining your garden, Petrol/ Gas powered lawnmowers are incredibly harmful for the environment (https://psci.princeton.edu/tips/2020/5/11/law-maintenance-and-climate-change)";
        } else if (objectName.equals("Train")) {
            factText = "Trains are particularly low-carbon ways to travel. Taking a train instead of a car for medium-length distances would cut your emissions by around 80%. (https://ourworldindata.org/travel-carbon-footprint#:~:text=Trains%20are%20particularly%20low%2Dcarbon,your%20emissions%20by%20around%2086%25.)";
        } else if (objectName.equals("Bike")) {
            factText = "Choosing a bike over a car just once a day can reduce the average person's transportation-related emissions by 67 (https://www.future.green/futureblog/save-carbon-biking#:~:text=Reducing%20Carbon%20Emissions,of%20CO2%20per%20mile%20traveled.)";
        } else if (objectName.equals("Mushrooms")) {
            factText = "Mushrooms: The carbon footprint of mushrooms is much smaller than most other sources of proteins and vegetables (https://www.americanmushroom.org/main/sustainability/#:~:text=The%20carbon%20footprint%20of%20mushrooms,per%20pound%20of%20food%20consumed.)";
        }
        // Display the popup box with the appropriate fact text
        popupBox = new PopupBox("Did you know...", skin, factText, stage);
        // Hide the "Yes" and "No" buttons
        popupBox.hideButtons();

        popupBox.show(stage);

        // Add event listeners to the "Yes" and "No" buttons
        TextButton closeButton = (TextButton) popupBox.getButtonTable().getCells().get(0).getActor();
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Handle button click event if needed
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
}