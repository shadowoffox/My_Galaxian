package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.sprite.Enemy;

public class EnemyGenerator {
    private static final float ENEMY_NONE_FIRE_HEIGHT = 0.1f;
    private static final float ENEMY_NONE_FIRE_BULLET_HEIGHT = 0f;
    private static final float ENEMY_NONE_FIRE_BULLET_VY = 0f;
    private static final int ENEMY_NONE_FIRE_DAMAGE = 1;
    private static final float ENEMY_NONE_FIRE_RELOAD_INTERVAL = 10;
    private static final int ENEMY_NONE_FIRE_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.1f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.06f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.20f;
    private static final int ENEMY_MEDIUM_DAMAGE = 3;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private Rect worldBounds;

    private float generateInterval = 2f;
    private float generateTimer;

    private final TextureRegion[] enemyNoneFireRegion;
    private final TextureRegion[] enemyMediumRegion;

    private final Vector2 enemyNoneFireV = new Vector2(0, -0.3f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.06f);

    private final TextureRegion bulletRegion;

    private final EnemyPool enemyPool;

    public EnemyGenerator(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("saucer1b");
        this.enemyNoneFireRegion = Regions.split(enemy0, 1, 1, 2);
        TextureRegion enemy1 = atlas.findRegion("saucer2b");
        this.enemyMediumRegion = Regions.split(enemy1, 1, 1, 2);
        this.bulletRegion = atlas.findRegion("beams-4");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemyNoneFireRegion,
                        enemyNoneFireV,
                        bulletRegion,
                        ENEMY_NONE_FIRE_BULLET_HEIGHT,
                        ENEMY_NONE_FIRE_BULLET_VY,
                        ENEMY_NONE_FIRE_DAMAGE,
                        ENEMY_NONE_FIRE_RELOAD_INTERVAL,
                        ENEMY_NONE_FIRE_HEIGHT,
                        ENEMY_NONE_FIRE_HP
                );
            } else if (type < 0.85f) {
                enemy.set(
                        enemyMediumRegion,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

}
