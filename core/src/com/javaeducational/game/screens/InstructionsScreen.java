package com.javaeducational.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import org.w3c.dom.css.Rect;

import java.awt.*;

public class InstructionsScreen implements Screen {
    private EducationGame game;
    private Stage stage;
    private BitmapFont gameFont;
    private Skin buttonSkin;
    private TextButton buttonExit;
    private GlyphLayout instructionsLayout;
    final InstructionsScreen instructionsScreen = this;
    private final String instructions = "Welcome to Carbon Cruncher: Tiggy's Adventures.\n\nIn this game your objective is to navigate the map and collect as many gems as possible within the time limit, while minimizing your environmental impact. \n\n You can use 4 modes of transport: Walking, Bike, Bus, & Train to reach the gems. The more Gems you collect the higher your score will be. \n\nBut be careful! Using faster modes of transport will reduce your time left and increase your Carbon Footprint, reducing your final score :( \n\n Have fun!";
    float x, y;

    public InstructionsScreen(EducationGame game) {
        this.game = game;
        this.gameFont = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt"));
        this.buttonSkin = new Skin(Gdx.files.internal("button.json"), new TextureAtlas(Gdx.files.internal("button.atlas")));
        this.buttonExit = new TextButton("X", buttonSkin, "default");
        this.instructionsLayout = new GlyphLayout();

        instructionsLayout.setText(gameFont, instructions, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        buttonExit.setPosition((Gdx.graphics.getWidth()  - buttonExit.getWidth()) / 1.05f, (Gdx.graphics.getHeight() - buttonExit.getHeight()) / 1.05f);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                instructionsScreen.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
//        stage.addActor(instructionsLayout);
        stage.addActor(buttonExit);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.424f, 0.792f, 0.627f,1f);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        // Center the text on the screen
        x = (Gdx.graphics.getWidth() - instructionsLayout.width) / 2;
        y = (Gdx.graphics.getHeight() + instructionsLayout.height) / 2;

        game.batch.begin();
        gameFont.draw(game.batch, instructionsLayout, x, y);
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

    //create exit function
}
