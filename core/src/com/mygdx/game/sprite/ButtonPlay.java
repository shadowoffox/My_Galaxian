package com.mygdx.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Base.TouchUpButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;


public class ButtonPlay extends TouchUpButton {

    private Game game;
    private Sound sound;
    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_play"));
        this.game = game;
        setWidthProportion(0.15f);
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/play.mp3"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setLeft(worldBounds.getLeft() + 0.02f);
    }

    @Override
    protected void action() {
        sound.play();
        sound.stop();
        sound.dispose();
        game.setScreen(new GameScreen());

    }
}
