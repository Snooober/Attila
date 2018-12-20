package com.nick.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilahelpers.AssetLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameBoard {
    private int numCols;
    private int numRows;
    private float boardSpaceLength;
    private List<List<BoardSpace>> boardSpaceRows;
    private int screenWidth;
    private int screenHeight;
    private float padding;
    private float[] initCoords;
    private PlayPiece[] player1_pieces;
    private PlayPiece[] player2_pieces;
    private float pieceWidth;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float factor = screenWidth / 60f;
        padding = Math.min(screenWidth / factor, screenHeight / factor / 1.778f);

        player1_pieces = new PlayPiece[3];
        player2_pieces = new PlayPiece[3];

        genBoardSpaces();
        genBoardSpaceLength();
        setInitCoords();

        pieceWidth = boardSpaceLength - (boardSpaceLength / 20);
        player1_pieces[0] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f - pieceWidth / 2 - pieceWidth - padding, padding, pieceWidth / 2f);
        player1_pieces[1] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f - pieceWidth / 2, padding, pieceWidth / 2f);
        player1_pieces[2] = new PlayPiece(PlayerNum.ONE, screenWidth / 2f - pieceWidth / 2 + pieceWidth + padding, padding, pieceWidth / 2f);
        player2_pieces[0] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f - pieceWidth / 2 - pieceWidth - padding, screenHeight - padding - pieceWidth, pieceWidth / 2f);
        player2_pieces[1] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f - pieceWidth / 2, screenHeight - padding - pieceWidth, pieceWidth / 2f);
        player2_pieces[2] = new PlayPiece(PlayerNum.TWO, screenWidth / 2f - pieceWidth / 2 + pieceWidth + padding, screenHeight - padding - pieceWidth, pieceWidth / 2f);
    }

    public boolean movePieces(final Vector2 touchPos) {
        touchPos.x = touchPos.x - pieceWidth / 2;
        touchPos.y = touchPos.y - pieceWidth / 2;
        for (int i = 0; i < player1_pieces.length; i++) {
            if (player1_pieces[i].getDrawCircle().contains(touchPos)) {
                player1_pieces[i].movePiece(touchPos);
                return true;
            }
        }
        for (int i = 0; i < player2_pieces.length; i++) {
            if (player2_pieces[i].getDrawCircle().contains(touchPos)) {
                player2_pieces[i].movePiece(touchPos);
                return true;
            }
        }
        return false;
    }

    //make boardSpaceRows List
    private void genBoardSpaces() {
        List<BoardSpace> boardSpaceRow = new ArrayList<BoardSpace>();
        for (int i = 0; i < numCols; i++) {
            BoardSpace boardSpace = new BoardSpace(true);
            boardSpaceRow.add(boardSpace);
        }
        boardSpaceRows = new ArrayList<List<BoardSpace>>();
        for (int i = 0; i < numRows; i++) {
            boardSpaceRows.add(boardSpaceRow);
        }
    }

    //determine width, height, boardSpaceLength
    private void genBoardSpaceLength() {
        float extraHeightMargin = screenHeight / 5f;
        float extraWidthMargin = screenWidth / 10f;

        float availableHeight = screenHeight - (padding * numRows + padding) - extraHeightMargin * 2;
        float availableWidth = screenWidth - (padding * numCols + padding) - extraWidthMargin * 2;
        boardSpaceLength = Math.min(availableHeight / numRows, availableWidth / numCols);
    }

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
        renderPlayerPieces(delta, batch);
    }

    private void renderBoard(final float delta, final ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float[] currentCoords = new float[2];
        currentCoords[0] = initCoords[0];
        currentCoords[1] = initCoords[1];

        Iterator<List<BoardSpace>> rowsIt = boardSpaceRows.iterator();
        while (rowsIt.hasNext()) {
            List<BoardSpace> rows = rowsIt.next();
            Iterator<BoardSpace> rowIt = rows.iterator();
            while (rowIt.hasNext()) {
                BoardSpace boardSpace = rowIt.next();

                shapeRenderer.rect(currentCoords[0], currentCoords[1], boardSpaceLength, boardSpaceLength);
                currentCoords[0] = currentCoords[0] + boardSpaceLength + padding;
            }
            //reset x-coord to the left
            //increment y-coord
            currentCoords[0] = initCoords[0];
            currentCoords[1] = currentCoords[1] + padding + boardSpaceLength;
        }

        shapeRenderer.end();
    }

    private void renderPlayerPieces(final float delta, final SpriteBatch batch) {
        batch.begin();

        for (int i = 0; i < player1_pieces.length; i++) {
            if (player1_pieces[i].played) {
                batch.draw(AssetLoader.redPiece, player1_pieces[i].getCircleX(), player1_pieces[i].getCircleY(), player1_pieces[i].getCircleRad() * 2, player1_pieces[i].getCircleRad() * 2);
            } else {

            }
        }
        for (int i = 0; i < player2_pieces.length; i++) {
            if (player2_pieces[i].played) {
                batch.draw(AssetLoader.blackPiece, player2_pieces[i].getCircleX(), player2_pieces[i].getCircleY(), player2_pieces[i].getCircleRad() * 2, player2_pieces[i].getCircleRad() * 2);
            } else {

            }
        }

        batch.end();
    }
}
