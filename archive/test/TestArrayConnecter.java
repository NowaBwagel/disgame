package com.nowabwagel.test;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TestArrayConnecter extends Game {

	private OrthographicCamera camera;
	SpriteBatch sprBatch;
	ShapeRenderer shapeDebug;
	
	int[][] unproccesed = { {0,1,1,0,1},
							{1,1,1,0,0},
							{0,0,1,0,1},
							{0,0,1,0,0},
							{1,1,1,1,0}};
	
	List<BetterQuad> proccesed = new ArrayList<BetterQuad>();
	
	class BetterQuad {
		public int row;
		public int width;
		public int type;
		
		public BetterQuad(int row, int width, int type){
			this.row = row;
			this.width = width;
			this.type = type;
		}
	}
	
	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		camera.update();
		sprBatch = new SpriteBatch();
		shapeDebug = new ShapeRenderer();
		
		int lPoint = 0;
		int rPoint = 0;
		
		int y = 0;
		while(y < unproccesed.length-1) {
			while(lPoint != unproccesed.length){
				while(rPoint < unproccesed.length -1 && unproccesed[lPoint][y] == unproccesed[rPoint+1][y]) {
					rPoint++;
				}
				proccesed.add(new BetterQuad(y, rPoint-lPoint, unproccesed[lPoint][y]));
				lPoint = rPoint+1;
			}
			lPoint = 0;
			rPoint = 0;
			y++;
		}
		
		String curLine = "";
		int lastRow = 0;
		for(BetterQuad quad : proccesed){
			if(lastRow != quad.row){
				curLine.concat("/n");
				lastRow = quad.row;}
			
				for(int i =0; i < quad.width; i++)
					curLine.concat(Integer.toString(quad.type));
		}
		System.out.println(curLine);
	}
	
	private void drawQuads(){

	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    sprBatch.begin();
	    Gdx.gl.glLineWidth(1);
	    shapeDebug.begin(ShapeType.Line);
	    shapeDebug.setProjectionMatrix(camera.combined);
	    shapeDebug.setColor(1, 1, 1, 1);
	    drawQuads();
	    shapeDebug.end();
	    sprBatch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		sprBatch.dispose();
		shapeDebug.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
	}

}
