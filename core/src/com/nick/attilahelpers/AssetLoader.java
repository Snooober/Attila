package com.nick.attilahelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static TextureRegion redPiece;
    public static Texture playerPieces;

    public static void load() {
        playerPieces = new Texture(Gdx.files.internal("marbles.png"));
        playerPieces.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        redPiece = new TextureRegion(playerPieces, 0, 0, 32, 32);
    }

    public static void dispose() {
        playerPieces.dispose();
    }
}
