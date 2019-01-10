package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class PlaceActionsController extends GameActionsController {

    public PlaceActionsController(final GameBoard board) {
        super(board);
    }

    @Override
    public void nextTurn() {
        if (board.getCurrentTurn().equals(PlayerNum.RED)) {
            board.setCurrentTurn(PlayerNum.BLACK);
        } else {
            board.setCurrentTurn(PlayerNum.RED);
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
                nextTurn();
            }
            board.setTouchedPiece(null);
            return true;
        }
        return false;
    }
}
