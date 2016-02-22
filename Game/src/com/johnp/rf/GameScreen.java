package com.johnp.rf;


import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Screen;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.rf.microgames.GameManager;
import com.johnp.rf.microgames.MicroGame;

public class GameScreen extends Screen {

	enum GameState {PAUSED, RUNNING, GAMEOVER}
	
	float onScreenTime = 200f + 200f /GameManager.speed;
	
	Paint paint;
	GameState gamestate;
	float timer = 0;
	
	int transitionPosY;
	String tansitionWord;
	MicroGame nextScreen;
	
	Animation animRobot;
	
	public GameScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
        gamestate = GameState.RUNNING;
        
        Rect[] temp = new Rect[3];
        temp[0] = new Rect(0,0,300,340);
        temp[1] = new Rect(301,0,601,340);
        temp[2] = new Rect(602,0,902,340);
        animRobot = new Animation(temp, 2f - GameManager.speed / 10f);
        
        transitionPosY = -340;
        
        if(GameManager.currentGame < GameManager.games.size())
        {
	        nextScreen = GameManager.getNextGame(GameManager.currentGame);
	        GameManager.currentGame++;
        }
        else
        {
        	GameManager.newGameList(game);
        	GameManager.currentGame = 0;
        	nextScreen = GameManager.getNextGame(GameManager.currentGame);
	        GameManager.currentGame++;
        }
        	
	}
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
		if(gamestate == GameState.RUNNING)
			updateRunning(deltaTime);
		else if(gamestate == GameState.PAUSED)
			updatePaused(deltaTime);
		else if(gamestate == GameState.GAMEOVER)
			updateGameOver(deltaTime);
		
	}

	public void updateRunning(float deltaTime)
	{
		
		animRobot.updateAnimation(deltaTime);
		
		if(GameManager.hp > 0)
		{
			timer += deltaTime;
			
			if(timer >= onScreenTime)
			{
				game.newInput();
				game.setScreen(nextScreen);
			}
		}
		else
		{	
		//GameOver
			gamestate = GameState.GAMEOVER;	
		}
	}
	
	public void updatePaused(float deltaTime)
	{
		Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        

        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
        	if(i < touchEvents.size())
        	{
	            TouchEvent event = touchEvents.get(i);
	            if (event.type == TouchEvent.TOUCH_UP) 
	            {
	            	if (inBounds(event, g.getWidth() / 2 - 122, g.getHeight() / 2 - 40, 245, 80))
	            	{
	            		gamestate = GameState.RUNNING;
	                }
	
	            }
        	}
        }
	}
	
	
	public void updateGameOver(float deltaTime)
	{
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
        	if(i < touchEvents.size())
        	{
	            TouchEvent event = touchEvents.get(i);
	            if (event.type == TouchEvent.TOUCH_UP) 
	            {
	            	if (inBounds(event, 100, 100, 1080, 600))
	            	{
	            		game.setScreen(new MainMenuScreen(game));
	            		
	                }
	
	            }
        	}
        }
	}
	
	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
				
		
		if(gamestate == GameState.RUNNING)
		{
			paint.setTextAlign(Paint.Align.LEFT);
			paint.setColor(Color.WHITE);
			paint.setTextSize(100);
			
			g.drawString("Score:" + GameManager.score, 0, 100, paint);
			
			if(timer > onScreenTime / 2)
				GameManager.success = -1;
			
			paint.setTextAlign(Paint.Align.CENTER);
			
			if(GameManager.success == 0)
			{
				g.drawString("Failure", g.getWidth() / 2, 100, paint);
				for(int i = 0; i < GameManager.hp; i++)
					g.drawImage(Assets.robotLose, i * 310 + 50, g.getHeight() / 2, animRobot.getCurrentRect().left, animRobot.getCurrentRect().top, animRobot.getCurrentRect().width(), animRobot.getCurrentRect().height());
			
			}else if(GameManager.success == 1)
			{
				g.drawString("Success!", g.getWidth() / 2, 100, paint);
				for(int i = 0; i < GameManager.hp; i++)
					g.drawImage(Assets.robotWin, i * 310 + 50, g.getHeight() / 2, animRobot.getCurrentRect().left, animRobot.getCurrentRect().top, animRobot.getCurrentRect().width(), animRobot.getCurrentRect().height());
			
			}
			else if(GameManager.success == -1)
			{
				for(int i = 0; i < GameManager.hp; i++)
					g.drawImage(Assets.robot, i * 310 + 50, g.getHeight() / 2, animRobot.getCurrentRect().left, animRobot.getCurrentRect().top, animRobot.getCurrentRect().width(), animRobot.getCurrentRect().height());
			
				if(timer > onScreenTime / 3)
				{
					if(transitionPosY < 0)
						transitionPosY+=20;
					
					paint.setColor(Color.BLACK);
					paint.setTextAlign(Align.CENTER);
					paint.setTextSize(50);
					paint.setFakeBoldText(true);				
					g.drawImage(Assets.robotTransition, 700, transitionPosY);
					g.drawString(nextScreen.getHint(),846,285 + transitionPosY, paint);
					paint.setFakeBoldText(false);
				}
			}
			
		}
		else if(gamestate == GameState.PAUSED)
		{
			paint.setColor(Color.WHITE);
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(100);
			
			g.drawString("Unpause", g.getWidth() / 2, g.getHeight() / 2, paint);
		}
		else if(gamestate == GameState.GAMEOVER)
		{
			paint.setColor(Color.WHITE);
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(100);
			
			g.drawString("Game Over!", g.getWidth() / 2, g.getHeight() / 2, paint);
		}
	}

	
    private boolean inBounds(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }
	
	@Override
	public void pause() {
		gamestate = GameState.PAUSED;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		gamestate = GameState.RUNNING;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		if(gamestate != GameState.PAUSED)
			pause();
		else
			game.setScreen(new MainMenuScreen(game));
	}

}
