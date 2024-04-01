package com.javaeducational.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.javaeducational.game.tools.EducationGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(EducationGame.WIDTH, EducationGame.HEIGHT);
		config.setResizable(false);
		config.setTitle("Educational Game");
		new Lwjgl3Application(new EducationGame(), config);
	}
}
