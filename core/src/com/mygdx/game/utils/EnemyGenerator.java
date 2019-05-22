package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.sprite.Enemy;

public class EnemyGenerator {
    private static final float ENEMY_NONE_FIRE_WIDTH = 0.05f;
    private static final float ENEMY_NONE_FIRE_BULLET_HEIGHT = 0f;
    private static final float ENEMY_NONE_FIRE_BULLET_VY = -0.20f;
    private static final int ENEMY_NONE_FIRE_DAMAGE = 0;
    private static final float ENEMY_NONE_FIRE_RELOAD_INTERVAL = 10f;
    private static final int ENEMY_NONE_FIRE_HP = 1;

    private static final float ENEMY_MEDIUM_WIDTH = 0.07f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.05f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.20f;
    private static final int ENEMY_MEDIUM_DAMAGE = 3;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2.5f;
    private static final int ENEMY_MEDIUM_HP = 3;

    private Rect worldBounds;

    private float generateInterval = 3f;
    private float generateTimer;

    private final TextureRegion[] enemyNoneFireRegion;
    private final TextureRegion[] enemyMediumRegion;

    private final Vector2 enemyNoneFireV = new Vector2(0, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.03f);

    private final TextureRegion bulletRegion;

    private final EnemyPool enemyPool;

    int args=1;

    public EnemyGenerator(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("saucer1b");
        this.enemyNoneFireRegion = Regions.split(enemy0, 1, 1, 1);
        TextureRegion enemy1 = atlas.findRegion("saucer2b");
        this.enemyMediumRegion = Regions.split(enemy1, 1, 1, 1);
        this.bulletRegion = atlas.findRegion("beams-4");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta, int stage) {
        args = stage/2 +1;
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
                        ENEMY_NONE_FIRE_WIDTH,
                        ENEMY_NONE_FIRE_HP*args
                );
            } else {
                enemy.set(
                        enemyMediumRegion,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE*args,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_WIDTH,
                        ENEMY_MEDIUM_HP
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
            worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

}
