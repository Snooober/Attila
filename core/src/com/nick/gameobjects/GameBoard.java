package com.nick.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        padding = 20;

        genBoardSpaces();
        genBoardSpaceLength();
        setInitCoords();
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
        batch.draw(AssetLoader.redPiece, 0, 0);
        batch.end();


    }
}
