package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;

public class Ship extends Sprite {
    private Vector2 touch;
    private Vector2 v;
    private Vector2 bufV;
    private Vector2 buf;

    public Ship(TextureRegion region) {
        super(region);
        touch = new Vector2();
        v = new Vector2();
        bufV = new Vector2();
        buf = new Vector2();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setWidthProportion(worldBounds.getWidth() * 0.15f);
        setBottom(worldBounds.getBottom());

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buf.set(touch);
        bufV.set(v);
        bufV.scl(delta);
        if (buf.sub(pos).len() < bufV.len()) {
            pos.set(touch);
        } else {
            pos.mulAdd(v, delta);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v.set(touch.cpy().sub(pos)).setLength(0.1f);
        return false;
    }
}
