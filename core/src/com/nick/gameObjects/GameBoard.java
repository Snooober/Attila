package com.nick.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameBoard {
    private PlayPiece touchedPiece;
    private Map<Integer, BoardSpace> gameBoardSpaceMap;
    private PlayerNum currentTurn;
    private GameActionsController controller;
    private int numCols;
    private int numRows;
    private int screenWidth;
    private int screenHeight;
    private float padding;
    private float boardSpaceLength;
    private float[] initCoords;
    private PlayPiece[][] playerPieces;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float factor = screenWidth / 60f;
        padding = Math.min(screenWidth / factor, screenHeight / factor / 1.778f);
        touchedPiece = null;
        controller = new PlaceActionsController(this);
        init();
    }

    public Map<Integer, BoardSpace> getGameBoardSpaceMap() {
        return gameBoardSpaceMap;
    }

    public PlayPiece getTouchedPiece() {
        return touchedPiece;
    }

    public void setTouchedPiece(PlayPiece touchedPiece) {
        this.touchedPiece = touchedPiece;
    }

    public PlayerNum getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(PlayerNum currentTurn) {
        this.currentTurn = currentTurn;
    }

    private void init() {
        initBoardSpaces();
        initPlayerTurn();
    }

    private void initBoardSpaces() {
        genBoardSpaceLength();
        setInitCoords();
        genBoardSpaces();

        BoardSpace[][] startSpaces = new BoardSpace[2][3];
        float pieceSize = boardSpaceLength * 0.75f;
        startSpaces[0][0] = new StartSpace(screenWidth / 2f - pieceSize / 2 - pieceSize, pieceSize / 2, pieceSize, pieceSize);
        startSpaces[0][1] = new StartSpace(screenWidth / 2f - pieceSize / 2, pieceSize / 2, pieceSize, pieceSize);
        startSpaces[0][2] = new StartSpace(screenWidth / 2f - pieceSize / 2 + pieceSize, pieceSize / 2, pieceSize, pieceSize);
        startSpaces[1][0] = new StartSpace(screenWidth / 2f - pieceSize / 2 - pieceSize, screenHeight - pieceSize / 2 - pieceSize, pieceSize, pieceSize);
        startSpaces[1][1] = new StartSpace(screenWidth / 2f - pieceSize / 2, screenHeight - pieceSize / 2 - pieceSize, pieceSize, pieceSize);
        startSpaces[1][2] = new StartSpace(screenWidth / 2f - pieceSize / 2 + pieceSize, screenHeight - pieceSize / 2 - pieceSize, pieceSize, pieceSize);

        playerPieces = new PlayPiece[2][3];
        for (int player = 0; player < playerPieces.length; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                if (player == 0) {
                    playerPieces[player][i] = new PlayPiece(this, PlayerNum.ONE, startSpaces[player][i]);
                } else {
                    playerPieces[player][i] = new PlayPiece(this, PlayerNum.TWO, startSpaces[player][i]);
                }
            }
        }
    }

    //make gameBoardSpaces
    private void genBoardSpaces() {
        gameBoardSpaceMap = new HashMap<Integer, BoardSpace>();
        float[] currentCoords = new float[2];
        currentCoords[0] = initCoords[0];
        currentCoords[1] = initCoords[1];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rect = new Rectangle(currentCoords[0], currentCoords[1], boardSpaceLength, boardSpaceLength);
                Integer[] boardCoord = new Integer[2];
                boardCoord[0] = j;
                boardCoord[1] = i;
                BoardSpace boardSpace = new GameBoardSpace(boardCoord, rect);
                gameBoardSpaceMap.put(Arrays.hashCode(boardCoord), boardSpace);

                currentCoords[0] = currentCoords[0] + boardSpaceLength + padding;
            }

            //reset x-coord
            //increment y-coord
            currentCoords[0] = initCoords[0];
            currentCoords[1] = currentCoords[1] + padding + boardSpaceLength;
        }
    }

    //find initial coordinate for first GameBoardSpace
    private void setInitCoords() {
        initCoords = new float[2];

        //get x coord
        if (numCols % 2 == 0) {
            float distFromMidline = ((float) numCols * boardSpaceLength / 2) + padding * numCols / 2 - padding + padding / 2;
            initCoords[0] = (float) screenWidth / 2 - distFromMidline;
        } else {
            float distFromMidLine = (float) Math.floor((double) numCols / 2) * boardSpaceLength + padding * (float) Math.floor((double) numCols / 2) + boardSpaceLength / 2;
            initCoords[0] = (float) screenWidth / 2 - distFromMidLine;
        }

        //get y coord
        if (numRows % 2 == 0) {
            float distFromMidline = ((float) numRows * boardSpaceLength / 2) + padding * numRows / 2 - padding + padding / 2;
            initCoords[1] = (float) screenHeight / 2 - distFromMidline;
        } else {
            float distFromMidLine = (float) Math.floor((double) numRows / 2) * boardSpaceLength + padding * (float) Math.floor((double) numRows / 2) + boardSpaceLength / 2;
            initCoords[1] = (float) screenHeight / 2 - distFromMidLine;
        }
    }

    //determine width, height of GameBoardSpace --> boardSpaceLength
    private void genBoardSpaceLength() {
        float extraHeightMargin = screenHeight / 5f;
        float extraWidthMargin = screenWidth / 10f;

        float availableHeight = screenHeight - (padding * numRows + padding) - extraHeightMargin * 2;
        float availableWidth = screenWidth - (padding * numCols + padding) - extraWidthMargin * 2;
        boardSpaceLength = Math.min(availableHeight / numRows, availableWidth / numCols);
    }

    private void initPlayerTurn() {
        boolean rand = new Random().nextBoolean();
        if (rand) {
            currentTurn = PlayerNum.ONE;
        } else {
            currentTurn = PlayerNum.TWO;
        }
    }

    //gets called during touchDragged() event
    public boolean onTouchDrag(final Vector2 touchPos) {
        return controller.onTouchDrag(touchPos);
    }

    public boolean onTouchUp(final Vector2 touchPos) {
        return controller.onTouchUp(touchPos);
    }

    public boolean allPlayed() {
        for (int player = 0; player <= 1; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                if (!playerPieces[player][i].isPlayed()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void nextTurn() {
        controller.nextTurn();
    }

    public void nextPhase() {
        controller = new PlayActionsController(this);
    }

    public void render(final float delta, final SpriteBatch batch, final ShapeRenderer shapeRenderer) {
        renderBoard(shapeRenderer);
        renderPlayerPieces(delta, batch);
    }

    private void renderBoard(final ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (BoardSpace boardSpace : gameBoardSpaceMap.values()) {
            if (boardSpace instanceof GameBoardSpace) {
                Rectangle rect = boardSpace.getRectangle();
                shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        }

        shapeRenderer.end();
    }

    private void renderPlayerPieces(final float delta, final SpriteBatch batch) {
        batch.begin();

        for (int player = 0; player <= 1; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                playerPieces[player][i].drawPiece(delta, batch);
            }
        }

        batch.end();
    }

    public PlayPiece[][] getPlayerPieces() {
        return playerPieces;
    }
}
