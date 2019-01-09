package com.nick.attilaHelpers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nick.screens.GameScreen;

public class InputHandler extends InputAdapter {
    private GameScreen gameScreen;

    public InputHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        gameScreen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);

        return gameScreen.board.onTouchDrag(touchPos2);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        gameScreen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);

        return gameScreen.board.onTouchUp(touchPos2);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        gameScreen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);

        return gameScreen.board.onTouchDown(touchPos2);
    }
}
