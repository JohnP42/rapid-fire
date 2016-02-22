package com.johnp.rf.microgames;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Assets;

public class MicroRetroPong extends MicroGame {

	Paint paint;
	Paddle p1, p2;
	Ball ball;
	Random random = new Random();
	
	int score;
	
	public MicroRetroPong(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 100)
			timer = 100;
		
		setWin(true);
		setHint("Ping-Pong");
		setDescription("Touch the screen and slide the left paddle up or down to hit the ball.");
		
		score = 0;
		paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
        p1 = new Paddle(true);
        p2 = new Paddle(false);
        
        ball = new Ball();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		p1.update(ball);
		p2.update(ball);
		ball.update();
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
		

        paint.setTextSize(300);
        paint.setColor(Color.LTGRAY);       
        paint.setTextAlign(Align.CENTER);
        g.drawString("0     " + score, 680,250,paint);
		
		//Objects
		g.drawRect(p1.x, p1.y, p1.width, p1.height, Color.WHITE);
		g.drawRect(p2.x, p2.y, p2.width, p2.height, Color.WHITE);
		
		g.drawRect(ball.x, ball.y, ball.radius * 2, ball.radius * 2, Color.WHITE);
		
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
	
	class Paddle
	{
		int x, y, width, height;
		boolean player;
		
		public Paddle(boolean p)
		{
			player = p;
			
			if(p)
			{
				x = 200;
				y = 400;
				width = 40;
				height = 200;
			}
			else
			{
				x = 1200;
				y = 400;
				width = 40;
				height = 200;
			}
		}
		
		public void update(Ball b)
		{
			if(player)
			{
		        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


		        int len = touchEvents.size();
		        for (int i = 0; i < len; i++)
		        {
		            TouchEvent event = touchEvents.get(i);

		             y = event.y - 100;

		        }		
		        
				if(Rect.intersects(new Rect(x,y,x + width,y + height), b.getSize()) && b.xSpeed < 0)
		        {
		        	b.reverse();
		        	SoundThread.add( Assets.sfxPong, 1f);
		        }
			}
			else
			{
				y = (int)b.getY() - height / 2;
				
				if(Rect.intersects(new Rect(x,y,x + width, y + height), b.getSize()) && b.xSpeed > 0)
		        {
		        	b.reverse();
		        	SoundThread.add( Assets.sfxPong, 1f);
		        }
			}


			
		}
		
	}
	
	class Ball
	{
		int x, y, radius;
		int xSpeed, ySpeed;
		
		public Ball()
		{
			x = 800;
			y = 400;
			radius = 20;
			xSpeed = (int)(GameManager.speed * 10f);
			
			
			if( xSpeed > 50)
				xSpeed = 50;
			
			ySpeed = 6 + (int)(GameManager.speed * 1f);
			
			if(ySpeed > 12)
				ySpeed = 12;
			
			if(random.nextInt(2) < 1)
				ySpeed *= -1;
		}
		
		public void update()
		{
			x += xSpeed;
			y += ySpeed;
			
			if(y <= 0 || y >= 700)
				ySpeed *= -1;
			
			if(x < 0 && getWin() == true)
			{
				setWin(false);
				
	    		if(getTimer() > 25)
	    			setTimer(25);
				
				score=1;
			}
		}
		
		public float getY()
		{
			return y;
		}
		
		public Rect getSize()
		{
			return new Rect(x,y, x + radius * 2, y + radius * 2);
		}
		
		public void reverse()
		{
			xSpeed *= -1;
		}
		
		
	}
	
}
