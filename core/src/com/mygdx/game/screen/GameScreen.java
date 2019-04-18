package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Ship;

public class GameScreen extends BaseScreen {
    private static final int COUNT = 13;
    private Texture bg;
    private Texture sh;
    private Background background;
    private Ship ship;
    private Vector2 v;
    private TextureAtlas textureAtlas;


    @Override
    public void show() {
        super.show();
        sh = new Texture("images/bgbattleship.png");
        bg = new Texture("images/ewq.png");
        background = new Background(bg,COUNT,490,350);
        ship = new Ship(new TextureRegion(sh));

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
    }

    private void update(float delta){
      ship.update(delta);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
 private void draw(){
     batch.begin();
     background.anidraw(batch);
     ship.draw(batch);
     batch.end();
 }
    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

   @Override
    public boolean touchDown(Vector2 touch, int pointer) {
       ship.touchDown(touch,pointer);
       return false;
    }
}
