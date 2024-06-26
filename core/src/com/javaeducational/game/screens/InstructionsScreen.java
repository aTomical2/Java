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

public class InstructionsScreen implements Screen {
    private EducationGame game;
    private Stage stage;
    private BitmapFont gameFont;
    private Skin buttonSkin;
    private TextButton buttonNext;
    private GlyphLayout instructionsLayout;
    final InstructionsScreen instructionsScreen = this;

    private final String instructions1 = "Welcome to Carbon Cruncher: Tiggy's Adventures!\n\n" +
            "Choose from 4 eco-friendly modes of Transport to find gems!\n\n" +
            "Level 1: You are restricted to Zone 1. Public Transport is not unlocked yet.\n\n" +
            "Level 2: Full map and public transport access!\n\n" +
            "Walking: Does not effect your Carbon footprint Score.\n\n" +
            "Bike: Collect those Gems faster, Carbon Footprint Score unaffected.\n\n";
;
    private final String instructions2 = "Scoring:\n\n" +
            "- Gems: +200 points each!\n\n" +
            "- Educational Popups: +5 points.\n\n" +
            "- Bus: Traverse faster, substantial Carbon Footprint (-50 points).\n\n" +
            "- Train: Most Environmentally Friendly Transport, Carbon Footprint Increases moderately (-15 points).\n\n";

    private final String instructions3 = "**Beware!**\n\n" +
            "Fast transport reduces time, but increases your Carbon Footprint.\n\n" +
            "Using the Bus reduces your time remaining by 10 seconds, and the Train reduces your time remaining by 5 seconds.\n\n" +
            "Enjoy exploring, learning, and collecting Gems!\n\n";

    float x, y;
    private int currentInstructions = 1; // To track which set of instructions is being shown

    public InstructionsScreen(EducationGame game) {
        this.game = game;
        this.gameFont = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt"));
        this.buttonSkin = new Skin(Gdx.files.internal("button.json"), new TextureAtlas(Gdx.files.internal("button.atlas")));
        this.buttonNext = new TextButton("Next", buttonSkin, "default");
        this.instructionsLayout = new GlyphLayout();
        updateInstructionsLayout();
    }

    private void updateInstructionsLayout() {
        if (currentInstructions == 1) {
            instructionsLayout.setText(gameFont, instructions1, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
        } else if (currentInstructions == 2) {
            instructionsLayout.setText(gameFont, instructions2, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
        } else {
            instructionsLayout.setText(gameFont, instructions3, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
        }
    }
    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        buttonNext.setPosition((Gdx.graphics.getWidth()  - buttonNext.getWidth()) / 1.05f, (Gdx.graphics.getHeight() - buttonNext.getHeight()) / 1.05f);
        buttonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentInstructions == 1) {
                    currentInstructions = 2;
                    updateInstructionsLayout();
                } else if (currentInstructions == 2) {
                    currentInstructions = 3;
                    updateInstructionsLayout();
                    buttonNext.setText("Back");
                } else {
                    // If there are no more instructions, go back to main menu
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });

        stage.addActor(buttonNext);
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
}