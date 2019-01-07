package com.nick.gameObjects;

import com.badlogic.gdx.math.Rectangle;

public class GameBoardSpace extends BoardSpace {
    private Integer[] boardCoord;

    public GameBoardSpace(final Integer[] boardCoord, final Rectangle rectangle) {
        super(rectangle);
        this.boardCoord = boardCoord;
    }

    public Integer[] getBoardCoord() {
        return boardCoord;
    }
}
