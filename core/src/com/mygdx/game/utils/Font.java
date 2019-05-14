package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Font extends BitmapFont {

    public Font(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile),Gdx.files.internal(imageFile),false,false);
    }
    public void setFontSize(float size){
        getData().setScale(size/getCapHeight());
    }
}
