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
            gamePhase = GamePhase.PLAY;
        }

        return currentTurn;
    }

    public PlayerNum getCurrentTurn() {
        return currentTurn;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }
}
