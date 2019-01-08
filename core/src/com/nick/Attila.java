package com.nick;

import com.badlogic.gdx.Game;
import com.nick.screens.MenuScreen;

public class Attila extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }


    @Override
    public void dispose() {

    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
