package com.mygdx.game.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.MatrixUtils;
import com.mygdx.game.math.Rect;


public abstract class BaseScreen implements Screen, InputProcessor {
 public  SpriteBatch batch;

    Rect worldBounds;
    Rect screenBounds;
    Rect glBownds;
    Vector2 touch;
    Matrix4 worldToGl;
    Matrix3 screenToWorld;
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        touch = new Vector2();
        worldBounds = new Rect();
        screenBounds = new Rect();
        glBownds = new Rect(0f,0f,1f,1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        final float aspect = height / (float)width;

        worldBounds.setWidth(1f);
        worldBounds.setHeight(1f * aspect);

        MatrixUtils.calcTransitionMatrix(worldToGl,worldBounds,glBownds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld,screenBounds,worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() { dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchDown(touch,pointer);
        return false;
    }
    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchDragged(touch,pointer);
        return false;
    }
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
