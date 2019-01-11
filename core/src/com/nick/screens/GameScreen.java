package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nick.Attila;
import com.nick.attilaHelpers.AssetLoader;
import com.nick.attilaHelpers.InputHandler;
import com.nick.attilaPanels.AttilaPanel;
import com.nick.attilaPanels.BoardAlign;
import com.nick.attilaPanels.Instructions;
import com.nick.attilaPanels.TurnIndicator;
import com.nick.gameObjects.GameBoard;

public class GameScreen implements Screen {
    public OrthographicCamera camera;
    public GameBoard board;
    public Attila game;
    private AttilaPanel turnIndicator;
    private AttilaPanel instructions;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private float screenWidth;
    private float screenHeight;

    public GameScreen(final Attila game) {
        Gdx.input.setInputProcessor(new InputHandler(this));
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        AssetLoader.load();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        board = new GameBoard(5, 4, this);
        turnIndicator = new TurnIndicator(board, BoardAlign.RIGHT);
        instructions = new Instructions(board, BoardAlign.LEFT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        board.render(delta, batch, shapeRenderer);
        turnIndicator.render(batch);
        instructions.render(batch);
    }


    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        shapeRenderer.dispose();
        turnIndicator.dispose();
        AssetLoader.dispose();
    }
}
