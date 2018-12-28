package com.nick.attila.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nick.Attila;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Graphics.DisplayMode[] displayModes = LwjglApplicationConfiguration.getDisplayModes();
        int width = displayModes[displayModes.length - 1].width;
        int height = displayModes[displayModes.length - 1].height;
        config.width = width - (width / 50);
        config.height = height - (height / 50);
        config.vSyncEnabled = false;
        config.forceExit = true;
        new LwjglApplication(new Attila(), config);
    }
}
