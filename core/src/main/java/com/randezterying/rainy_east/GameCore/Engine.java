package com.randezterying.rainy_east.GameCore;

import com.badlogic.gdx.Game;
import com.randezterying.rainy_east.View.Screens.MF.SplashScreen;

public class Engine extends Game {

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}
}