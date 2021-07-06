package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.My_Galaxian;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3f/4f;
	//	float aspect = 9f/16f;
		config.height = 400;
		config.width = (int) (config.height / aspect);
		config.resizable = false;
		new LwjglApplication(new My_Galaxian(), config);
	}


}
