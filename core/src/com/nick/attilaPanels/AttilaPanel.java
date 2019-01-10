package com.nick.attilaPanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nick.gameObjects.GameBoard;

public abstract class AttilaPanel {
    protected GameBoard board;
    private float x;
    private float y;
    private float width;
    private float height;

    public AttilaPanel(GameBoard board, BoardAlign align) {
        this.board = board;
        GameBoard.Dimensions boardDimen = board.getDimensions();

        if (align.equals(BoardAlign.RIGHT)) {
            x = Gdx.graphics.getWidth() - boardDimen.rightMarginLengthX;
            width = boardDimen.rightMarginLengthX;
        } else {
            x = 0;
            width = boardDimen.leftMarginLengthX;
        }
        y = 0;
        height = Gdx.graphics.getHeight();
    }

    public abstract void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer);

    public abstract void dispose();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
