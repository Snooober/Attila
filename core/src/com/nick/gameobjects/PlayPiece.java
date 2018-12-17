package com.nick.gameobjects;

public class PlayPiece {

    int playerTeam;
    int pos[][];

    public PlayPiece(final int pos[][], final int playerTeam) {
        this.playerTeam = playerTeam;
        this.pos = pos;

    }
}
