package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Base.Sprite;

public class Explosion extends Sprite {
    private float animateTimer;
    private float animateInterval = 0.01f;
    public Explosion(TextureAtlas atlas) {
        super(atlas.findRegion("explosion"), 7, 10, 56);

    }
    public void set (float width, Vector2 pos){
        this.pos.set(pos);
        setWidthProportion(width);
    }

    @Override
    public void update(float delta) {
      animateTimer +=delta;
      if (animateTimer>=animateInterval){
          animateTimer = 0f;
          if (++frame == regions.length){
              destroy();
          }
      }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
