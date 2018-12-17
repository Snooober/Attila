package com.nick.gameobjects;

public class BoardSpace {
    private boolean playable;
    private PlayedSpace played;

    public BoardSpace(final boolean playable) {
        this.playable = playable;
        this.played = PlayedSpace.EMPTY;
    }

    public boolean isPlayable() {
        return playable;
    }

    public PlayedSpace getPlayed() {
        return played;
    }
}
