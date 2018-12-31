package com.nick.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.AssetLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayPiece {

    private boolean played;
    private PlayerNum playerNum;
    private Circle drawCircle;
    private BoardSpace currentSpace;
    private BoardSpace newSpace;

    public boolean isTouchUp() {
        return touchUp;
    }

    private boolean touchUp;
    private TextureRegion pieceTexture;

    PlayPiece(final PlayerNum playerNum, final BoardSpace startSpace) {
        this.playerNum = playerNum;
        this.played = false;
        this.currentSpace = startSpace;
        this.newSpace = startSpace;
        this.touchUp = false;
        if (playerNum.equals(PlayerNum.ONE)) {
            pieceTexture = AssetLoader.redPiece;
        } else {
            pieceTexture = AssetLoader.blackPiece;
        }
        Rectangle rect = startSpace.getRectangle();
        setDrawCircle(rect.x + rect.width / 2, rect.y + rect.height / 2, rect.width / 2);
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    void drawPiece(final float delta, SpriteBatch batch) {
        moveToSpace(delta);
        batch.draw(pieceTexture, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
    }

    private void moveToSpace(final float delta) {
        float speed = 3500 * delta;
        if (touchUp) {
            float distToX = newSpace.getCenter().x - drawCircle.x;
            float distToY = newSpace.getCenter().y - drawCircle.y;

            float distance = (float) Math.sqrt(distToX * distToX + distToY * distToY);
            distToX = distToX / distance;
            distToY = distToY / distance;

            float travelX = distToX * speed;
            float travelY = distToY * speed;

            float distTravel = (float) Math.sqrt(travelX * travelX + travelY * travelY);
            if (distTravel > distance || distance == 0) {
                drawCircle.x = newSpace.getCenter().x;
                drawCircle.y = newSpace.getCenter().y;
                touchUp = false;
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

    //moves piece to selected GameBoardSpace. If moved, returns the PlayerNum for the piece that is moved. Else returns null
    PlayerNum onTouchUp() {
        if (!touchUp) {
            touchUp = true;
            if (currentSpace != newSpace) {
                currentSpace = newSpace;
                played = true;
                return playerNum;
            }
        }
        return null;
    }

    void setNewSpace(BoardSpace newSpace) {
        this.newSpace = newSpace;
    }

    void resetSpace() {
        this.newSpace = currentSpace;
    }

    Vector2 getCircleCenter() {
        return new Vector2(drawCircle.x, drawCircle.y);
    }

    public List<BoardSpace> findPlayableSpaces(final Map<Integer[], BoardSpace> boardSpaceMap) {
        Integer[] currentBoardCoord = ((GameBoardSpace) currentSpace).getBoardCoord();
        Set<BoardSpace> playableSpaces = new HashSet<BoardSpace>();
        //TODO

        for (int xAdd = 1; xAdd<=2; xAdd++) {
            for (int yAdd = 1; yAdd<=2; yAdd++) {
                Integer[] playableCoord = new Integer[2];
                playableCoord[0] = currentBoardCoord[0]+xAdd;
                playableCoord[1] = currentBoardCoord[1]+yAdd;
                playableSpaces.add(boardSpaceMap.get(playableCoord));
            }
        }


    }
}
