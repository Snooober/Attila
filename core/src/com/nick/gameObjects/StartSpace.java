package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;

class StartSpace extends BoardSpace {

    public StartSpace(final float x, final float y, final float width, final float height) {
        super(new Rectangle(x, y, width, height));
        super.setOccupied(true);
    }
}
