package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nick.Attila;

public class MenuScreen extends InputAdapter implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Attila game;
    private int screenWidth;
    private int screenHeight;

    private BitmapFont startFont;
    private BitmapFont exitFont;
    private Rectangle startButton;
    private Rectangle exitButton;
    private GlyphLayout startLayout;
    private GlyphLayout exitLayout;

    public MenuScreen(final Attila game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        startLayout = new GlyphLayout();
        exitLayout = new GlyphLayout();
        startButton = new Rectangle();
        exitButton = new Rectangle();
        startFont = new BitmapFont();
        exitFont = new BitmapFont();

        startLayout.setText(startFont, "Start");
        exitLayout.setText(exitFont, "Exit");

        startButton.set(screenWidth / 2f, screenHeight / 2f, startLayout.width, startLayout.height);
        exitButton.set(startButton.getX(), (screenHeight / 2f) + startButton.height, exitLayout.width, exitLayout.height);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        startFont.draw(batch, startLayout, screenWidth / 2f, screenHeight / 2f);
        exitFont.draw(batch, exitLayout, screenWidth / 2f, (screenHeight / 2f) - startButton.height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        startFont.dispose();
        exitFont.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);

        if (startButton.contains(touchPos.x, touchPos.y)) {
            game.setScreen(new GameScreen(game));
            this.dispose();
            return true;
        } else if (exitButton.contains(touchPos.x, touchPos.y)) {
            this.dispose();
            game.dispose();
            Gdx.app.exit();
            System.exit(0);
            return true;
        }
        return false;
    }

    @Override
    public void show() {

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
}
