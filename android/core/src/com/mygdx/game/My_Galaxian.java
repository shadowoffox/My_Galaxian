package com.mygdx.game;

import com.badlogic.gdx.Game;

import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class My_Galaxian extends Game {


	@Override
	public void create () {
	setScreen(new MenuScreen(this));

	}

}