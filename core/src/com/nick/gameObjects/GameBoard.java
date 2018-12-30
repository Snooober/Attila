package com.nick.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameBoard {
    private int numCols;
    private int numRows;
    private float boardSpaceLength;
    private BoardSpace[][] startSpaces;
    private List<BoardSpace> boardSpaces;
    private int screenWidth;
    private int screenHeight;
    private float padding;
    private float[] initCoords;
    private PlayPiece[][] playerPieces;
    public PlayPiece touchedPiece;
    private GameState gameState;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float factor = screenWidth / 60f;
        padding = Math.min(screenWidth / factor, screenHeight / factor / 1.778f);
        gameState = new GameState();

        initBoardSpaces();
    }

    private void initBoardSpaces() {
        genBoardSpaceLength();
        setInitCoords();
        genBoardSpaces();

        startSpaces = new BoardSpace[2][3];
        startSpaces[0][0] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2 - boardSpaceLength, boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);
        startSpaces[0][1] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2, boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);
        startSpaces[0][2] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2 + boardSpaceLength, boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);
        startSpaces[1][0] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2 - boardSpaceLength, screenHeight - boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);
        startSpaces[1][1] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2, screenHeight - boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);
        startSpaces[1][2] = new StartSpace(screenWidth / 2f - boardSpaceLength / 2 + boardSpaceLength, screenHeight - boardSpaceLength / 2, boardSpaceLength, boardSpaceLength);

        playerPieces = new PlayPiece[2][3];
        for (int player = 0; player <= 1; player++) {
            for (int i = 0; i < 3; i++) {
                if (player == 0) {
                    playerPieces[player][i] = new PlayPiece(PlayerNum.ONE, startSpaces[player][i]);
                    boardSpaces.add(startSpaces[player][i]);
                } else {
                    playerPieces[player][i] = new PlayPiece(PlayerNum.TWO, startSpaces[player][i]);
                    boardSpaces.add(startSpaces[player][i]);
                }
            }
        }
    }

    //make boardSpaces
    private void genBoardSpaces() {
        boardSpaces = new ArrayList<BoardSpace>();
        float[] currentCoords = new float[2];
        currentCoords[0] = initCoords[0];
        currentCoords[1] = initCoords[1];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rect = new Rectangle(currentCoords[0], currentCoords[1], boardSpaceLength, boardSpaceLength);
                currentCoords[0] = currentCoords[0] + boardSpaceLength + padding;
                GameBoardSpace gameBoardSpace = new GameBoardSpace(numCols, numRows, rect);
                boardSpaces.add(gameBoardSpace);
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

    //gets called during touchDragged() event
    public boolean movePieces(final Vector2 touchPos) {
        if (gameState.getCurrentTurn().equals(PlayerNum.ONE)) {
            for (int i = 0; i < playerPieces[0].length; i++) {
                if (playerPieces[0][i].getCircle().contains(touchPos) && !playerPieces[0][i].isPlayed()) {
                    playerPieces[0][i].movePiece(touchPos);
                    touchedPiece = playerPieces[0][i];
                    return true;
                }
            }
        } else if (gameState.getCurrentTurn().equals(PlayerNum.TWO)) {
            for (int i = 0; i < playerPieces[1].length; i++) {
                if (playerPieces[1][i].getCircle().contains(touchPos) && !playerPieces[1][i].isPlayed()) {
                    playerPieces[1][i].movePiece(touchPos);
                    touchedPiece = playerPieces[1][i];
                    return true;
                }
            }
        }

        return false;
    }

    //gets called during touchUp() event. Snaps PlayPiece's to GameBoardSpace
    public void setPiece(PlayPiece playPiece) {
        PlayerNum playerNum = playPiece.moveToNewSpace();
        if (playerNum != null) {
            gameState.nextTurn();
        }
    }

    public void render(final float delta, final SpriteBatch batch, final ShapeRenderer shapeRenderer) {
        renderBoard(delta, shapeRenderer);
        renderPlayerPieces(delta, batch, shapeRenderer);
    }

    private void renderBoard(final float delta, final ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Iterator<BoardSpace> boardSpaceIt = boardSpaces.iterator();
        while (boardSpaceIt.hasNext()) {
            BoardSpace boardSpace = boardSpaceIt.next();
            if (boardSpace instanceof GameBoardSpace) {
                Rectangle rect = boardSpace.getRectangle();
                shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        }

        shapeRenderer.end();
    }

    private void renderPlayerPieces(final float delta, final SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();

        for (int player = 0; player <= 1; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                playerPieces[player][i].drawPiece(batch);

                //if touching a BoardSpace, then set currentSpace.
                Iterator<BoardSpace> boardSpaceIterator = boardSpaces.iterator();
                while (boardSpaceIterator.hasNext()) {
                    BoardSpace boardSpace = boardSpaceIterator.next();
                    if (boardSpace.getRectangle().contains(playerPieces[player][i].getCircleCenter())) {
                        playerPieces[player][i].setNewSpace(boardSpace);
                    }
                }
            }
        }

        batch.end();
    }
}
