package com.javaeducational.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StationSelectPopup extends Dialog {
    private SelectBox<String> selectBox;

    public StationSelectPopup(String title, Skin skin, String[] stations, Stage stage) {
        super(title, skin);

        selectBox = new SelectBox<String>(skin);
        selectBox.setItems(stations);

        text("Choose your destination:");
        getContentTable().add(selectBox).fillX().uniformX();
        button("OK", "ok");

        setSize(300, 200);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
    }

    public String getSelectedStation() {
        return selectBox.getSelected();
    }
}
