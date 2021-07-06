package com.mygdx.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Ship;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;

public class Enemy extends Ship {
    private enum State {DESCENT, FIGHT}
    private State state;
    private Vector2 descentV;

    private MyShip myShip;
    private HpBar hpBar;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool,Sound shootSound, Rect worldBounds, MyShip myShip, HpBar hpBar) {
        this.myShip = myShip;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.descentV = new Vector2(0, -0.3f);
        this.hpBar=hpBar;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop()) {
            state = State.FIGHT;
            v.set(v0);
        }
        if (state == State.FIGHT) {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
        }
        if (getBottom()<worldBounds.getBottom()){
            destroy();
            myShip.takeDamage(1);
            hpBar.updateHp(9-(myShip.getHP()-1),hpBar.getSize()-0.01f);
        }
    }
    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight()<getLeft() ||
                 bullet.getLeft()>getRight() ||
                 bullet.getBottom()> getTop()||
                 bullet.getTop()< pos.y);
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float width,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setWidthProportion(width);
        this.hp = hp;
        v.set(descentV);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }
}
