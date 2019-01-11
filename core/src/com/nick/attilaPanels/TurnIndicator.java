package com.nick.attilaPanels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nick.gameObjects.GameBoard;
import com.nick.gameObjects.PlayerNum;

public class TurnIndicator extends TextPanel {
    private String text;
    private PlayerNum currentTurn;

    public TurnIndicator(GameBoard board, BoardAlign align) {
        super(board, align);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (board.getEndGameWinner() != null) {
            return;
        }

        currentTurn = board.getCurrentTurn();
        text = currentTurn.toString() + "'s turn";
        super.drawCenter(text, batch);
    }
}
