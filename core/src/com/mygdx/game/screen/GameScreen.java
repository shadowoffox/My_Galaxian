package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.Base.Ship;
import com.mygdx.game.sprite.ButtonPlay;
import com.mygdx.game.sprite.MyShip;
import com.mygdx.game.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {
    private static final int COUNT = 13;
    private Texture bg;
    private Texture sh;
    private Background background;
    private MyShip ship;
    private Vector2 v;
    private TextureAtlas textureAtlas;
    private Music music;
    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private Sound myLaser;
    private Sound enemyLaser;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;


    @Override
    public void show() {
        super.show();
       // sh = new Texture("images/bgbattleship.png");
        bg = new Texture("images/ewq.png");
        background = new Background(bg,COUNT,490,350);
        atlas = new TextureAtlas("images/allforbattles.pack");
        bulletPool = new BulletPool();
        myLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/mylaser.wav"));
        enemyLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/enemylaser.wav"));

        ship = new MyShip(atlas, bulletPool,myLaser);
        enemyPool = new EnemyPool(bulletPool, enemyLaser, worldBounds, ship);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Battle in the winter.mp3"));
        music.play();
        music.setLooping(true);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
    }

    private void update(float delta){
      ship.update(delta);
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyedSprites();
        draw();
    }
    private void checkCollisions() {
        for (Object shipp : enemyPool.getActiveObjects()) {

            if (shipp instanceof Ship) {

                if (!((Ship) shipp).isOutside(ship)) {
                    ((Ship) shipp).takeDamage(1);
                    ship.takeDamage(1);
                }
            }
        }
        if (ship.isDestroyed()){
            System.out.println("GAME OVER");
            Gdx.app.exit();
        }
    }
    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

 private void draw(){
     batch.begin();
     background.anidraw(batch);
     ship.draw(batch);
     bulletPool.drawActiveSprites(batch);
     enemyPool.drawActiveSprites(batch);
     batch.end();
 }
    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        music.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        myLaser.dispose();
        enemyLaser.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

   @Override
    public boolean touchDown(Vector2 touch, int pointer) {
       ship.touchDown(touch,pointer);
       return false;
    }
    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch, pointer);
        return false;
    }
}
