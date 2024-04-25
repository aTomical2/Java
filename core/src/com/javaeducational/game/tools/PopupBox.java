package com.javaeducational.game.tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PopupBox extends Dialog {
    Stage stage;
    public PopupBox(String title, Skin skin, String text, Stage stage) {
        super(title, skin);
        this.stage = stage;

        Gdx.input.setInputProcessor(stage);

        text(text);
        button("Yes", true);

        button("No", false);

        setSize(300, 150);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
    }
}