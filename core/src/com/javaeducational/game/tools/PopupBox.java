package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PopupBox extends Dialog {
    Stage stage;
    public PopupBox(String title, Skin skin, String text, Stage stage) {
        super(title, skin);
        this.stage = stage;

        Gdx.input.setInputProcessor(stage);
        // Add UI components to the popup box
//        text();
        text(text);
        // Add "Yes" button that returns true when clicked
        button("Yes", true);

        // Add "No" button that returns false when clicked
        button("No", false);

        // Set the size and position of the popup box
        setSize(300, 150);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
    }
}
