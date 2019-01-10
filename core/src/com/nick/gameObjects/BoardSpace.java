package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BoardSpace {
    private Rectangle rectangle;
    private boolean occupied;
    private boolean exists;

    BoardSpace(final Rectangle rectangle) {
        this.rectangle = rectangle;
        this.occupied = false;
        this.exists = true;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Vector2 getCenter() {
        return new Vector2(rectangle.getX() + rectangle.width / 2, rectangle.getY() + rectangle.height / 2);
    }
}
