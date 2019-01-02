package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;

public interface GameActionsController {
    boolean onTouchDrag(final Vector2 touchPos);

    void nextTurn();

    boolean onTouchUp(final Vector2 touchPos);
}
