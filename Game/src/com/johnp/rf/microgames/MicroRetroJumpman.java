package com.johnp.rf.microgames;

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

public class MicroRetroJumpman extends MicroGame{

	Paint paint;
	int floor = 736;
	Animation animJumpman;
	Jumpman jumpman;
	
	public MicroRetroJumpman(Game game) {
		super(game);
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 125)
			timer = 125;
		
		setWin(true);
		setHint("Jumpman");
		setDescription("Touch the screen and to jump over the spike.");
		
        Rect[] temp = new Rect[3];
        temp[0] = new Rect(0,0,16,16);
        temp[1] = new Rect(16,0,32,16);
        temp[2] = new Rect(32,0,48,16);
		
		animJumpman = new Animation(temp, 2f - GameManager.speed / 10f);
		
		jumpman = new Jumpman();
		
		paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		animJumpman.updateAnimation(deltaTime);
		
		jumpman.update();
		
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		
		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.rgb(153, 204, 255));
		
		g.drawScaledImage(Assets.sprJumpman,128, 400, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,300, 200, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,800, 300, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,1200, 500, 128, 128, 112, 0, 21, 16);
		
		for(int i = 0; i < 10; i++)
			g.drawScaledImage(Assets.sprJumpman, i * 128, 736, 128, 128, 80, 0, 16, 16);
		
		if(getWin())
		{
			if(jumpman.y == floor - jumpman.height)
				g.drawScaledImage(Assets.sprJumpman, jumpman.x, jumpman.y, jumpman.width, jumpman.height, animJumpman.getCurrentRect().left, animJumpman.getCurrentRect().top, animJumpman.getCurrentRect().width(), animJumpman.getCurrentRect().height());
			else
				g.drawScaledImage(Assets.sprJumpman, jumpman.x, jumpman.y, jumpman.width, jumpman.height, 48, 0, 16, 16);
		}
		else
		{
			g.drawScaledImage(Assets.sprJumpman, jumpman.x, jumpman.y, jumpman.width, jumpman.height, 96, 0, 16, 16);
		}
		
		g.drawScaledImage(Assets.sprJumpman, 800, 608, 128, 128, 64, 0, 16, 16);
		
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
	
	class Jumpman
	{
		Rect body;
		int x, y, width, height, hspeed;
		
		public Jumpman()
		{
			x = 128;
			y = 608;
			hspeed = 0;
			width = 128;
			height = 128;

			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			//gravity
			if(y + height < floor)
				hspeed += 1;
			
			else if(y + height > floor )
			{
				y = floor - height;
				hspeed = 0;
			}
		
			
	        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


	        int len = touchEvents.size();
	        for (int i = 0; i < len; i++)
	        {
	            TouchEvent event = touchEvents.get(i);

	            if(event.type == TouchEvent.TOUCH_DOWN && y == floor - height)
	            {
	            	if(getWin() == true)
	            	{
		            	hspeed = -23;
		            	SoundThread.add( Assets.sfxJump, 1f);
	            	}
	            }

	        }	
			
	        //Spike
	        if(Rect.intersects(body, new Rect(814,608,914,736)))
	        {
	        	setWin(false);
	        	hspeed = -10;
	        	
	    		if(getTimer() > 50)
	    			setTimer(50);
	        	
	        }
	        
	        if(getWin() == true)
	        	x += 7 + GameManager.speed * 2;
	        else if(y + height != floor)
	        	x -= 4;
			
			y += hspeed;
			body = new Rect(x,y,x + width,y + height);
		}
	}
	

}
