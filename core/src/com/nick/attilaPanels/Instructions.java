package com.nick.attilaPanels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nick.attilaControllers.GameActionsController;
import com.nick.attilaControllers.PlaceActionsController;
import com.nick.attilaControllers.PlayActionsController;
import com.nick.gameObjects.GameBoard;

public class Instructions extends TextPanel {
    private String text;
    private GameActionsController controller;

    public Instructions(GameBoard board, BoardAlign align) {
        super(board, align);
    }

    @Override
    public void render(SpriteBatch batch) {
        controller = board.getController();

        if (board.getEndGameWinner() == null) {
            if (controller instanceof PlaceActionsController) {
                text = "Take turns placing pieces on the board.";
            } else {
                if (((PlayActionsController) controller).isAwaitRemove()) {
                    text = "Choose an un-occupied space to remove from the board.";
                } else {
                    text = "Select a piece to move. Pieces may only be moved in an \"L-shape\".";
                }
            }
        } else {
            return;
        }

        super.drawCenter(text, batch);
    }
}
