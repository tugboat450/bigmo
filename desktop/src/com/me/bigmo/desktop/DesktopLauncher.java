package com.me.bigmo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.bigmo.BigMo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Big Mo";
        config.width = 480;
        config.height = 800;
        new LwjglApplication(new BigMo(), config);
	}
}
