package com.mygdx.game.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Base.SpritesPool;
import com.mygdx.game.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
private TextureAtlas atlas;

public ExplosionPool (TextureAtlas atlas){
    this.atlas = atlas;
}

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas);
    }
}
