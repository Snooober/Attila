package com.nick.gameObjects;

import com.badlogic.gdx.math.Vector2;

import java.util.Map;

public class PlayActionsController extends GameActionsController {
    private boolean awaitRemove;

    public PlayActionsController(final GameBoard board) {
        super(board);
        awaitRemove = false;
        allUnplayed();
    }

    private void allUnplayed() {
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
                awaitRemove = true;
            }
            board.setTouchedPiece(null);
            return true;
        }
        return false;
    }

    @Override
    public void nextTurn() {
        if (board.getCurrentTurn().equals(PlayerNum.ONE)) {
            board.setCurrentTurn(PlayerNum.TWO);
        } else {
            board.setCurrentTurn(PlayerNum.ONE);
        }
        allUnplayed();
    }

    @Override
    boolean onTouchDrag(Vector2 touchPos) {
        if (awaitRemove) {
            return false;
        } else {
            return super.onTouchDrag(touchPos);
        }
    }

    @Override
    public boolean onTouchDown(Vector2 touchPos) {
        if (awaitRemove) {
            Map<Integer, BoardSpace> gameBoardSpaceMap = board.getGameBoardSpaceMap();
            for (BoardSpace boardSpace : gameBoardSpaceMap.values()) {
                if (boardSpace.getRectangle().contains(touchPos) && boardSpace.isExists() && !boardSpace.isOccupied()) {
                    boardSpace.setExists(false);
                    awaitRemove = false;
                    board.nextTurn();
                    return true;
                }
            }
        }

        return false;
    }
}
