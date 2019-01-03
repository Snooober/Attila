package com.nick.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.InputSwitchListener;
import com.nick.attilaHelpers.InputSwitcher;
import com.nick.attilaHelpers.RemoveSpaceInputHandler;

public class PlayActionsController extends GameActionsControllerImpl implements InputSwitchListener {
    @Override
    public void switchInput() {

    }

    public PlayActionsController(final GameBoard board) {
        super(board);

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
    public boolean onTouchUp(final Vector2 touchPos) {
        PlayPiece touchedPiece = board.getTouchedPiece();
        if (touchedPiece != null && touchedPiece.getCircle().contains(touchPos)) {
            if (touchedPiece.setCurrentSpace()) {
                //TODO player chooses empty space to remove


                return true;
            }
        }
        return false;
    }
}
