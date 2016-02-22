package com.johnp.rf.microgames;

import com.johnp.framework.Game;
import com.johnp.framework.Screen;
import com.johnp.rf.GameScreen;

public abstract class MicroGame extends Screen{

	
	boolean win;
	float timer;
	String hint;
	String description;
	
	public MicroGame(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	
	public void returnToGameScreen()
	{
		if(win)
		{
			GameManager.success = 1;
			GameManager.score++;
			if(GameManager.speed < GameManager.maxSpeed)
			GameManager.speed+=.25f;
		}
		else
		{
			GameManager.success = 0;
			GameManager.hp--;
		}
		
		game.setScreen(new GameScreen(game));
	}
	
	public void setWin(boolean b)
	{
		win = b;
	}
	
	public boolean getWin()
	{
		return win;
	}

	public void setTimer(float t)
	{
		timer = t;
	}
	
	public float getTimer()
	{
		return timer;
	}
	
	public void setDescription(String x)
	{
		description = x;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setHint(String x)
	{
		hint = x;
	}
	
	public String getHint()
	{
		return hint;
	}
}
