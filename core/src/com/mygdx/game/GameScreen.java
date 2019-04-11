package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 13;
    private static final int STEP_NEXT_STAR_WIDTH = 490;
    private static final int STAR_WIDTH = 490;
    private static final int STAR_HEIGHT = 350;

    private SpriteBatch batch;
    private Texture img;
    private Texture ship;
    private TextureRegion[] region = new TextureRegion[STAR_COUNT];
    private Animation backgroundAnimation;
    private TextureRegion background;

    private Vector2 pos;
    private Vector2 v;
    private Vector2 v2;

    private Vector2 touch;

    private float stateTime;

    @Override
    public void show() {
        super.show();
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2();
        v2 = new Vector2();



        batch = new SpriteBatch();
        img = new Texture("images/ewq.png");
        ship = new Texture("images/bgbattleship.png");
        for (int i = 0; i < STAR_COUNT; i++) {

            region[i] = new TextureRegion(img, (i * STEP_NEXT_STAR_WIDTH), 0, STAR_WIDTH, STAR_HEIGHT);
            region[i].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        backgroundAnimation = new Animation(0.5f, region);


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        background = (TextureRegion) backgroundAnimation.getKeyFrame(stateTime, true);


        batch.begin();
        batch.draw(background,0,0,Gdx.app.getGraphics().getWidth(),Gdx.app.getGraphics().getHeight());
        if (pos.x!=touch.x){pos.x+=v2.x;}
        if (pos.y!=touch.y){pos.y+=v2.y;}
        batch.draw(ship,pos.x,pos.y);
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
        ship.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY);
        System.out.println("touch " +touch.x + " " + touch.y);
        v=touch.cpy().sub(pos);
        System.out.println("v= " + v.x + " " + v.y);
        if (v.x>0 ){v2.x=1f;}
        else{v2.x=-1f;}
        if (v.y>0){v2.y=1f;}
        else{v2.y=-1f;}
        System.out.println("v2= " +v2.x + " " +v2.y);
        System.out.println("pos= " +pos.x + " " +pos.y );
        return false;
    }
}
