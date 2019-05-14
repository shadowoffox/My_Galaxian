package com.mygdx.game.pool;

import com.mygdx.game.Base.SpritesPool;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.MyShip;
import com.mygdx.game.sprite.PowerUP;

public class PowerUPPool extends SpritesPool<PowerUP> {

    private Rect worldBounds;
    private MyShip myShip;

    public PowerUPPool(Rect worldBounds, MyShip myShip){
        this.worldBounds = worldBounds;
        this.myShip = myShip;
    }
    @Override
    protected PowerUP newObject() {
        return new PowerUP(worldBounds, myShip);
    }
}
