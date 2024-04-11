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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Hud {
    public Stage stage;
    private Viewport viewport;

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

    public Skin getSkin() {
        return skin;
    }
    public Hud(SpriteBatch sb) {
        score = 0;
        timeCount = 0;
        worldTimer = 10;
        timerExpired = false;

        viewport = new FitViewport(EducationGame.WIDTH, EducationGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // Initialize the skin
        skin = new Skin(Gdx.files.internal("assets/popup/uiskin.json"));

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        countdownLabel = new Label(String.format("%06d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        CarbonCrunchersLabel = new Label("Carbon Crunchers", labelStyle);
        timeLabel = new Label("Time", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(font, Color.WHITE));
        WorldLabel = new Label("Level", new Label.LabelStyle(font, Color.WHITE));

        table.add(CarbonCrunchersLabel).expandX().padTop(10);
        table.add(WorldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

        // Initialize the popup box
        popupBox = new PopupBox("Level Completed!", new Skin(Gdx.files.internal("assets/popup/uiskin.json")));
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%02d", worldTimer));
            timeCount = 0;

            if (worldTimer <= 0) {
                timerExpired = true;
            }
        }
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public boolean isTimerExpired() {
        return timerExpired;
    }

    public void levelEnd() {
        PopupBox popupBox = new PopupBox("Time's up!", skin);
        popupBox.show(stage);
        
        // Add event listeners to the "Yes" and "No" buttons
        TextButton yesButton = (TextButton) popupBox.getButtonTable().getCells().get(0).getActor(); // Assuming "Yes" button is added first
        yesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Handle "Yes" button click
                boolean continueGame = popupBox.getResult();
                if (!continueGame) {
                    // Player chose not to continue, so exit the game
                    Gdx.app.exit();
                } else {
                    // Player chose to continue, handle it accordingly
                    // For example, reset the timer or start the next level
                }
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
}