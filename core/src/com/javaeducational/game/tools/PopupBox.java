package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PopupBox extends Dialog {
    public PopupBox(String title, Skin skin) {
        super(title, skin);
        
        // Add UI components to the popup box
        text("Level-1 Complete. Ready for Level-2?");
        
        // Add "Yes" button that returns true when clicked
        button("Yes", true);
        
        // Add "No" button that returns false when clicked
        button("No", false);
        
        // Set the size and position of the popup box
        setSize(300, 150);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
        
        // Add listener to close the popup box when the "No" button is clicked
        getButtonTable().getCells().get(1).getActor().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide(); // Close the popup box when the "No" button is clicked
            }
        });
    }
}
