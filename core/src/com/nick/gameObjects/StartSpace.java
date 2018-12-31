package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StartSpace extends BoardSpace {

    public StartSpace(final float x, final float y, final float width, final float height) {
        super.setRectangle(new Rectangle(x, y, width, height));
    }
}
