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

public class MainMenuScreen implements Screen {
    private final EducationGame game;
    private Stage stage;
    private BitmapFont gameFont;
    private GlyphLayout gameTitleGlyph;
    private TextButton buttonPlay, buttonExit, buttonInstructions;
    Skin buttonSkin;
    final MainMenuScreen mainMenuScreen = this;


    public MainMenuScreen(EducationGame game) {
        this.game = game;
        this.gameFont = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt"));
        this.gameTitleGlyph = new GlyphLayout();
        this.buttonSkin = new Skin(Gdx.files.internal("button.json"), new TextureAtlas(Gdx.files.internal("button.atlas")));
        this.buttonPlay = new TextButton("Play", buttonSkin, "default");
        this.buttonExit = new TextButton("Exit", buttonSkin, "default");
        this.buttonInstructions = new TextButton("Instructions", buttonSkin, "default");
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonPlay.setPosition((Gdx.graphics.getWidth() - buttonPlay.getWidth()) / 3f, (Gdx.graphics.getHeight() - buttonPlay.getHeight()) / 2.33f);
        buttonExit.setPosition((Gdx.graphics.getWidth()  - buttonExit.getWidth()) / 1.5f, (Gdx.graphics.getHeight() - buttonExit.getHeight()) / 2.33f);
        buttonInstructions.setPosition((Gdx.graphics.getWidth() - buttonInstructions.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - buttonInstructions.getHeight()) / 1.66f);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuScreen.dispose();
                game.setScreen(new GameMapScreen(game, 1));
            }
        });

        buttonInstructions.addListener(new ClickListener() {
           @Override
            public void clicked(InputEvent event, float x, float y) {
               mainMenuScreen.dispose();
               game.setScreen(new InstructionsScreen(game));
           }

        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuScreen.dispose();
                Gdx.app.exit();
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonInstructions);
        stage.addActor(buttonExit);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.424f, 0.792f, 0.627f,1f);
        gameTitleGlyph.setText(gameFont, "Carbon Cruncher: Tiggy's Adventures");
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        game.batch.begin();
        gameFont.draw(game.batch, gameTitleGlyph, (Gdx.graphics.getWidth() - gameTitleGlyph.width) / 2, (Gdx.graphics.getHeight() + gameTitleGlyph.height) / 1.25f);
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
