package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Base.TouchUpButton;
import com.mygdx.game.math.Rect;

public class ButtonExit extends TouchUpButton {

    private Sound sound;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("button_stop"));
        setWidthProportion(0.15f);
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/exit.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }

    @Override
    protected void action() {
        sound.play();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sound.stop();
        sound.dispose();

        Gdx.app.exit();
    }
}
