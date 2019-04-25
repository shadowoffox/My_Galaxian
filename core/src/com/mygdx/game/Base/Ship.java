package com.mygdx.game.Base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.sprite.Bullet;

public class Ship extends Sprite {
    protected Sound shootSound;
    protected Rect worldBounds;
    protected Vector2 v;
    protected Vector2 v0;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();

    }

    public Ship() {
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);


        this.worldBounds = worldBounds;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
    }

   public void shoot(){
       Bullet bullet = (Bullet) bulletPool.obtain();
       bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
       shootSound.play();
   }

    public void takeDamage(int damage){
        this.hp -= damage;
        if (hp <= 0){
            destroy();
        }
    }
    public int getHP(){
        return hp;
    }
}
