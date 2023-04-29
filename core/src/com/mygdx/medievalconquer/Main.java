package com.mygdx.medievalconquer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.medievalconquer.engine.Game;
import com.mygdx.medievalconquer.engine.tools.Tools;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	public Tools t = new Tools();
	public Game game = new Game(t);
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		game.display();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
