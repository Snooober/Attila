package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameBoardSpace implements BoardSpace {
    private Rectangle rectangle;
    private int boardCoordX;
    private int boardCoordY;

    public GameBoardSpace(final int boardCoordX, final int boardCoordY, final Rectangle rectangle) {
        this.boardCoordX = boardCoordX;
        this.boardCoordY = boardCoordY;
        this.rectangle = rectangle;
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
