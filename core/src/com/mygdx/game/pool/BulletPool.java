package com.mygdx.game.pool;

import com.mygdx.game.Base.Sprite;
import com.mygdx.game.Base.SpritesPool;
import com.mygdx.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
