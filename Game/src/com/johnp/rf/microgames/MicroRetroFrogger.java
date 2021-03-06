package com.johnp.rf.microgames;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Assets;

public class MicroRetroFrogger extends MicroGame {

	Paint paint;
	Frog frog;
	Car c1, c2;
	
	public MicroRetroFrogger(Game game) {
		super(game);
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 175)
			timer = 175;
		
		setWin(false);
		setHint("Cross");
		setDescription("Touch the screen to make the frog hop forward and avoid the cars.");
		
		frog = new Frog();
		
		c1 = new Car(true);
		c2 = new Car(false);

		paint = new Paint();
        paint.setAntiAlias(true);
		
	}

	@Override
	public void update(float deltaTime) {
		
		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		frog.update();
		c1.update();
		c2.update();
		
		
	}

	@Override
	public void paint(float deltaTime) {

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);

		//SideWalk
		for(int i = 0; i < 40; i++)
		{
			
			if( i < 10)
				g.drawScaledImage(Assets.sprFrog, i * 128, 0, 128, 128, 96, 0, 16, 16);
			else if( i < 20)
				g.drawScaledImage(Assets.sprFrog, (i - 10) * 128, 128, 128, 128, 96, 0, 16, 16);
			else if(i < 30)
				g.drawScaledImage(Assets.sprFrog, (i - 20) * 128, 650, 128, 128, 96, 0, 16, 16);
			else
				g.drawScaledImage(Assets.sprFrog, (i - 30) * 128, 768, 128, 128, 96, 0, 16, 16);

			
			if( i < 6 )
				g.drawRect(i * 256 - 64, 432, 128, 32, Color.YELLOW);
			
		}
		
		//Frog
		
		if(!frog.hit)
		{
			if(frog.speed == 0)
				g.drawScaledImage(Assets.sprFrog, frog.x, frog.y, frog.width, frog.height, 0, 0, 16, 16);
			else
				g.drawScaledImage(Assets.sprFrog, frog.x, frog.y, frog.width, frog.height, 16, 0, 16, 16);
		}
		else
			g.drawScaledImage(Assets.sprFrog, frog.x, frog.y, frog.width, frog.height, 112, 0, 16, 16);
		
		//Cars
		
		g.drawScaledImage(Assets.sprFrog, c1.x, c1.y, c1.width, c1.height, 64, 0, 32, 16);
		g.drawScaledImage(Assets.sprFrog, c2.x, c2.y, c2.width, c2.height, 32, 0, 32, 16);
		
		
		//timer
        paint.setColor(Color.BLACK);
        paint.setTextSize(26);
        paint.setTextAlign(Align.LEFT);
		g.drawString((int)(timer / 100) + "", 0, 20, paint);
		g.drawRect(28, 5, (int)timer, 5, Color.BLACK);
		
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

	class Frog
	{
		int x, y, width, height;
		Rect body;
		boolean hit;
		int speed;
		
		public Frog()
		{			
			x = 576;
			y = 640;
			width = 128;
			height = 128;
			
			speed = 0;
			
			hit = false;
			
			body = new Rect(x,y,x + width,y + height);
			
		}
		
		public void update()
		{
			if(!hit)
			{
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				
		        int len = touchEvents.size();
		        for (int i = 0; i < len; i++)
		        {
		            TouchEvent event = touchEvents.get(i);
	
		            if(speed == 0 && event.type == TouchEvent.TOUCH_DOWN)
		            {
		            	speed = -32;
		            	y -= 32;
		            	SoundThread.add(Assets.sfxJump, 2f);
		            }
		           
		        }	
		        
			}
			else
				speed = 0;
			
	        if(speed != 0)
	        {
	        	if(Math.abs(y) % 128 == 0)
	        		speed = 0;
	        }
	        
	        y += speed;
	        
			body = new Rect(x,y,x + width,y + height);
						
	        
	        if(y <= 128 && !getWin())
	        {
	        	setWin(true);
	        	
	        	if(getTimer() > 25)
	        		setTimer(25);
	        }
	        
			
		}
	}
	
	class Car
	{
		int x, y, width, height;
		Rect body;
		boolean right;
		
		public Car(boolean right)
		{
			x = (int)(Math.random() * 1024);
			
			this.right = right;
			
			if(right)
				y = 512;
			else
				y = 256;
			
			width = 256;
			height = 128;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			
			if(right)
			{
				x += GameManager.speed * 3 + 5;
				
				if(x > 1280 + width)
					x = -width;
				
			}
			else
			{
				x -= GameManager.speed * 3 + 5;
				
				if(x < 0 - width)
					x = 1280 + width;
				
			}
						
			
			body = new Rect(x,y,x + width,y + height);
			
			if(Rect.intersects(body, frog.body) && !frog.hit)
			{
				frog.hit = true;
				SoundThread.add(Assets.sfxCrash, 2f);
				
	        	if(getTimer() > 50)
	        		setTimer(50);
				
			}
			
		}
	}
	
}

