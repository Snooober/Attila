package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nick.Attila;
import com.nick.attilaHelpers.AssetLoader;
import com.nick.attilaHelpers.InputHandler;
import com.nick.gameObjects.GameBoard;

public class GameScreen implements Screen {
    public OrthographicCamera camera;
    public GameBoard board;
    private Attila game;
    private ShapeRenderer shapeRenderer;
    private int screenWidth;
    private int screenHeight;

    public GameScreen(final Attila game) {
        Gdx.input.setInputProcessor(new InputHandler(this));
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        shapeRenderer = new ShapeRenderer();
        AssetLoader.load();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        board = new GameBoard(5, 4);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        board.render(delta, game.batch, shapeRenderer);
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
        shapeRenderer.dispose();
        AssetLoader.dispose();
    }
}
