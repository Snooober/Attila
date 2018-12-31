package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameBoardSpace extends BoardSpace {
    private Integer[] boardCoord;

    public GameBoardSpace(final Integer[] boardCoord, final Rectangle rectangle) {
        this.boardCoord = boardCoord;
        super.setRectangle(rectangle);
    }

    public Integer[] getBoardCoord() {
        return boardCoord;
    }
}
