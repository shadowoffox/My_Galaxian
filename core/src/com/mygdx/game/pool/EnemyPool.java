package com.mygdx.game.pool;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.Base.SpritesPool;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Enemy;
import com.mygdx.game.sprite.MyShip;

public class EnemyPool extends SpritesPool<Enemy> {
    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound shootSound;
    private MyShip myShip;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds, MyShip myShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.myShip = myShip;

    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, shootSound, worldBounds, myShip);
    }
}
