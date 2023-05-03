package com.mygdx.medievalconquer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.medievalconquer.engine.inputs.Inputs;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.medievalconquer.engine.Game;
import com.mygdx.medievalconquer.engine.tools.Picture;
import com.mygdx.medievalconquer.engine.tools.Tools;

import java.util.Map;

public class Main extends ApplicationAdapter {
	public SpriteBatch batch;
	public ShapeRenderer shape;
	public Inputs inputs;
	public Tools t;
	public Game game;
	private Viewport viewport;
	private OrthographicCamera camera;

	@Override
	public void create () {
		this.t = new Tools(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.game = new Game(this.t);
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
		this.inputs = new Inputs(this);
		Gdx.input.setInputProcessor(this.inputs);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(this.camera.combined);
		shape.setProjectionMatrix(this.camera.combined);
		game.display(batch, shape);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shape.dispose();
		for(Map.Entry<String, Map<String, Texture>> m: Picture.get().entrySet()) {
			for(Map.Entry<String, Texture> texture: m.getValue().entrySet()) {
				texture.getValue().dispose();
			}
		}
	}
}
