package com.nick.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class GameBoardSpace extends BoardSpace {
    private Integer[] boardCoord;
    private Color color;

    public GameBoardSpace(final Integer[] boardCoord, final Rectangle rectangle) {
        super(rectangle);
        this.color = Color.GRAY;
        this.boardCoord = boardCoord;
    }

    public Color getColor() {
        return color;
    }

    public void setPlayableColor(Color color) {
        this.color = color;
    }

    public Integer[] getBoardCoord() {
        return boardCoord;
    }
}
