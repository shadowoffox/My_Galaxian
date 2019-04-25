package com.mygdx.game.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.utils.Regions;


public class Sprite extends Rect {
    float angle;
    float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected Animation animation;
    float stateTime;
    protected TextureRegion animateRegion;
    private boolean isDestroyed;

    public Sprite(TextureRegion region){
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(Texture texture, int count, int width, int height) {
    this.regions =new TextureRegion[count];
        for (int i = 0; i < count; i++) {
           this.regions[i] = new TextureRegion(texture, (i * width), 0,width, height);
            regions[i].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        animation = new Animation(0.5f, regions);
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public Sprite() {
    }

    public void anidraw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        animateRegion = (TextureRegion) animation.getKeyFrame(stateTime, true);
        batch.draw(
                animateRegion,
                getLeft(),getBottom(),
                halfWidth,halfHeight,
                getWidth(),getHeight(),
                scale,scale,angle
        );
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(),getBottom(),
                halfWidth,halfHeight,
                getWidth(),getHeight(),
                scale,scale,angle
        );
    }

    public void resize(Rect worldBounds) {

    }

    public void update(float delta) {

    }

    public void setWidthProportion(float width) {
        setWidth(width);
        float aspect = regions[frame].getRegionHeight() / (float) regions[frame].getRegionWidth();
        setHeight(width * aspect);
    }
    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public void flushDestroy() {
        isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
