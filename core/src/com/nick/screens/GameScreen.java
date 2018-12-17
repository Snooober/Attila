package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nick.attila.Attila;
import com.nick.gameobjects.BoardSpace;
import com.nick.gameobjects.GameBoard;

import java.util.List;

public class GameScreen implements Screen {
    List<List<BoardSpace>> boardSpaceRows;
    float[] currentCoords;
    float[] initCoords;
    float boardSpaceLength;
    float padding;
    private Attila game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private int screenWidth;
    private int screenHeight;
    private GameBoard board;

    public GameScreen(final Attila game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        shapeRenderer = new ShapeRenderer();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        board = new GameBoard(5, 4);
        boardSpaceRows = board.getBoardSpaceRows();
        currentCoords = board.getCoords().clone();
        initCoords = currentCoords.clone();
        boardSpaceLength = board.getBoardSpaceLength();
        padding = board.getPadding();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        currentCoords = board.getCoords().clone();
        initCoords = board.getCoords().clone();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();

    }
}
