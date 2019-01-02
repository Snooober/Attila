package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class PlayActionsController implements GameActionsController {
    private GameBoard board;

    public PlayActionsController(final GameBoard board) {
        this.board = board;

        //make all PlayerPieces() un-played
        PlayPiece[][] playerPieces = board.getPlayerPieces();
        for (PlayPiece[] pieces :
                playerPieces) {
            for (PlayPiece playPiece :
                    pieces) {
                playPiece.setPlayed(false);
            }
        }
    }

    @Override
    public void nextTurn() {

    }

    @Override
    public boolean onTouchDrag(Vector2 touchPos) {
        return false;
    }

    @Override
    public boolean onTouchUp(Vector2 touchPos) {
        return false;
    }
}
