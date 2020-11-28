package com.darklord_games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	boolean gameState = false;
	float gravity = 2;

	//Main material e.g background and bird and calling of bird movement
	public void MainMaterial(){
		BirdFlapes();
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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
		birdY = (Gdx.graphics.getHeight())/2 - (birds[0].getHeight()/2);
	}

	@Override
	public void render () {
		//Main movement up down
		if(!gameState){
			if(Gdx.input.justTouched()){
				velocity = -30;
			}
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
