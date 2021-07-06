package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;

public class Asteroids extends Sprite {

    private float animateTimer;
    private float animateInterval = 0.01f;
    protected Vector2 v;

    public Asteroids(TextureAtlas atlas) {
        super(atlas.findRegion("Group_2"), 1, 4, 4);
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.5f, -0.1f);
        v = new Vector2(vx, vy);
    }
    public void set (Rect worldBounds){
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        this.pos.set(posX, posY);
        setWidthProportion(0.35f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        animateTimer +=delta;
        if (animateTimer>=animateInterval){
            animateTimer = 0f;
            if (++frame == regions.length){
                frame = 0;
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
