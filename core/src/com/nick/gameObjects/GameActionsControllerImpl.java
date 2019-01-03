package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.InputSwitchListener;
import com.nick.attilaHelpers.InputSwitcher;

import java.util.Iterator;

public abstract class GameActionsControllerImpl implements  GameActionsController {
    GameBoard board;

    public GameActionsControllerImpl(GameBoard board) {
        this.board = board;
    }

    @Override
    public boolean onTouchDrag(Vector2 touchPos) {
        PlayPiece[][] playerPieces = board.getPlayerPieces();
        PlayPiece touchedPiece = board.getTouchedPiece();

        //find who's turn it is
        int playerNumIndex = board.getCurrentTurn().getPlayerIndex();

        //set touchedPiece to PlayerPiece() being dragged
        if (touchedPiece == null) {
            for (int i = 0; i < playerPieces[playerNumIndex].length; i++) {
                if (playerPieces[playerNumIndex][i].getCircle().contains(touchPos) && !playerPieces[playerNumIndex][i].isPlayed()) {
                    board.setTouchedPiece(playerPieces[playerNumIndex][i]);
                }
            }
        }

        if (touchedPiece == null || touchedPiece.isMoving()) {
            return false;
        }

        touchedPiece.dragPiece(touchPos);

        //if touchedPiece is touching a playable BoardSpace(), then set newSpace
        Iterator<BoardSpace> playableSpaceIt = touchedPiece.getPlayableSpaces().iterator();
        BoardSpace onSpace = null;
        while (playableSpaceIt.hasNext()) {
            BoardSpace potentialSpace = playableSpaceIt.next();
            if (potentialSpace.getRectangle().contains(touchedPiece.getCircleCenter())) {
                onSpace = potentialSpace;
                break;
            }
        }
        if (onSpace != null) {
            touchedPiece.setNewSpace(onSpace);
        } else {
            touchedPiece.resetNewSpace();
        }

        return true;
    }

    @Override
    public void nextTurn() {

    }

    @Override
    public boolean onTouchUp(Vector2 touchPos) {
        return false;
    }
}
