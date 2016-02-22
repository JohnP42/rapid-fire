package com.johnp.rf.microgames;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Animation;
import com.johnp.rf.Assets;

public class MicroRetroSpaceShooter extends MicroGame{

	Paint paint;
	Ship ship;
	Alien alien;
	ArrayList<Lazer> lazers = new ArrayList<Lazer>();
	Animation animAlien;
	
	public MicroRetroSpaceShooter(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 100)
			timer = 100;
		
		setWin(false);
		setHint("Shoot");
		setDescription("Tap the screen to fire.");
		
		paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
        ship = new Ship();
        alien = new Alien();
        lazers = new ArrayList<Lazer>();
        
        Rect[] temp = new Rect[2];
        temp[0] = new Rect(32,0,64,32);
        temp[1] = new Rect(64,0,96,32);
        
        animAlien = new Animation(temp, 2f - GameManager.speed / 10f);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		animAlien.updateAnimation(deltaTime);
        
        ship.update();
        
        if(alien != null)
        alien.update();
        
        ArrayList<Lazer> temp = new ArrayList<Lazer>();
        
        for(Lazer l : lazers)
		{
        	temp.add(l);
		}
        
        for(Lazer l : temp)
		{
        	l.update();
		}
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
	
	
		for(Lazer l : lazers)
		{
			g.drawRect(l.x, l.y, l.width, l.height, Color.RED);
		}
		
		g.drawScaledImage(Assets.sprSpaceShooter, ship.x, ship.y,ship.width,ship.height, 0, 0, 32, 32);
		
		if(alien != null)
		g.drawScaledImage(Assets.sprSpaceShooter, alien.x, alien.y, alien.width, alien.height, animAlien.getCurrentRect().left, animAlien.getCurrentRect().top, animAlien.getCurrentRect().width(), animAlien.getCurrentRect().height());
		
		//timer
        paint.setColor(Color.WHITE);
        paint.setTextSize(26);
        paint.setTextAlign(Align.LEFT);
		g.drawString((int)(timer / 100) + "", 0, 20, paint);
		g.drawRect(28, 5, (int)timer, 5, Color.WHITE);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}

	class Ship
	{
		Rect body;
		int x, y, width, height;
		
		public Ship()
		{
			x = 576;
			y = 650;
			width = 128;
			height = 128;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			//Input
	        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();      
	        

	        int len = touchEvents.size();
	        
	        for (int i = 0; i < len; i++)
	        {
	            TouchEvent event = touchEvents.get(i);

	            if(event.type == TouchEvent.TOUCH_DOWN)
				{
					shootLazer();
				}

	        }	
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void shootLazer()
		{
			lazers.add(new Lazer());
			SoundThread.add( Assets.sfxLaser, 1f);
		}
	}
	
	class Alien
	{
		Rect body;
		int x, y, width, height;
		int speed = (int) GameManager.speed * 3 + 5;
		
		public Alien()
		{
			x = 1280;
			y = 128;
			width = 128;
			height = 128;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			x -= speed;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void dispose()
		{
			alien = null;
		}
		
	}
	
	class Lazer
	{
		int x, y, width, height;
		Rect body;
		int speed = (int) 30;
		
		public Lazer()
		{
			x = ship.x + 57;
			y = ship.y;
			width = 12;
			height = 60;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			y -= speed;
			
			body = new Rect(x,y,x + width,y + height);
			
			if(alien != null && body.intersect(alien.body))
			{
				alien.dispose();
				
				
				if(getWin() == false)
				{
					setWin(true);
		    		if(getTimer() > 25)
		    			setTimer(25);
				}
	
	        	lazers.remove(this);
			}
				
		}
	}
	
}
