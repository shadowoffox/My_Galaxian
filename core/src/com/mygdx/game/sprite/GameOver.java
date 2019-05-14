package com.mygdx.game.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Base.Sprite;
import com.mygdx.game.math.Rect;


public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("game_over"));
        setWidthProportion(0.9f);
        setBottom(worldBounds.getBottom()+0.01f);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
    }

}
