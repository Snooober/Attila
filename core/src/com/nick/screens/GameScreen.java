package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

    }

    private void genTurnIndicator() {



    }

    private void renderTurnIndicator() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0,0,50, 100);
        shapeRenderer.end();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        board.render(delta, batch, shapeRenderer);

        float xCoord = screenWidth - board.getDimensions().rightMarginLengthX;
        Vector3 vector3 = new Vector3(xCoord,0,0);
        camera.unproject(vector3);
        shapeRenderer.setProjectionMatrix(new Matrix4(new Quaternion(vector3,0)));
        renderTurnIndicator();
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
