package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EduPops extends Dialog {
    Stage stage;

    public EduPops(String title, Skin skin, String text, Stage stage) {
        super(title, skin);
        this.stage = stage;

        Gdx.input.setInputProcessor(stage);

        text(text);
        
        // Add "Close" button that returns true when clicked
        TextButton closeButton = new TextButton("Close", skin);
        button(closeButton, true);

        // Set the size and position of the popup box
        setSize(300, 150);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
    }
}
