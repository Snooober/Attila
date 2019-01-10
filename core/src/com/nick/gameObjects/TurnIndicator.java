package com.nick.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TurnIndicator extends AttilaPanel {
    private BitmapFont font;
    private PlayerNum currentTurn;
    private float screenWidth;
    private float screenHeight;

    public TurnIndicator(GameBoard board, BoardAlign align) {
        super(board, align);
        font = new BitmapFont();
        font.getData().setScale(2);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    @Override
    public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();
        currentTurn = board.getCurrentTurn();

        font.draw(batch, currentTurn.toString() + "'s turn", screenWidth - (super.getWidth() / 2f), screenHeight / 2f);

        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
