package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    public Background(Texture texture,int count, int width, int height) {
        super(texture, count, width, height);

    }


    @Override
    public void resize(Rect worldBounds) {
        setWidthProportion(worldBounds.getWidth());
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
