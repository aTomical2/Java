package com.javaeducational.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.javaeducational.game.screens.MainMenuScreen;

public class EducationGame extends Game {
	public static final int WIDTH = 1300;
	public static final int HEIGHT = 800;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}
	@Override
	public void render () {
		super.render();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}

	public int getWidth() {
		return this.WIDTH;
	}
	public int getHeight() {
		return this.HEIGHT;
	}
}