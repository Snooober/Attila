package com.nick.gameObjects;

public enum PlayerNum {
    ONE(0),
    TWO(1);

    private int playerIndex;

    PlayerNum(final int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
