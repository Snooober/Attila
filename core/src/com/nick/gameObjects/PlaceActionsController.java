package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class PlaceActionsController extends GameActionsControllerImpl {

    public PlaceActionsController(final GameBoard board) {
        super(board);
    }

    @Override
    public void nextTurn() {
        if (board.getCurrentTurn().equals(PlayerNum.ONE)) {
            board.setCurrentTurn(PlayerNum.TWO);
        } else {
            board.setCurrentTurn(PlayerNum.ONE);
        }
        if (board.allPlayed()) {
            board.nextPhase();
        }
    }

    @Override
    public boolean onTouchUp(final Vector2 touchPos) {
        PlayPiece touchedPiece = board.getTouchedPiece();
        if (touchedPiece != null && touchedPiece.getCircle().contains(touchPos)) {
            if (touchedPiece.setCurrentSpace()) {
                board.nextTurn();
            }
            board.setTouchedPiece(null);
            return true;
        }
        return false;
    }
}
