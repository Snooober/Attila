package com.nick.gameobjects;

import com.badlogic.gdx.math.Rectangle;

public class BoardSpace {
    private boolean realSpace;
    private PlayedSpace played;
    private Rectangle rectangle;

    public BoardSpace(final boolean realSpace, final Rectangle rectangle) {
        this.realSpace = realSpace;
        this.played = PlayedSpace.EMPTY;
        this.rectangle = rectangle;
    }

    public boolean isRealSpace() {
        return realSpace;
    }

    public PlayedSpace getPlayed() {
        return played;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
