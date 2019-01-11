package com.nick.attilaPanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.nick.gameObjects.GameBoard;
import com.nick.gameObjects.PlayerNum;

public class TurnIndicator extends AttilaPanel {
    private BitmapFont font;
    private GlyphLayout layout;
    private PlayerNum currentTurn;

    public TurnIndicator(GameBoard board, BoardAlign align) {
        super(board, align);
        font = new BitmapFont();
        font.getData().setScale(2);
        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();

        currentTurn = board.getCurrentTurn();
        layout.setText(font, currentTurn.toString() + "'s turn", Color.WHITE,0, Align.center,false);
        super.drawCenter(font,layout,batch);

        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
