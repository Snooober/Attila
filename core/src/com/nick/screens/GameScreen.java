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
import com.nick.gameObjects.GameBoard;

public class GameScreen implements Screen {
    public OrthographicCamera camera;
    public GameBoard board;
    public Attila game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

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

        board = new GameBoard(5, 4, this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        board.render(delta, batch, shapeRenderer);
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
        AssetLoader.dispose();
    }
}
