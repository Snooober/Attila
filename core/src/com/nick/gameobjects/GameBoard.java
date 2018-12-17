package com.nick.gameobjects;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int numCols;
    private int numRows;
    private float boardSpaceLength;
    private List<List<BoardSpace>> boardSpaceRows;
    private int screenWidth;
    private int screenHeight;
    private float width;
    private float height;
    private float padding;
    private float[] coords;

    public GameBoard(final int numCols, final int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        padding = 50;

        genBoardSpaces();
        genDimensions();
        getInitialCoord();
    }

    public float getBoardSpaceLength() {
        return boardSpaceLength;
    }

    public float getPadding() {
        return padding;
    }

    public float[] getCoords() {
        return coords;
    }

    public List<List<BoardSpace>> getBoardSpaceRows() {
        return boardSpaceRows;
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
    private void genDimensions() {
        float availableHeight = screenHeight - (padding * numRows + padding);
        float availableWidth = screenWidth - (padding * numCols + padding);
        boardSpaceLength = Math.min(availableHeight / numRows, availableWidth / numCols);
    }

    private void getInitialCoord() {
        coords = new float[2];

        //get x coord
        if (numCols % 2 == 0) {
            float distFromMidline = ((float) numCols * boardSpaceLength / 2) + padding * numCols / 2 - padding + padding / 2;
            coords[0] = (float) screenWidth / 2 - distFromMidline;
        } else {
            float distFromMidLine = (float) Math.floor((double) numCols / 2) * boardSpaceLength + padding * (float) Math.floor((double) numCols / 2) + boardSpaceLength / 2;
            coords[0] = (float) screenWidth / 2 - distFromMidLine;
        }

        //get y coord
        if (numRows % 2 == 0) {
            float distFromMidline = ((float) numRows * boardSpaceLength / 2) + padding * numRows / 2 - padding + padding / 2;
            coords[1] = (float) screenHeight / 2 - distFromMidline;
        } else {
            float distFromMidLine = (float) Math.floor((double) numRows / 2) * boardSpaceLength + padding * (float) Math.floor((double) numRows / 2) + boardSpaceLength / 2;
            coords[1] = (float) screenHeight / 2 - distFromMidLine;
        }
    }
}
