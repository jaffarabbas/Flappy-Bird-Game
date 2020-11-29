package com.darklord_games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	//variables
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube,bottomTube;
	Texture gameOver;
	BitmapFont font;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity = 2;
	float gap = 400;
	int score = 0;
	int scoringTube = 0;
	float maxTubeOffset;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float DistanceBetweenTubes;
	Circle birdCircle;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;
	Random randomGenerater;
	//Main material e.g background and bird and calling of bird movement
	public void MainMaterial(){
		BirdFlapes();
		//draw bird
		batch.draw(birds[flapState],Gdx.graphics.getWidth()/2 - birds[flapState].getWidth()/2,birdY);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		gameOver = new Texture("gameover.png");
		birdCircle = new Circle();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
	    maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
	    //for random position of tubes
	    randomGenerater = new Random();
	    DistanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;
	    topTubeRectangles = new Rectangle[numberOfTubes];
	    bottomTubeRectangles = new Rectangle[numberOfTubes];
	    //start the game
		StartGame();
	}

	//game start here
	void StartGame(){
		birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		for (int i = 0; i < numberOfTubes; i++) {
			//random position of tubes
			tubeOffset[i] = (randomGenerater.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2-topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * DistanceBetweenTubes;
			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//Main movement up down
		if(gameState == 1){
			//score
 			if(tubeX[scoringTube] < Gdx.graphics.getWidth() / 2){
				score++;
				Gdx.app.log("Score : ",String.valueOf(score));
				if(scoringTube < numberOfTubes - 1){
					scoringTube++;
				}else{
					scoringTube = 0;
				}
			}
			//velocity
			if(Gdx.input.justTouched()){
				velocity = -24;
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

				topTubeRectangles[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],topTube.getWidth(),topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			}
			//it will not let the bird go out of screen
			if(birdY > 0 ){
				velocity = velocity + gravity;
				birdY -= velocity;
			}else{
				gameState = 2;
			}
		}else if(gameState == 0){
			//starting the game
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){
			//game over and new game will start
			batch.draw(gameOver,Gdx.graphics.getWidth()/2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			if(Gdx.input.justTouched()){
				//game start
				gameState = 1;
				StartGame();
				score  = 0;
				scoringTube = 0;
				velocity = 0;
			}
		}
		MainMaterial();
		font.draw(batch,String.valueOf(score),100,200);
		//shapes for checking collison
		birdCircle.set(Gdx.graphics.getWidth() / 2,birdY + birds[flapState].getHeight() / 2,birds[flapState].getWidth() / 2);
		for (int i = 0; i < numberOfTubes; i++) {
			//chekcing collision
			if(Intersector.overlaps(birdCircle,topTubeRectangles[i]) || Intersector.overlaps(birdCircle,bottomTubeRectangles[i])){
			    gameState = 2;
			}
		}
		batch.end();
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
