package com.nick.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.nick.attilaHelpers.AssetLoader;

public class PlayPiece {

    public boolean played;
    private PlayerNum playerNum;
    private Circle drawCircle;
    private BoardSpace currentSpace;

    public PlayPiece(final PlayerNum playerNum, final float x, final float y, final float radius) {
        this.playerNum = playerNum;
        this.played = true;
        this.currentSpace = null;
        setDrawCircle(x, y, radius);
    }

    public BoardSpace getCurrentSpace() {
        return currentSpace;
    }

    public void setCurrentSpace(BoardSpace currentSpace) {
        this.currentSpace = currentSpace;
    }

    public void drawPiece(SpriteBatch batch) {
        if (this.playerNum.equals(PlayerNum.ONE)) {
            batch.draw(AssetLoader.redPiece, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
        } else if (this.playerNum.equals(PlayerNum.TWO)) {
            batch.draw(AssetLoader.blackPiece, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
        }
    }

    public void setDrawCircle(final float x, final float y, final float radius) {
        this.drawCircle = new Circle(x, y, radius);
    }

    public float getCircleX() {
        return drawCircle.x;
    }

    public float getCircleY() {
        return drawCircle.y;
    }

    public float getCircleRad() {
        return drawCircle.radius;
    }

    public Circle getCircle() {
        return drawCircle;
    }

    public void movePiece(final Vector2 touchPos) {
        drawCircle.setPosition(touchPos.x, touchPos.y);
    }

    public void moveToSpace() {
        if (currentSpace == null) {

        } else {
            movePiece(getCurrentSpace().getCenter());
        }
    }

    public Vector2 getCircleCenter() {
        return new Vector2(drawCircle.x, drawCircle.y);
    }
}
