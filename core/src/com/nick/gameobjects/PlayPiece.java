package com.nick.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class PlayPiece {

    public boolean played;
    private PlayerNum playerNum;
    private Circle drawCircle;

    public PlayPiece(final PlayerNum playerNum, final float x, final float y, final float radius) {
        this.playerNum = playerNum;
        this.played = true;
        setDrawCircle(x, y, radius);
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

    public Circle getDrawCircle() {
        return drawCircle;
    }

    public void movePiece(final Vector2 touchPos) {
        drawCircle.setPosition(touchPos.x, touchPos.y);
    }
}
