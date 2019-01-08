package com.nick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.nick.Attila;

public class MenuScreen implements Screen {
    private Stage stage;
    private BitmapFont font;
    private OrthographicCamera camera;
    private int screenWidth;
    private int screenHeight;

    public MenuScreen(final Attila game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.font = new BitmapFont();
        font.getData().setScale(4);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        TextButton startButton = new TextButton("Start", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        startButton.setX(screenWidth / 2f, Align.center);
        startButton.setY(screenHeight / 2f);
        exitButton.setX(screenWidth / 2f, Align.center);
        exitButton.setY(startButton.getY() - startButton.getHeight());

        stage.addActor(startButton);
        stage.addActor(exitButton);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                MenuScreen.this.dispose();
            }
        });
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.this.dispose();
                Gdx.app.exit();
                System.exit(0);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
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
