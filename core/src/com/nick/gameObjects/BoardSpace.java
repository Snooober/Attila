package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoardSpace {
    private boolean realSpace;
    private PlayedSpace played;
    private Rectangle rectangle;

    public BoardSpace(final boolean realSpace, final Rectangle rectangle) {
        this.realSpace = realSpace;
        this.played = PlayedSpace.EMPTY;
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getCenter() {
        return new Vector2(rectangle.getX() + rectangle.width / 2, rectangle.getY() + rectangle.height / 2);
    }
}