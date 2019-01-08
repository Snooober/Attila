package com.nick.attilaHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class InputEndGame extends InputAdapter {
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.exit();
        System.exit(0);
        return true;
    }
}
