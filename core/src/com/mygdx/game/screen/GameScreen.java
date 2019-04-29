package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
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
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.Base.Ship;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.ButtonNewGame;
import com.mygdx.game.sprite.ButtonPlay;
import com.mygdx.game.sprite.Enemy;
import com.mygdx.game.sprite.Explosion;
import com.mygdx.game.sprite.GameOver;
import com.mygdx.game.sprite.MyShip;
import com.mygdx.game.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {
    public enum State {PLAYING, GAME_OVER, PAUSE}
    private State state;
    private static final int COUNT = 13;
    private Texture bg;
    private Background background;
    private MyShip ship;
    private Music music;
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private BulletPool bulletPool;
    private Sound myLaser;
    private Sound enemyLaser;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;
    private ExplosionPool explosionPool;
    private ButtonNewGame buttonNewGame;
    private GameOver gameOver;
    @Override
    public void show() {
        super.show();
        bg = new Texture("images/ewq.png");
        background = new Background(bg,COUNT,490,350);
        atlas = new TextureAtlas("images/allforbattles.pack");
        atlas2 = new TextureAtlas("images/itsnew.pack");
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas2);
        myLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/mylaser.wav"));
        enemyLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/enemylaser.wav"));
        buttonNewGame = new ButtonNewGame(atlas2);
        gameOver = new GameOver(atlas2,worldBounds);
        ship = new MyShip(atlas, bulletPool,explosionPool,myLaser);
        enemyPool = new EnemyPool(bulletPool, explosionPool,enemyLaser, worldBounds, ship);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Battle in the winter.mp3"));
        music.play();
        music.setLooping(true);
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        if (state==State.GAME_OVER){
            gameOver.resize(worldBounds);
            buttonNewGame.resize(worldBounds);
        }
    }

    private void update(float delta){
        if (state == State.PLAYING){
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
        }
        explosionPool.updateActiveSprites(delta);
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
        if (state != State.PLAYING){
            return;
        }
        for (Enemy enemyship : enemyPool.getActiveObjects()) {

            if (enemyship != null) {
                if (!enemyship.isOutside(ship)) {
                    ship.takeDamage(enemyship.getDamage());
                    enemyship.destroy();
                    System.out.println("вы словили урон = " + enemyship.getDamage());
                    System.out.println("HP = " + ship.getHP());
                }
            }
        }

        for (Bullet bullet : bulletPool.getActiveObjects()) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == ship) {
                for (Enemy enemy : enemyPool.getActiveObjects()) {
                    if (enemy.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.takeDamage(bullet.getDamage());
                        bullet.destroy();
                        return;
                    }
                }
            } else {
                if (ship.isBulletCollision(bullet)) {
                    ship.takeDamage(bullet.getDamage());
                    bullet.destroy();
                    return;
                }
            }
        }

        if (ship.isDestroyed()){
            state = State.GAME_OVER;

        }
    }
    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

 private void draw(){
     batch.begin();
     background.anidraw(batch);
     if (state==State.GAME_OVER){
         buttonNewGame.draw(batch);
         gameOver.draw(batch);
     }
    else if (state == State.PLAYING){
         ship.draw(batch);
         bulletPool.drawActiveSprites(batch);
         enemyPool.drawActiveSprites(batch);
        }
     explosionPool.drawActiveSprites(batch);
     batch.end();
 }
    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        music.dispose();
        atlas.dispose();
        atlas2.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        myLaser.dispose();
        enemyLaser.dispose();
        explosionPool.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

   @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING){
       ship.touchDown(touch,pointer);}
        else if (state==State.GAME_OVER){
            buttonNewGame.touchDown(touch,pointer); }
       return false;
    }
    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING){
        ship.touchUp(touch, pointer);}
        else if (state==State.GAME_OVER){
            buttonNewGame.touchUp(touch,pointer); }
        return false;
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.PLAYING){
            music.pause();
            state = State.PAUSE;
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.PAUSE){
            music.play();
         state = State.PLAYING;
        }
    }
    public void reset(){
       if(state == State.GAME_OVER){ ship.setHP(5);
        state= State.PLAYING;}
    }
}
