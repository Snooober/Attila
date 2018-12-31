package com.nick.gameObjects;

import java.util.Random;

public class GameState {
    private GameBoard gameBoard;
    private PlayerNum currentTurn;
    private GamePhase gamePhase;

    public GameState(final GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        boolean rand = new Random().nextBoolean();
        if (rand) {
            currentTurn = PlayerNum.ONE;
        } else {
            currentTurn = PlayerNum.TWO;
        }
        gamePhase = GamePhase.PLACE;
    }

    public PlayerNum nextTurn() {
        if (currentTurn.equals(PlayerNum.ONE)) {
            currentTurn = PlayerNum.TWO;
        } else {
            currentTurn = PlayerNum.ONE;
        }

        if (gameBoard.allPlayed()) {
            nextPhase();
        }

        return currentTurn;
    }

    private void nextPhase() {
        gamePhase = GamePhase.PLAY;

        //set playableSpaces() Set for each PlayPiece()
        PlayPiece[][] playerPieces = gameBoard.getPlayerPieces();
        for (int player = 0; player < playerPieces.length; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                playerPieces[player][i].setPlayed(false);
                playerPieces[player][i].findPlayableSpaces(gameBoard.gameBoardSpaceMap);
            }
        }
    }

    public PlayerNum getCurrentTurn() {
        return currentTurn;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }
}
