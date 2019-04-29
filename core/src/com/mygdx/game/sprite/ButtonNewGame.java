package com.mygdx.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Base.TouchUpButton;
import com.mygdx.game.screen.GameScreen;



public class ButtonNewGame extends TouchUpButton {

    private GameScreen game;
    public ButtonNewGame(TextureAtlas atlas) {
        super(atlas.findRegion("new_game"));
        pos.set(getHalfWidth(),getHalfHeight());
        setWidthProportion(0.3f);
    }


    @Override
    protected void action() {
       game.reset();

    }
}
