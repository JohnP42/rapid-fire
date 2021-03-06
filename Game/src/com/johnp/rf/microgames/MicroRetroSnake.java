package com.johnp.rf.microgames;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.implementation.TouchSlide;
import com.johnp.rf.Assets;

public class MicroRetroSnake extends MicroGame {

	Paint paint;
	
	Head head;
	Berry berry;
	
	public MicroRetroSnake(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		
		setTimer(800 - GameManager.speed * 100f);
		if (timer < 400)
			timer = 400;
		
		setWin(false);
		setHint("Snake");
		setDescription("Slide the screen to guide the snake and eat the berry.");
		
		
		head = new Head();
		berry = new Berry();
		
		paint = new Paint();
        paint.setAntiAlias(true);
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();

        head.update();
        
		
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
		
		//Border
		for(int i = 0; i < 20; i ++)
		{
			for(int z = 0; z < 20; z++)
			{
				if(i == 0 || i == 19 || z == 0 || z == 19)
					g.drawScaledImage(Assets.sprSnake, 388 + i * 32, z * 32, 32, 32, 32, 0, 16, 16);
			}
		}
		
		
		//Snake
		g.drawScaledImage(Assets.sprSnake, 420 + head.x * 32, 32 + head.y * 32, head.width, head.height, 0, 0, 16, 16);
		
		for(Snake s : head.body)
		{
			g.drawScaledImage(Assets.sprSnake, 420 + s.x * 32, 32 + s.y * 32, s.width, s.height, 0, 0, 16, 16);
		}
		
		//Berry
		g.drawScaledImage(Assets.sprSnake, 420 + berry.x * 32, 32 + berry.y * 32, berry.width, berry.height, 16, 0, 16, 16);
		
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
	
	abstract class Snake
	{
		int x, y;
		int width = 32;
		int height = 32;
		
		public int getX()
		{
			return x;
		}	
		
		public int getY()
		{
			return y;
		}
		
		public void setX(int x)
		{
			this.x = x;
		}
		
		public void setY(int y)
		{
			this.y = y;
		}
		
	}
	
	class Head extends Snake
	{
		ArrayList<Snake> body;
		int length;
		int delay, count;
		char dir;
		boolean live;
		
		public Head()
		{
			setX(10);
			setY(10);
			
			dir = 'R';
			live = true;
			
			delay =  10 - (int)(GameManager.speed);
			count = 0;

			body = new ArrayList<Snake>();
			
			body.add(this);
			
			length = (int)(GameManager.speed * 2);
					
		}
		
		public void update()
		{
		
			if(live)
			{
			
		        if(count >= delay)
		        {
		        	
		        	if(body.size() < length)
		        		body.add(new Body(body.get(body.size() - 1)));
		        	
					if(TouchSlide.dir == 'L')
					{
						if(dir != 'R')
							dir = 'L';
					}
					else if(TouchSlide.dir == 'R')
					{
						if(dir != 'L')
							dir = 'R';
					}
					else if(TouchSlide.dir == 'U')
					{
						if(dir != 'D')
							dir = 'U';
					}
					else if(TouchSlide.dir == 'D')
					{
						if(dir != 'U')
							dir = 'D';
					}
		        	
		        	if(canMove(dir))
		        	{
		        		for(int i = body.size() - 1; i > 0; i--)
		        			((Body)body.get(i)).move();
						if(dir == 'R')
							x++;
						else if(dir == 'L')
							x--;
						else if(dir == 'U')
							y--;
						else if(dir == 'D')
							y++;
		        		
		        	}
		        	else
		        	{
		        		live = false;
		        		if(getTimer() > 25)
		        			setTimer(25);
		        	}
		        	
		        	count = 0;
		        }
		        else
		        	count++;
			}
			
			eatBerry();
			
		}
		
		public void eatBerry()
		{
			if(x == berry.x && y == berry.y)
			{
				setWin(true);
				length++;
				berry = new Berry();
        		if(getTimer() > 100)
        			setTimer(100);
			}
			
		}
		
		public boolean canMove(char d)
		{
			boolean move = true;;
			
			for(Snake b : body)
			{
				if(d == 'R')
				{
					if((b.x == x + 1 && b.y == y) || x + 1 > 17)
						move = false;
				}
				else if(d == 'L')
				{
					if((b.x == x - 1 && b.y == y) || x - 1 < 0)
						move = false;
				}
				else if(d == 'U')
				{
					if((b.x == x && b.y == y - 1) || y - 1 < 0)
						move = false;
				}
				else if(d == 'D')
				{
					if((b.x == x && b.y == y + 1) || y + 1 > 17)
						move = false;
				}
			}
		
			return move;
		}
		
	}

	class Body extends Snake
	{
		Snake parent;
		
		public Body(Snake snake) 
		{
			parent = snake;
			setX(snake.getX());
			setY(snake.getY());
		}
		
		public void move()
		{
			x = parent.x;
			y = parent.y;
		}
	}
	
	class Berry
	{
		Random random;
		int x, y;
		int width = 32;
		int height = 32;
		
		public Berry()
		{
			random = new Random();
						
			x = random.nextInt(18);
			y = random.nextInt(18);
		
		}
		

		
	}
}
