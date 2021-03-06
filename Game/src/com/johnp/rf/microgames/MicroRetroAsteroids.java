package com.johnp.rf.microgames;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Assets;
import com.johnp.rf.DPad;

public class MicroRetroAsteroids extends MicroGame {

	Paint paint;
	Ship ship;
	Asteroid a1, a2;
	
	public MicroRetroAsteroids(Game game) {
		super(game);
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 125)
			timer = 125;
		
		setWin(true);
		setHint("Avoid");
		setDescription("Touch the screen and the ship will follow. Avoid the asteroids.");
		
		ship = new Ship();
		a1 = new Asteroid();
		a2 = new Asteroid();
		
		paint = new Paint();
        paint.setAntiAlias(true);
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		setTimer(getTimer() - deltaTime);
		
		ship.update();
		a1.update();
		a2.update();
		
		if(timer <= 0)
		{
			if(ship.soundID != 0)
				Assets.sfxEngine.stop(ship.soundID);
			
			returnToGameScreen();
		}
		
		
	}

	@Override
	public void paint(float deltaTime) {

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
		
		
		//Ship
		if(ship.alive)
		{
			if(ship.thruster)
				g.drawRotatedFImage(Assets.spMeteors, ship.x, ship.y, ship.width, ship.height, ship.dir, 16, 0, 16, 16);
			else
				g.drawRotatedFImage(Assets.spMeteors, ship.x, ship.y, ship.width, ship.height, ship.dir, 0, 0, 16, 16);
			
		}
		else
		{
		//ShipDestroid
			g.drawRotatedFImage(Assets.spMeteors, ship.x, ship.y, ship.width, ship.height, ship.dir, 48, 0, 16, 16);
			g.drawRotatedFImage(Assets.spMeteors, ship.partX, ship.y, ship.width, ship.height, ship.dir, 64, 0, 16, 16);
			g.drawRotatedFImage(Assets.spMeteors, ship.x, ship.partY, ship.width, ship.height, ship.dir, 80, 0, 16, 16);
			
		}
		
		//Asteroids
		g.drawRotatedFImage(Assets.spMeteors, a1.x, a1.y, a1.width, a1.height, a1.dir, 32, 0, 16, 16);
		g.drawRotatedFImage(Assets.spMeteors, a2.x, a2.y, a2.width, a2.height, a2.dir, 32, 0, 16, 16);
		

		
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
		float x, y, width, height;
		RectF body;
		float xSpeed, ySpeed;
		float speed;
		float dir;
		boolean thruster;
		int counter;
		int soundID;
		boolean alive;
		float partX, partY;
		
		
		public Ship()
		{
			x = game.getGraphics().getWidth() / 2 - 64;
			y = game.getGraphics().getHeight() / 2 - 64;
			width = 128;
			height = 128;
			
			partX = 0;
			partY = 0;

			
			xSpeed = 0;
			ySpeed = 0;

            speed = (GameManager.speed * 2) + 5;
					
			dir = 0;
			counter = 0;
			
			soundID = 0;
			alive = true;
			
			body = new RectF(x,y,x + width,y + height);
		}
		
		public void update()
		{
			if(alive)
			{
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				
		        int len = touchEvents.size();
	
		        for (int i = 0; i < len; i++)
		        {
		            TouchEvent event = touchEvents.get(0);
		            
		            if(event.type == TouchEvent.TOUCH_DOWN)
		            	thruster = true;
		            
		            if(event.type == TouchEvent.TOUCH_UP)
		            	thruster = false;
		            
		            dir = DPad.getDirectionTo(x, y, event.x, event.y) - 90f;
	          
		            
		        }
		        
		        
		        
		        if(thruster)
		        {
		        	
		        	if(soundID == 0)
		        	{
		        		soundID = Assets.sfxEngine.play(1f);
		        	}
		        	
		            xSpeed = (float)(Math.sin(Math.toRadians(dir)) * speed);
		            ySpeed = (float)(-Math.cos(Math.toRadians(dir)) * speed);
	
		        }
		        else
		        {
		        	Assets.sfxEngine.stop(soundID);
		        	soundID = 0;
		        }

			}
			else
			{
				
				partX -= xSpeed;
				partY -= ySpeed;
				
			}
	        
	        x += xSpeed;
	        y += ySpeed;
	        
			body = new RectF(x,y,x + width,y + height);
		}
	}
	
	class Asteroid
	{
		
		float x, y, width, height;
		RectF body;
		float xSpeed, ySpeed;
		float dir;
		
		public Asteroid()
		{
			x = (int)(Math.random() * 2) * 1280 - 64;
			y = (float)Math.random() * 800;
			width = 128;
			height = 128;
			
            xSpeed = -(float)(Math.cos(Math.toRadians(DPad.getDirectionTo(x, y, game.getGraphics().getWidth() / 2, game.getGraphics().getHeight() / 2))) * (GameManager.speed * 2 + 4));
            ySpeed = -(float)(Math.sin(Math.toRadians(DPad.getDirectionTo(x, y, game.getGraphics().getWidth() / 2, game.getGraphics().getHeight() / 2))) * (GameManager.speed * 2 + 4));;
			
            dir = (float)Math.random() * 360;
            
			body = new RectF(x,y,x + width,y + height);
		}
		
		public void update()
		{
			
	        x += xSpeed;
	        y += ySpeed;
	        
	        dir+= GameManager.speed;
	        
	        body = new RectF(x,y,x + width,y + height);
	        
	        if(ship.alive && RectF.intersects(body, ship.body))
	        {
	        	ship.alive = false;
	        	setWin(false);
	        	
	        	if(getTimer() > 50)
	        		setTimer(50);
	        	
	        	ship.partX = ship.x;
	        	ship.partY = ship.y;
	        	
	        	SoundThread.add(Assets.sfxCrash, 1f);
	        }
			
		}
		
	}
	
}




