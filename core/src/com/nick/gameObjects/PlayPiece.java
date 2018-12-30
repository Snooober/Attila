package com.nick.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.AssetLoader;

public class PlayPiece {

    private boolean played;
    private PlayerNum playerNum;
    private Circle drawCircle;
    private BoardSpace currentSpace;
    private BoardSpace newSpace;
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

    void drawPiece(final float delta, SpriteBatch batch) {
        float speed = 200 * delta;
        if (touchUp) {
            float distToX = newSpace.getCenter().x - drawCircle.x;
            float distToY = newSpace.getCenter().y - drawCircle.y;

            float distance = (float) Math.sqrt(distToX * distToX + distToY * distToY);
            distToX = distToX / distance;
            distToY = distToY / distance;

            float travelX = distToX * speed;
            float travelY = distToY * speed;

            float distTravel = (float) Math.sqrt(travelX * travelX + travelY * travelY);
            if (distTravel > distance) {
                drawCircle.x = distToX;
                drawCircle.y = distToY;
            } else {
                drawCircle.x = drawCircle.x + travelX;
                drawCircle.y = drawCircle.y + travelY;
            }
        }
        if (getCircleCenter().equals(newSpace.getCenter())) {
            touchUp = false;
        }

        batch.draw(pieceTexture, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
    }

    private void setDrawCircle(final float x, final float y, final float radius) {
        this.drawCircle = new Circle(x, y, radius);
    }

    public Circle getCircle() {
        return drawCircle;
    }

    void dragPiece(final Vector2 touchPos) {
        if (!touchUp) {
            drawCircle.setPosition(touchPos.x, touchPos.y);
        }
    }

    //moves piece to selected GameBoardSpace. If moved, returns the PlayerNum for the piece that is moved. Else returns null
    PlayerNum onTouchUp() {
        touchUp = true;
        if (currentSpace != newSpace) {
            currentSpace = newSpace;
            played = true;
            return playerNum;
        }
        return null;
    }

    void setNewSpace(BoardSpace newSpace) {
        if (newSpace instanceof StartSpace) {
            return;
        }
        this.newSpace = newSpace;
    }

    void resetSpace() {
        this.newSpace = currentSpace;
    }

    Vector2 getCircleCenter() {
        return new Vector2(drawCircle.x, drawCircle.y);
    }
}
