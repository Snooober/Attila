package com.nick.gameObjects;

import java.util.Random;

public class GameState {
    private PlayerNum currentTurn;

    public GameState() {
        boolean rand = new Random().nextBoolean();
        if (rand) {
            currentTurn = PlayerNum.ONE;
        } else {
            currentTurn = PlayerNum.TWO;
        }
    }

    public PlayerNum nextTurn() {
        if (currentTurn.equals(PlayerNum.ONE)) {
            currentTurn = PlayerNum.TWO;
        } else {
            currentTurn = PlayerNum.ONE;
        }

        return currentTurn;
    }

    public PlayerNum getCurrentTurn() {
        return currentTurn;
    }

}
