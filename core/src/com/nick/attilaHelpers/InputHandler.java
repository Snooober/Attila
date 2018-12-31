package com.nick.attilaHelpers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nick.screens.GameScreen;

public class InputHandler extends InputAdapter {
    private GameScreen screen;

    public InputHandler(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        screen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);

        return screen.board.movePieces(touchPos2);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        screen.camera.unproject(touchPos);
        Vector2 touchPos2 = new Vector2(touchPos.x, touchPos.y);

        if (screen.board.touchedPiece != null && screen.board.touchedPiece.getCircle().contains(touchPos2)) {
            screen.board.setPiece(screen.board.touchedPiece);
            screen.board.touchedPiece = null;
            return true;
        }

        return false;
    }

}
