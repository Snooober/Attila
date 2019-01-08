package com.nick.attilaHelpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.nick.screens.GameScreen;
import com.nick.screens.MenuScreen;

public class InputEndGame extends InputAdapter {
    private GameScreen gameScreen;

    public InputEndGame(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameScreen.game.setScreen(new MenuScreen(gameScreen.game));
        gameScreen.dispose();
        return true;
    }
}
