package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StartSpace implements BoardSpace {
    private Rectangle rectangle;

    public StartSpace(final float x, final float y, final float width, final float height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }


    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public Vector2 getCenter() {
        return new Vector2(rectangle.getX() + rectangle.width / 2, rectangle.getY() + rectangle.height / 2);
    }
}
