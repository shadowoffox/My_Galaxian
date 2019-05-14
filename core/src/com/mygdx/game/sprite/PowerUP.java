package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Ship;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;

public class PowerUP extends Ship {
    private Rect worldBounds;
    private Vector2 descentV;
    private int damageUp;
    private int hpUp;

    private MyShip myShip;

    public PowerUP(Rect worldBounds, MyShip myShip) {
        this.myShip = myShip;
        this.worldBounds = worldBounds;
        this.descentV = new Vector2(0, -0.3f);
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            int damageUp,
            int hpUp,
            float width
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.damageUp = damageUp;
        this.hpUp = hpUp;
        setWidthProportion(width);
        v.set(descentV);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop()) {
            v.set(v0);
        }
        if (getBottom()<worldBounds.getBottom()){
            destroy();
        }
    }

    public int getDamageUp() {
        return damageUp;
    }

}
