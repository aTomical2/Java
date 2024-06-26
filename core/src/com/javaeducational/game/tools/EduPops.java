package com.javaeducational.game.tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EduPops extends Dialog {
    Stage stage;
    public EduPops(String title, Skin skin, String text, Stage stage) {
        super(title, skin);
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        text(text);
        
        TextButton closeButton = new TextButton("Close", skin);
        button(closeButton, true);

        setSize(300, 150);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
    }
}