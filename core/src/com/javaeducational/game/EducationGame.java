package com.javaeducational.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.javaeducational.game.screens.MainMenuScreen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EducationGame extends Game {
	public static final int WIDTH = 1800;
	public static final int HEIGHT = 900;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		shapeRenderer = new ShapeRenderer();
	}
	@Override
	public void render () {
		super.render();
	}
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	public int getWidth() {
		return this.WIDTH;
	}
	public int getHeight() {
		return this.HEIGHT;
	}
}