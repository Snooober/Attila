package com.nick.attilaHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static TextureRegion redPiece;
    public static TextureRegion blackPiece;
    private static Texture playerPieces;

    public static void load() {
        playerPieces = new Texture(Gdx.files.internal("marbles.png"));
        playerPieces.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        redPiece = new TextureRegion(playerPieces, 0, 32, 32, 32);
        blackPiece = new TextureRegion(playerPieces, 32, 32, 32, 32);
    }

    public static void dispose() {
        playerPieces.dispose();
    }
}