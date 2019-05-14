package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.PowerUPPool;
import com.mygdx.game.sprite.PowerUP;

public class ItemGenerator {
    private static final int POWER_UP=1;
    private static final int HP_UP=1;

    private Rect worldBounds;

    private float generateInterval = 20f;
    private float generateTimer;

    private final TextureRegion[] hpUpRegion;
    private final TextureRegion[] powerUpRegion;

    private final Vector2 hpUpV = new Vector2(0, -0.2f);
    private final Vector2 powerUpV = new Vector2(0, -0.2f);

    private final PowerUPPool powerUPPool;

    public ItemGenerator(TextureAtlas atlas,PowerUPPool powerUPPool, Rect worldBounds) {
        TextureRegion hpUP = atlas.findRegion("bonus");
        this.hpUpRegion = Regions.split(hpUP, 1, 1, 1);
        TextureRegion powerUp = atlas.findRegion("upgrade");
        this.powerUpRegion = Regions.split(powerUp, 1, 1, 1);
        this.powerUPPool=powerUPPool;
        this.worldBounds=worldBounds;

    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            PowerUP powerUP = powerUPPool.obtain();
            float type = (float) (0 + Math.random()* 20);
            if (type >= 10f) {
                powerUP.set(
                        hpUpRegion,//какой элемент, картинка
                        hpUpV, // скорость
                        0,
                        HP_UP,
                        0.1f
                );
            } else if (type < 10) {
                powerUP.set(
                        powerUpRegion,
                        powerUpV,
                        POWER_UP,
                        0,
                        0.1f
                );
            }
            powerUP.pos.x = Rnd.nextFloat(worldBounds.getLeft() + powerUP.getHalfWidth(),
                    worldBounds.getRight() - powerUP.getHalfWidth());
            powerUP.setBottom(worldBounds.getTop());
        }
    }
}
