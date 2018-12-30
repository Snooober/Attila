package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface BoardSpace {
    Rectangle getRectangle();

    Vector2 getCenter();
}
