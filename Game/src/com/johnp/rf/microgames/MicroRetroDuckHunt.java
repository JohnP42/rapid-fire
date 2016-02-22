package com.johnp.rf.microgames;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Animation;
import com.johnp.rf.Assets;

public class MicroRetroDuckHunt extends MicroGame {

	Paint paint;
	Duck duck;
	Animation animDuckR, animDuckL;
	boolean start = false;
	int sID;
	
	public MicroRetroDuckHunt(Game game) {
		super(game);
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 150)
			timer = 150;
		
		setWin(false);
		setHint("Hunt!");
		setDescription("Touch the Duck to shoot it down.");
		
		duck = new Duck();
		
        Rect[] temp = new Rect[3];
        temp[0] = new Rect(0,37,16,53);
        temp[1] = new Rect(16,37,32,53);
        temp[2] = new Rect(32,37,48,53);
		
		animDuckR = new Animation(temp, 2f - GameManager.speed / 10f);
		
        Rect[] temp2 = new Rect[3];
        temp2[0] = new Rect(64,37,80,53);
        temp2[1] = new Rect(80,37,96,53);
        temp2[2] = new Rect(96,37,112,53);
        
        animDuckL = new Animation(temp2, 2f - GameManager.speed / 10f);
		
		paint = new Paint();
        paint.setAntiAlias(true);
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
		{
			if(sID != 0)
				Assets.sfxFlap.stop(sID);
			
			returnToGameScreen();
		}
		
		onStart();
		
		animDuckR.updateAnimation(deltaTime);
		animDuckL.updateAnimation(deltaTime);
		
		duck.update();
	}

	public void onStart()
	{
		if(!start)
		{
			sID = Assets.sfxFlap.play(1f);
		}
		start = true;
	}
	
	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.rgb(153, 204, 255));

		//clouds
		g.drawScaledImage(Assets.sprJumpman,128, 400, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,300, 200, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,800, 300, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,1200, 500, 128, 128, 112, 0, 21, 16);
		
		//duck
		if(duck.alive)
		{
			if(duck.hspeed > 0)
				g.drawScaledImage(Assets.sprDuck, duck.x, duck.y, duck.width, duck.height, animDuckR.getCurrentRect().left, animDuckR.getCurrentRect().top, animDuckR.getCurrentRect().width(), animDuckR.getCurrentRect().height());
			else
				g.drawScaledImage(Assets.sprDuck, duck.x, duck.y, duck.width, duck.height, animDuckL.getCurrentRect().left, animDuckL.getCurrentRect().top, animDuckL.getCurrentRect().width(), animDuckL.getCurrentRect().height());
		}
		else
		{
			g.drawScaledImage(Assets.sprDuck, duck.x, duck.y, duck.width, duck.height, 112, 37, 16, 16);
		}
		
		//floor
		g.drawScaledImage(Assets.sprDuck, 0, 522, 1280, 288, 0, 1, 160, 36);
		
		
		if(duck.p != null)
			g.drawScaledImage(Assets.sprDuck, duck.p.x - 64, duck.p.y - 64, 128, 128, 48, 37, 16, 16);
		
		//timer
        paint.setColor(Color.BLACK);
        paint.setTextSize(26);
        paint.setTextAlign(Align.LEFT);
		g.drawString((int)(timer / 100) + "", 0, 20, paint);
		g.drawRect(28, 5, (int)timer, 5, Color.BLACK);
		
		if(duck.gunCount > 0)
			g.clearScreen(Color.WHITE);
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
	
	class Duck
	{
		
		int x, y, width, height, hspeed, vspeed;
		Rect body;
		boolean alive;
		Random random;
		int count;
		Point p;
		int gunCount;
		
		public Duck()
		{
			random = new Random();
			
			width = 128;
			height = 128;
			
			x = random.nextInt(1280);
			y = 650;
			
			vspeed =  -4 - (int)GameManager.speed;
			
			if(x < 640)
				hspeed = 4 + (int)GameManager.speed;
			else
				hspeed = -4 - (int)GameManager.speed;
			
			count = 15;
			
			alive = true;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
		
			if(alive)
			{
			
		        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		    	
		        int len = touchEvents.size();
		        for (int i = 0; i < len; i++)
		        {
		            TouchEvent event = touchEvents.get(i);
	
		            if(event.type == TouchEvent.TOUCH_DOWN && gunCount == 0)
		            {
		            	if(body.contains(event.x, event.y))
		            	{
		            		Assets.sfxFlap.stop(sID);
		            		sID = 0;
			            	alive = false;
			            	hspeed = 0;
			            	vspeed = 0;
			            	setWin(true);
			            	
				    		if(getTimer() > 75)
				    			setTimer(75);
			            	
		            	}
		            	p = new Point(event.x, event.y);
		            	SoundThread.add( Assets.sfxGun, 1f);
		            	gunCount = 3;
		            	
		            	break;
		            }
		        }	
			}
			else if(count > 0)
			{
				count--;
			}
			else
				vspeed = 6;
			
			if(gunCount > 0)
				gunCount--;
			
			x += hspeed;
			y += vspeed;
			
			body = new Rect(x,y,x + width,y + height);
		}
		
	}
	
}


