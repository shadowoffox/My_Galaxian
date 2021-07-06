package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.ButtonExit;
import com.mygdx.game.sprite.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private Game game;
 private static final int COUNT = 13;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private  Music music;
    public MenuScreen(Game game) {
        this.game = game;

    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("images/ewq.png");
        background = new Background(bg,COUNT,490,350);

        atlas = new TextureAtlas("images/buttons.txt");

        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/through space.ogg"));
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);

        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.anidraw(batch);

        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        update(delta);
    }



    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return false;
    }

}
