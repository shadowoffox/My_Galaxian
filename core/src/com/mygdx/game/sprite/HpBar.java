package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;



public class HpBar extends Sprite {
    public float size;
    public HpBar(Texture reg, Rect worldBounds){
        super(reg,12,100);
        setWidthProportion1(0.15f);
        setLeft(worldBounds.getLeft()+0.27f);
        setTop(worldBounds.getTop()+0.27f);
    }

   public void updateHp(int overHp, float size){
        this.size=size;
        frame1=overHp;
       setWidthProportion1(size);
   }

    public float getSize() {
        return size;
    }
}
