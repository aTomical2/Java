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

    Label scoreLabel;
    Label CarbonCrunchersLabel;

    public Hud (SpriteBatch sb) {
    score =0;

    viewport = new FitViewport(EducationGame.WIDTH, EducationGame.HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport , sb);


    Table table = new Table();
    table.top();
    table.setFillParent(true);

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
        //float screenX = worldCoordinates.x;
       // float screenY = worldCoordinates.y;

        // Now, you can position your label or widget using screenX and screenY
        //label.setPosition(screenX, screenY);


        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        CarbonCrunchersLabel = new Label("Carbon Crunchers", labelStyle);


        table.add(scoreLabel).expandX().padTop(10);
        table.add( CarbonCrunchersLabel).expandX().padTop(10);




        stage.addActor(table);

    }



}

