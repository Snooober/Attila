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
    private List<BoardSpace> boardSpaces;
    private int screenWidth;
    private int screenHeight;
    private float padding;
    private float[] initCoords;
    private PlayPiece[] player1_pieces;
    private PlayPiece[] player2_pieces;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float factor = screenWidth / 60f;
        padding = Math.min(screenWidth / factor, screenHeight / factor / 1.778f);

        genBoardSpaces();
        initPieces();
    }

    private void initPieces() {
        player1_pieces = new PlayPiece[3];
        player2_pieces = new PlayPiece[3];
        player1_pieces[0] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f - boardSpaceLength, boardSpaceLength, boardSpaceLength * 0.4f);
        player1_pieces[1] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f, boardSpaceLength, boardSpaceLength * 0.4f);
        player1_pieces[2] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f + boardSpaceLength, boardSpaceLength, boardSpaceLength * 0.4f);
        player2_pieces[0] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f - boardSpaceLength, screenHeight - boardSpaceLength, boardSpaceLength * 0.4f);
        player2_pieces[1] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f, screenHeight - boardSpaceLength, boardSpaceLength * 0.4f);
        player2_pieces[2] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f + boardSpaceLength, screenHeight - boardSpaceLength, boardSpaceLength * 0.4f);
    }

    public boolean movePieces(final Vector2 touchPos) {
        //touchPos.x = touchPos.x - pieceWidth / 2;
        //touchPos.y = touchPos.y - pieceWidth / 2;
        for (int i = 0; i < player1_pieces.length; i++) {
            if (player1_pieces[i].getCircle().contains(touchPos)) {
                player1_pieces[i].movePiece(touchPos);
                return true;
            }
        }
        for (int i = 0; i < player2_pieces.length; i++) {
            if (player2_pieces[i].getCircle().contains(touchPos)) {
                player2_pieces[i].movePiece(touchPos);
                return true;
            }
        }
        return false;
    }

    public boolean setPieces() {
        for (int i = 0; i < player1_pieces.length; i++) {
            player1_pieces[i].moveToSpace();
        }
        for (int i = 0; i < player2_pieces.length; i++) {
            player2_pieces[i].moveToSpace();
        }

        return true;
    }

    //make board spaces
    private void genBoardSpaces() {
        genBoardSpaceLength();
        setInitCoords();

        boardSpaces = new ArrayList<BoardSpace>();
        float[] currentCoords = new float[2];
        currentCoords[0] = initCoords[0];
        currentCoords[1] = initCoords[1];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rect = new Rectangle(currentCoords[0], currentCoords[1], boardSpaceLength, boardSpaceLength);
                currentCoords[0] = currentCoords[0] + boardSpaceLength + padding;
                BoardSpace boardSpace = new BoardSpace(true, rect);
                boardSpaces.add(boardSpace);
            }

            //reset x-coord
            //increment y-coord
            currentCoords[0] = initCoords[0];
            currentCoords[1] = currentCoords[1] + padding + boardSpaceLength;
        }
    }

    //determine width, height --> boardSpaceLength
    private void genBoardSpaceLength() {
        float extraHeightMargin = screenHeight / 5f;
        float extraWidthMargin = screenWidth / 10f;

        float availableHeight = screenHeight - (padding * numRows + padding) - extraHeightMargin * 2;
        float availableWidth = screenWidth - (padding * numCols + padding) - extraWidthMargin * 2;
        boardSpaceLength = Math.min(availableHeight / numRows, availableWidth / numCols);
    }

    //find initial coordinate for first BoardSpace
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
            Rectangle rect = boardSpace.getRectangle();
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        shapeRenderer.end();
    }

    private void renderPlayerPieces(final float delta, final SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //TODO remove
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(player1_pieces[0].getCircleX(), player1_pieces[0].getCircleY(), player1_pieces[0].getCircleRad());
        shapeRenderer.end();

        batch.begin();

        for (int i = 0; i < player1_pieces.length; i++) {
            if (player1_pieces[i].played) {
                player1_pieces[i].drawPiece(batch);

                //if touching a BoardSpace, then associate piece with that space
                Iterator<BoardSpace> boardSpaceIt = boardSpaces.iterator();
                while (boardSpaceIt.hasNext()) {
                    BoardSpace boardSpace = boardSpaceIt.next();
                    if (boardSpace.getRectangle().contains(player1_pieces[i].getCircleCenter())) {
                        player1_pieces[i].setCurrentSpace(boardSpace);
                    }
                }
            }
        }

        for (int i = 0; i < player2_pieces.length; i++) {
            if (player2_pieces[i].played) {
                player2_pieces[i].drawPiece(batch);
            }
        }

        batch.end();
    }
}
