package com.darklord_games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube,bottomTube;
	ShapeRenderer shapeRenderer;
	Circle birdCircle;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	boolean gameState = false;
	float gravity = 2;
	float gap = 400;
	float maxTubeOffset;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float DistanceBetweenTubes;

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
		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		birdY = (Gdx.graphics.getHeight())/2 - (birds[0].getHeight()/2);
	    maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
	    //for random position of tubes
	    randomGenerater = new Random();
	    DistanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

		for (int i = 0; i < numberOfTubes; i++) {
			//random position of tubes
			tubeOffset[i] = (randomGenerater.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2-topTube.getWidth() / 2 + i * DistanceBetweenTubes;
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//Main movement up down
		if(!gameState){
			if(Gdx.input.justTouched()){
				velocity = -30;
			}
			for (int i = 0; i < numberOfTubes; i++) {
				//movement of tubes
				if(tubeX[i] < -topTube.getWidth()){
					tubeX[i] += numberOfTubes * DistanceBetweenTubes;
					tubeOffset[i] = (randomGenerater.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				}else{
					tubeX[i] = tubeX[i] - tubeVelocity;
				}
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
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
		//shapes for checking collison
		birdCircle.set(Gdx.graphics.getWidth() / 2,birdY + birds[flapState].getHeight() / 2,birds[flapState].getWidth() / 2);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		shapeRenderer.end();
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
