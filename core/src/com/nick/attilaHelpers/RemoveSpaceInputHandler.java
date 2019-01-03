package com.nick.attilaHelpers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nick.screens.GameScreen;

public class RemoveSpaceInputHandler extends InputAdapter {
    private GameScreen screen;

    public RemoveSpaceInputHandler(final GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        screen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);




    }
}
