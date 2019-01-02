package com.nick.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.AssetLoader;

import java.util.*;

public class PlayPiece {

    private boolean played;
    private Circle drawCircle;
    private BoardSpace currentSpace;
    private BoardSpace newSpace;
    private boolean moving;
    private TextureRegion pieceTexture;
    private GameBoard board;

    PlayPiece(final GameBoard board, final PlayerNum playerNum, final BoardSpace startSpace) {
        this.board = board;
        this.currentSpace = startSpace;
        this.newSpace = startSpace;
        this.played = false;
        this.moving = false;
        if (playerNum.equals(PlayerNum.ONE)) {
            pieceTexture = AssetLoader.redPiece;
        } else {
            pieceTexture = AssetLoader.blackPiece;
        }
        Rectangle rect = startSpace.getRectangle();
        setDrawCircle(rect.x + rect.width / 2, rect.y + rect.height / 2, rect.width / 2);
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    void drawPiece(final float delta, SpriteBatch batch) {
        moveToNewSpace(delta);
        batch.draw(pieceTexture, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
    }

    private void moveToNewSpace(final float delta) {
        float speed = 3500 * delta;
        if (moving) {
            float distToX = currentSpace.getCenter().x - drawCircle.x;
            float distToY = currentSpace.getCenter().y - drawCircle.y;

            float distance = (float) Math.sqrt(distToX * distToX + distToY * distToY);
            distToX = distToX / distance;
            distToY = distToY / distance;

            float travelX = distToX * speed;
            float travelY = distToY * speed;

            float distTravel = (float) Math.sqrt(travelX * travelX + travelY * travelY);
            if (distTravel > distance || distance == 0) {
                drawCircle.x = currentSpace.getCenter().x;
                drawCircle.y = currentSpace.getCenter().y;
                moving = false;
            } else {
                drawCircle.x = drawCircle.x + travelX;
                drawCircle.y = drawCircle.y + travelY;
            }
        }
    }

    private void setDrawCircle(final float x, final float y, final float radius) {
        this.drawCircle = new Circle(x, y, radius);
    }

    public Circle getCircle() {
        return drawCircle;
    }

    void dragPiece(final Vector2 touchPos) {
        drawCircle.setPosition(touchPos.x, touchPos.y);
    }

    //sets currentSpace to newSpace if they are not equal. Returns true if currentSpace changes.
    public boolean setCurrentSpace() {
        if (!moving) {
            moving = true;
            if (currentSpace != newSpace) {
                currentSpace.setOccupied(false);
                currentSpace = newSpace;
                currentSpace.setOccupied(true);
                played = true;

                return true;
            }
        }
        return false;
    }

    void setNewSpace(BoardSpace newSpace) {
        this.newSpace = newSpace;
    }

    void resetNewSpace() {
        this.newSpace = currentSpace;
    }

    Vector2 getCircleCenter() {
        return new Vector2(drawCircle.x, drawCircle.y);
    }

    public Set<BoardSpace> getPlayableSpaces() {
        Set<BoardSpace> playableSpaces = new HashSet<BoardSpace>();
        Map<Integer, BoardSpace> gameBoardSpaceMap = board.getGameBoardSpaceMap();

        Integer[] currentBoardCoord;
        if (currentSpace instanceof GameBoardSpace) {
            currentBoardCoord = ((GameBoardSpace) currentSpace).getBoardCoord();
        } else {
            Iterator<BoardSpace> boardSpaceIt = gameBoardSpaceMap.values().iterator();
            while (boardSpaceIt.hasNext()) {
                BoardSpace potentialSpace = boardSpaceIt.next();
                if (!potentialSpace.isOccupied()) {
                    playableSpaces.add(potentialSpace);
                }
            }
            return playableSpaces;
        }

        Integer[] moveValues = new Integer[4];
        moveValues[0] = -2;
        moveValues[1] = -1;
        moveValues[2] = 1;
        moveValues[3] = 2;

        for (int x = 0; x < moveValues.length; x++) {
            for (int y = 0; y < moveValues.length; y++) {
                Integer[] playableCoord = new Integer[2];
                playableCoord[0] = currentBoardCoord[0] + moveValues[x];
                playableCoord[1] = currentBoardCoord[1] + moveValues[y];
                BoardSpace potentialSpace = board.getGameBoardSpaceMap().get(Arrays.hashCode(playableCoord));
                if (potentialSpace != null && !potentialSpace.isOccupied() && Math.abs(moveValues[x]) + Math.abs(moveValues[y]) == 3) {
                    playableSpaces.add(board.getGameBoardSpaceMap().get(Arrays.hashCode(playableCoord)));
                }
            }
        }
        return playableSpaces;
    }
}
