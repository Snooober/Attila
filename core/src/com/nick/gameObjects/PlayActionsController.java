package com.nick.gameObjects;

import com.badlogic.gdx.graphics.Color;
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
            //clear playable color highlight
            for (BoardSpace space :
                    board.getGameBoardSpaceMap().values()) {
                ((GameBoardSpace) space).setPlayableColor(Color.GRAY);
            }

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
        if (board.getCurrentTurn().equals(PlayerNum.RED)) {
            if (hasPlayable(PlayerNum.BLACK)) {
                board.setCurrentTurn(PlayerNum.BLACK);
            } else {
                board.endGame(PlayerNum.RED);
            }
        } else {
            if (hasPlayable(PlayerNum.RED)) {
                board.setCurrentTurn(PlayerNum.RED);
            } else {
                board.endGame(PlayerNum.BLACK);
            }
        }
        allUnplayed();
    }

    private boolean hasPlayable(PlayerNum playerNum) {
        PlayPiece[][] playPieces = board.getPlayerPieces();
        for (int i = 0; i < playPieces[playerNum.getPlayerIndex()].length; i++) {
            PlayPiece playPiece = playPieces[playerNum.getPlayerIndex()][i];
            if (!playPiece.getPlayableSpaces().isEmpty()) {
                return true;
            }
        }
        return false;
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
                    nextTurn();
                    return true;
                }
            }
        } else {
            PlayPiece[][] playerPieces = board.getPlayerPieces();

            //find who's turn it is
            int playerNumIndex = board.getCurrentTurn().getPlayerIndex();

            //set touchedPiece to PlayerPiece() being dragged
            for (int i = 0; i < playerPieces[playerNumIndex].length; i++) {
                if (playerPieces[playerNumIndex][i].getCircle().contains(touchPos) && !playerPieces[playerNumIndex][i].isPlayed()) {
                    board.setTouchedPiece(playerPieces[playerNumIndex][i]);
                    break;
                }
            }

            PlayPiece touchedPiece = board.getTouchedPiece();

            if (touchedPiece == null || touchedPiece.isMoving()) {
                return false;
            }

            for (BoardSpace space :
                    board.getGameBoardSpaceMap().values()) {
                ((GameBoardSpace) space).setPlayableColor(Color.DARK_GRAY);
            }
            for (BoardSpace space :
                    touchedPiece.getPlayableSpaces()) {
                ((GameBoardSpace) space).setPlayableColor(Color.GRAY);
            }

            return true;
        }

        return false;
    }
}
