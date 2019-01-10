package com.nick.gameObjects;

public enum PlayerNum {
    RED(0),
    BLACK(1);

    private int playerIndex;

    PlayerNum(final int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
