package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    public Background(Texture texture, int width, int height) {
        super(texture, width, height);
    }

    @Override
    public void resize(Rect worldBounds) {
        setWidthProportion(worldBounds.getWidth());
        pos.set(worldBounds.pos);
    }
}
