package com.javaeducational.game.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaeducational.game.EducationGame;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




public class Hud {
    public Stage stage;
    private Viewport viewport;

    private int score;

    private float timeCount;

    private int worldTimer;



    Label scoreLabel;
    Label CarbonCrunchersLabel;
    Label timeLabel;
    Label countdownLabel;
    Label WorldLabel;

    Label levelLabel;

    public Hud (SpriteBatch sb) {
        score =0;
        timeCount =0;
        worldTimer =100;

        viewport = new FitViewport(EducationGame.WIDTH, EducationGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport , sb);



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


    public void update(float dt) {
        timeCount +=dt;
        if (timeCount >=1){
            worldTimer --;
            countdownLabel.setText(String.format("%06d", worldTimer));
            timeCount = 0;

        }
    }


}