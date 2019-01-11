package com.nick.attilaPanels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.nick.gameObjects.GameBoard;

public abstract class TextPanel extends AttilaPanel {
    private BitmapFont font;
    private GlyphLayout layout;

    TextPanel(GameBoard board, BoardAlign align) {
        super(board, align);
        font = new BitmapFont();
        font.getData().setScale(2);
        layout = new GlyphLayout();
    }

    void drawCenter(String text, SpriteBatch batch) {
        layout.setText(font, text, Color.WHITE, super.getWidth(), Align.center, true);
        super.drawCenter(font, layout, batch);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
