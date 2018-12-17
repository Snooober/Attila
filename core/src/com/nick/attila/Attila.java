package com.nick.attila;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nick.screens.MenuScreen;

public class Attila extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }


    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
