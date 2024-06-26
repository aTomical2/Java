package com.javaeducational.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javaeducational.game.EducationGame;

public class LevelChangeScreen implements Screen {
    private EducationGame game;
    private Stage stage;
    private BitmapFont gameFont;
    private Skin buttonSkin;
    private TextButton buttonContinue, buttonExit;
    private GlyphLayout levelStatsLayout;
    float x, y;
    private int level1Score;
    private final LevelChangeScreen levelChangeScreen = this;

    public LevelChangeScreen(EducationGame game, int gemsCollected, int carbonFootprint, int score) {
        this.game = game;
        this.gameFont = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt"));
        this.buttonSkin = new Skin(Gdx.files.internal("button.json"), new TextureAtlas(Gdx.files.internal("button.atlas")));
        this.buttonContinue = new TextButton("Continue", buttonSkin, "default");
        this.buttonExit = new TextButton("Quit Game", buttonSkin, "default");
        this.levelStatsLayout = new GlyphLayout();
        this.level1Score = score - carbonFootprint;
        levelStatsLayout.setText(gameFont, "Level 1 complete! \n\n Are you ready for level 2? \n\n" +
                "Level 1 Stats: \n\n" +
                "--------------------------- \n\n" +
                "Gems Collected: \t" + gemsCollected + " \n\n" +
                "Carbon Footprint: \t" + carbonFootprint + " \n\n" +
                "Score: \t" + score + " \n\n" +
                "--------------------------- \n\n" +
                "Level Score: \t" + level1Score, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonContinue.setPosition((Gdx.graphics.getWidth() - buttonContinue.getWidth()) / 3.33f, (Gdx.graphics.getHeight() - buttonContinue.getHeight()) / 5);
        buttonExit.setPosition((Gdx.graphics.getWidth()  - buttonExit.getWidth()) / 1.45f, (Gdx.graphics.getHeight() - buttonExit.getHeight()) / 5);

        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levelChangeScreen.dispose();
                game.setScreen(new GameMapScreen(game, 2, level1Score));
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levelChangeScreen.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(buttonContinue);
        stage.addActor(buttonExit);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.424f, 0.792f, 0.627f,1f);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        // Center the text on the screen
        x = (Gdx.graphics.getWidth() - levelStatsLayout.width) / 2;
        y = (Gdx.graphics.getHeight() + levelStatsLayout.height) / 1.75f;

        game.batch.begin();
        gameFont.draw(game.batch, levelStatsLayout, x, y);
        stage.draw();
        game.batch.end();

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
        stage.dispose();
        gameFont.dispose();
        buttonSkin.dispose();
        Gdx.input.setInputProcessor(stage);
    }
}