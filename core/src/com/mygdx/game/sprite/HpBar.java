package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;



public class HpBar extends Sprite {
    public float size;
    public HpBar(Texture reg){
        super(reg,12,100);
       // setWidthProportion1(0.15f);

    }

   public void updateHp(int overHp, float size){
        this.size=size;
        frame1=overHp;
       setWidthProportion1(size);
   }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setWidthProportion1(0.15f);
        setRight(worldBounds.getRight());
        setTop(worldBounds.getTop());
    }

    public float getSize() {
        return size;
    }
}

