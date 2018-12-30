package com.nick.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    PlayPiece(final PlayerNum playerNum, final BoardSpace startSpace) {
        this.playerNum = playerNum;
        this.played = false;
        this.currentSpace = startSpace;
        this.newSpace = startSpace;
        Rectangle rect = startSpace.getRectangle();
        setDrawCircle(rect.x + rect.width / 2, rect.y + rect.height / 2, rect.width / 2);
    }

    void setNewSpace(BoardSpace newSpace) {
        this.newSpace = newSpace;
    }

    public boolean isPlayed() {
        return played;
    }

    void drawPiece(SpriteBatch batch) {
        if (this.playerNum.equals(PlayerNum.ONE)) {
            batch.draw(AssetLoader.redPiece, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
        } else if (this.playerNum.equals(PlayerNum.TWO)) {
            batch.draw(AssetLoader.blackPiece, drawCircle.x - drawCircle.radius * 1.5f, drawCircle.y - drawCircle.radius * 1.5f, drawCircle.radius * 3f, drawCircle.radius * 3f);
        }
    }

    private void setDrawCircle(final float x, final float y, final float radius) {
        this.drawCircle = new Circle(x, y, radius);
    }

    public Circle getCircle() {
        return drawCircle;
    }

    void movePiece(final Vector2 touchPos) {
        drawCircle.setPosition(touchPos.x, touchPos.y);
    }

    //moves piece to selected GameBoardSpace. If moved, returns the PlayerNum for the piece that is moved. Else returns null
    PlayerNum moveToNewSpace() {
        if (currentSpace != newSpace) {
            movePiece(newSpace.getCenter());
            currentSpace = newSpace;
            played = true;
            return playerNum;
        } else {
            movePiece(currentSpace.getCenter());
        }
        return null;
    }

    Vector2 getCircleCenter() {
        return new Vector2(drawCircle.x, drawCircle.y);
    }
}
