package com.darklord_games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube,bottomTube;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	boolean gameState = false;
	float gravity = 2;
	float gap = 400;
	float maxTubeOffset;
	float tubeOffset;

	Random randomGenerater;
	//Main material e.g background and bird and calling of bird movement
	public void MainMaterial(){
		BirdFlapes();
		batch.draw(birds[flapState],Gdx.graphics.getWidth()/2 - birds[flapState].getWidth()/2,birdY);
		batch.end();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		birdY = (Gdx.graphics.getHeight())/2 - (birds[0].getHeight()/2);
	    maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
	    //for random position of tubes
	    randomGenerater = new Random();
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//Main movement up down
		if(!gameState){
			if(Gdx.input.justTouched()){
				velocity = -30;
				//random position of tubes
				tubeOffset = (randomGenerater.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			}
			batch.draw(topTube,Gdx.graphics.getWidth()/2-topTube.getWidth() / 2,Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset);
			batch.draw(bottomTube,Gdx.graphics.getWidth()/2-topTube.getWidth() / 2,Gdx.graphics.getHeight()/2 - gap / 2 - bottomTube.getHeight() + tubeOffset);
			//it will not let the bird go out of screen
			if(birdY > 0 || velocity < 0 ){
				velocity = velocity + gravity;
				birdY -= velocity;
			}
		}else{
			if(Gdx.input.justTouched()){
				gameState = true;
			}
		}
		MainMaterial();
	}

	//movement of flapes
	public void BirdFlapes(){
		if(flapState == 0){
			flapState = 1;
		}else{
			flapState = 0;
		}
	}

}
