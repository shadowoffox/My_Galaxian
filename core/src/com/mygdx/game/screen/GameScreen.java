package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.BaseScreen;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.pool.PowerUPPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.ButtonNewGame;
import com.mygdx.game.sprite.Enemy;
import com.mygdx.game.sprite.GameOver;
import com.mygdx.game.sprite.HpBar;
import com.mygdx.game.sprite.MyShip;
import com.mygdx.game.sprite.PowerUP;
import com.mygdx.game.utils.EnemyGenerator;
import com.mygdx.game.utils.Font;
import com.mygdx.game.utils.ItemGenerator;

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
    private TextureAtlas atlas3;
    private BulletPool bulletPool;
    private Sound myLaser;
    private Sound enemyLaser;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;
    private ExplosionPool explosionPool;
    private PowerUPPool powerUPPool;
    private ItemGenerator itemGenerator;
    private ButtonNewGame buttonNewGame;
    private GameOver gameOver;
    private Texture barHp;
    private HpBar hpBar;
    private Font font;
    private int kills=0;
    private int lvl=1;
    private int lvlMode=10;
    private static final String KILLS="KILLS : ";
    private static final String LEVEL = "LEVEL : ";
    private StringBuilder sbKills= new StringBuilder();
    private StringBuilder sbLevel= new StringBuilder();
    private int hpOver=9;
    private float size = 0.15f;
    @Override
    public void show() {
        super.show();
        bg = new Texture("images/ewq.png");
        background = new Background(bg,COUNT,490,350);
        atlas = new TextureAtlas("images/allforbattles.pack");
        atlas2 = new TextureAtlas("images/itsnew.pack");
        atlas3 = new TextureAtlas("images/items.atlas");
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas2);
        myLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/mylaser.wav"));
        enemyLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/enemylaser.wav"));
        buttonNewGame = new ButtonNewGame(atlas2,this);
        gameOver = new GameOver(atlas2,worldBounds);
        barHp = new Texture("images/items.png");
        hpBar = new HpBar(barHp,worldBounds);
        ship = new MyShip(atlas, bulletPool,explosionPool,myLaser);
        enemyPool = new EnemyPool(bulletPool, explosionPool,enemyLaser, worldBounds, ship,hpBar);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
        powerUPPool = new PowerUPPool(worldBounds,ship);
        itemGenerator = new ItemGenerator(atlas3,powerUPPool,worldBounds);
        font = new Font("font/font.fnt","font/font.png");
        font.setFontSize(0.03f);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Battle in the winter.mp3"));
        music.play();
        music.setLooping(true);
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        hpBar.resize(worldBounds);
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
        enemyGenerator.generate(delta,lvl);
        powerUPPool.updateActiveSprites(delta);
        itemGenerator.generate(delta);
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
                    ship.takeDamage(enemyship.getHP());
                    enemyship.destroy();
                    if (ship.getHP()<=0){hpBar.updateHp(9,0.09f);}
                    else{hpBar.updateHp(hpOver-(ship.getHP()-1),size-=0.01f);}
                    if (enemyship.isDestroyed()){kills++;}
                    System.out.println("вы словили урон = " + enemyship.getDamage());
                    System.out.println("HP = " + ship.getHP());
                    System.out.println("frame"+(hpOver-(ship.getHP()-1)));
                    System.out.println("size"+size);
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
                        if (enemy.isDestroyed()){kills++;}
                        if (kills>=lvlMode){lvl++;lvlMode+=20;}
                        return;
                    }
                }
            } else {
                if (ship.isBulletCollision(bullet)) {
                    ship.takeDamage(bullet.getDamage());
                    if (bullet.getDamage()>0){
                    if (ship.getHP()<=0){hpBar.updateHp(9,0.09f);}
                    else{hpBar.updateHp(hpOver-(ship.getHP()-1),size-=0.01f);}
                    System.out.println("вы словили урон = " + bullet.getDamage());
                    System.out.println("HP = " + ship.getHP());
                    System.out.println("frame"+(hpOver-(ship.getHP()-1)));
                    System.out.println("size"+size);}
                    bullet.destroy();
                    return;
                }
            }
        }

        if (ship.isDestroyed()){
            state = State.GAME_OVER;

        }
        for (PowerUP powerUP : powerUPPool.getActiveObjects()) {
            if (powerUP != null) {
                if (!powerUP.isOutside(ship)) {
                    if (powerUP.getDamageUp()==0){
                    ship.setHP(ship.getHP()+3);

                        if (ship.getHP()>=10){
                            ship.setHP(10);
                        }
                        hpBar.updateHp(hpOver-(ship.getHP()-1),size+=0.03f);
                        System.out.println("HP = " + ship.getHP());
                        System.out.println("frame"+(hpOver-(ship.getHP()-1)));
                        System.out.println("size"+size);
                    } else {ship.setDamage(ship.getDamage()+1);

                        System.out.println(ship.getDamage());}
                    powerUP.destroy();
                }
            }
        }

    }
    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        powerUPPool.freeAllDestroyedActiveSprites();
    }

 private void draw(){
     batch.begin();
     background.anidraw(batch);
     if (state==State.GAME_OVER){
         buttonNewGame.draw(batch);
         gameOver.draw(batch);
     }
    else if (state == State.PLAYING){
        hpBar.ndraw(batch);
         ship.draw(batch);
         bulletPool.drawActiveSprites(batch);
         enemyPool.drawActiveSprites(batch);
         powerUPPool.drawActiveSprites(batch);
         printInfo();
     }
     explosionPool.drawActiveSprites(batch);
     batch.end();
 }

 public void printInfo(){
        sbKills.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch,sbKills.append(KILLS).append(kills),worldBounds.getLeft(),worldBounds.getTop());
        font.draw(batch,sbLevel.append(LEVEL).append(lvl),worldBounds.pos.x-0.06f,worldBounds.getTop());
 }
    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        music.dispose();
        atlas.dispose();
        atlas2.dispose();
        atlas3.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        powerUPPool.dispose();
        myLaser.dispose();
        enemyLaser.dispose();
        explosionPool.dispose();
        font.dispose();
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
    public void reset(){ ship.reset();
       bulletPool.freeAllActiveSprites();
       enemyPool.freeAllActiveSprites();
       explosionPool.freeAllActiveSprites();
       powerUPPool.freeAllActiveSprites();
       kills=0;
       lvl=1;
       lvlMode=10;
       ship.setDamage(1);
       ship.setHP(10);
        hpBar.updateHp(0,0.15f);
        size=0.15f;
        HpBar.frame1=0;
        state= State.PLAYING;
    }
}
