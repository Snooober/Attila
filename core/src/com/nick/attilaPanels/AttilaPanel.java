package com.nick.attilaPanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nick.gameObjects.GameBoard;

public abstract class AttilaPanel {
    protected GameBoard board;
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float width;
    private float height;

    AttilaPanel(GameBoard board, BoardAlign align) {
        this.board = board;
        GameBoard.Dimensions boardDimen = board.getDimensions();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (align.equals(BoardAlign.RIGHT)) {
            xMin = screenWidth - boardDimen.rightMarginLengthX;
            xMax = screenWidth;
        } else {
            xMin = 0 + board.getPadding();
            xMax = boardDimen.leftMarginLengthX;
        }
        yMin = 0;
        yMax = screenHeight;
        width = xMax - xMin;
        height = yMax - yMin;
    }

    public abstract void render(SpriteBatch batch);

    protected void drawCenter(BitmapFont font, GlyphLayout layout, SpriteBatch batch) {
        batch.begin();

        font.draw(batch, layout, xMin, yMax - (yMax - yMin) / 2);

        batch.end();
    }

    public abstract void dispose();

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
