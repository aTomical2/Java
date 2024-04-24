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
            "Modes of Transport:\n" +
            "Choose from 4 eco-friendly modes to find gems!\n" +
            "Level 1: Zone 1 only, no public transport.\n" +
            "Level 2: Full map and public transport!\n" +
            "Walking: No carbon footprint.\n" +
            "Bike: Moderate speed, moderate footprint.\n" +
            "Bus: Faster, +50 points, substantial footprint (-50 points).\n" +
            "Train: Fastest, +25 points, footprint increase (-15 points).\n\n";
    private final String instructions2 = "Scoring:\n" +
            "- Gems: +200 points each!\n" +
            "- Educational Popups: +5 points.\n\n";

    private final String instructions3 = "**Beware!**\n" +
            "Fast transport reduces time, increases footprint.\n" +
            "Bus reduces score by 50 points, train by 15 points.\n" +
            "Enjoy exploring, learning, and collecting gems!\n";


    float x, y;
    private int currentInstructions = 1; // To track which set of instructions is being shown

    public InstructionsScreen(EducationGame game) {
        this.game = game;
        this.gameFont = new BitmapFont(Gdx.files.internal("fonts/Press_Start_2p.fnt"));
        this.buttonSkin = new Skin(Gdx.files.internal("button.json"), new TextureAtlas(Gdx.files.internal("button.atlas")));
        //this.buttonExit = new TextButton("X", buttonSkin, "default");
        this.buttonNext = new TextButton("Next", buttonSkin, "default");
        this.instructionsLayout = new GlyphLayout();
        updateInstructionsLayout();

        //instructionsLayout.setText(gameFont, instructions, com.badlogic.gdx.graphics.Color.WHITE, game.getWidth() / 2, com.badlogic.gdx.utils.Align.center, true);
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
                    buttonNext.setText("Back to Menu");
                } else {
                    // Go back to main menu
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
