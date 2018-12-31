package com.nick.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class GameBoard {
    private int numCols;
    private int numRows;
    private float boardSpaceLength;
    private int screenWidth;
    private int screenHeight;
    private float padding;
    private float[] initCoords;
    private PlayPiece[][] playerPieces;
    public PlayPiece touchedPiece;
    private GameState gameState;
    private Map<Integer[], BoardSpace> boardSpaceMap;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float factor = screenWidth / 60f;
        padding = Math.min(screenWidth / factor, screenHeight / factor / 1.778f);
        gameState = new GameState(this);

        initBoardSpaces();
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
        boardSpaceMap = new HashMap<Integer[], BoardSpace>();
        float[] currentCoords = new float[2];
        currentCoords[0] = initCoords[0];
        currentCoords[1] = initCoords[1];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rect = new Rectangle(currentCoords[0], currentCoords[1], boardSpaceLength, boardSpaceLength);
                Integer[] boardCoord = new Integer[2];
                boardCoord[0] = numCols;
                boardCoord[1] = numRows;
                BoardSpace boardSpace = new GameBoardSpace(boardCoord, rect);
                boardSpaceMap.put(boardCoord, boardSpace);

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

    //gets called during touchDragged() event
    public boolean movePieces(final Vector2 touchPos) {
        if (gameState.getGamePhase().equals(GamePhase.PLACE)) {
            return movePiecesPlacePhase(touchPos);
        } else if (gameState.getGamePhase().equals(GamePhase.PLAY)) {
            return movePiecesPlayPhase(touchPos);
        }
        return false;
    }

    private boolean movePiecesPlacePhase(final Vector2 touchPos) {
        //find who's turn it is
        int playerNumIndex = gameState.getCurrentTurn().getPlayerIndex();

        //find PlayerPiece() being dragged
        for (int i = 0; i < playerPieces[playerNumIndex].length; i++) {
            if (playerPieces[playerNumIndex][i].getCircle().contains(touchPos) && !playerPieces[playerNumIndex][i].isPlayed()) {
                if (playerPieces[playerNumIndex][i].isTouchUp()) {
                    return true;
                }
                playerPieces[playerNumIndex][i].dragPiece(touchPos);
                touchedPiece = playerPieces[playerNumIndex][i];

                //if touching a BoardSpace, then set currentSpace.
                Iterator<BoardSpace> boardSpaceIterator = boardSpaces.iterator();
                BoardSpace onSpace = null;
                while (boardSpaceIterator.hasNext()) {
                    BoardSpace boardSpace = boardSpaceIterator.next();
                    if (boardSpace.getRectangle().contains(playerPieces[playerNumIndex][i].getCircleCenter())) {
                        onSpace = boardSpace;
                        break;
                    }
                }
                if (onSpace != null) {
                    playerPieces[playerNumIndex][i].setNewSpace(onSpace);
                } else {
                    playerPieces[playerNumIndex][i].resetSpace();
                }

                return true;
            }
        }

        return false;
    }

    private boolean movePiecesPlayPhase(final Vector2 touchPos) {
        //find who's turn it is
        int playerNumIndex = gameState.getCurrentTurn().getPlayerIndex();

        //find PlayerPiece() being dragged
        for (int i = 0; i < playerPieces[playerNumIndex].length; i++) {
            if (playerPieces[playerNumIndex][i].getCircle().contains(touchPos)) {
                //TODO here
                if (playerPieces[playerNumIndex][i].isTouchUp()) {
                    return true;
                }
                playerPieces[playerNumIndex][i].dragPiece(touchPos);
                touchedPiece = playerPieces[playerNumIndex][i];

                playerPieces[playerNumIndex][i].findPlayableSpaces(boardSpaces);

            }
        }


        return false;
    }

    //gets called during touchUp() event. Snaps PlayPiece's to GameBoardSpace
    public void setPiece(PlayPiece playPiece) {
        PlayerNum playerNum = playPiece.onTouchUp();
        if (playerNum != null) {
            gameState.nextTurn();
        }
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

    public void render(final float delta, final SpriteBatch batch, final ShapeRenderer shapeRenderer) {
        renderBoard(shapeRenderer);
        renderPlayerPieces(delta, batch);
    }

    private void renderBoard(final ShapeRenderer shapeRenderer) {
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

    private void renderPlayerPieces(final float delta, final SpriteBatch batch) {
        batch.begin();

        for (int player = 0; player <= 1; player++) {
            for (int i = 0; i < playerPieces[player].length; i++) {
                playerPieces[player][i].drawPiece(delta, batch);
            }
        }

        batch.end();
    }
}
