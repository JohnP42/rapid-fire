package com.johnp.rf.microgames;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.framework.implementation.TouchSlide;
import com.johnp.rf.Animation;
import com.johnp.rf.Assets;

public class MicroRetroEatman extends MicroGame {

	Paint paint;
	Eatman eatman;
	ArrayList<Apple> apples;
	
	Animation animEatRight;
	Animation animEatDown;
	Animation animEatLeft;
	Animation animEatUp;
	
	
	public MicroRetroEatman(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 175)
			timer = 175;
		
		setWin(false);
		setHint("Eat");
		setDescription("Slide up, down, left, or right to move and eat all the apples.");
		
        Rect[] temp = new Rect[3];
        temp[0] = new Rect(0,0,16,16);
        temp[1] = new Rect(16,0,32,16);
        temp[2] = new Rect(32,0,48,16);
        
        animEatRight = new Animation(temp, 2f - GameManager.speed / 10f);
        
        Rect[] temp2 = new Rect[3];
        temp2[0] = new Rect(0,0,16,16);
        temp2[1] = new Rect(48,0,64,16);
        temp2[2] = new Rect(64,0,80,16);
        
        animEatDown = new Animation(temp2, 2f - GameManager.speed / 10f);
        
        Rect[] temp3 = new Rect[3];
        temp3[0] = new Rect(0,0,16,16);
        temp3[1] = new Rect(80,0,96,16);
        temp3[2] = new Rect(96,0,112,16);
        
        animEatLeft = new Animation(temp3, 2f - GameManager.speed / 10f);
        
        Rect[] temp4 = new Rect[3];
        temp4[0] = new Rect(0,0,16,16);
        temp4[1] = new Rect(112,0,128,16);
        temp4[2] = new Rect(128,0,144,16);
        
        animEatUp = new Animation(temp4, 2f - GameManager.speed / 10f);
		
        eatman = new Eatman();
        apples = new ArrayList<Apple>();
        
        for(int i = 0; i < 8; i++)
        	apples.add(new Apple(i));
        
		paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		animEatRight.updateAnimation(deltaTime);
		animEatDown.updateAnimation(deltaTime);
		animEatLeft.updateAnimation(deltaTime);
		animEatUp.updateAnimation(deltaTime);
		
        
        eatman.update();
        
        if(apples.size() == 0)
        {
        	setWin(true);
    		if(getTimer() > 25)
    			setTimer(25);
        }
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
		
		g.drawLine(439, 199, 841, 199, Color.BLUE);
		g.drawLine(841, 199, 841, 601, Color.BLUE);
		g.drawLine(439, 600, 841, 600, Color.BLUE);
		g.drawLine(439, 199, 439, 601, Color.BLUE);
		
		g.drawLine(568, 328, 712, 328, Color.BLUE);
		g.drawLine(711, 328, 711, 472, Color.BLUE);
		g.drawLine(568, 471, 712, 471, Color.BLUE);
		g.drawLine(568, 328, 568, 472, Color.BLUE);
		
		for(Apple a : apples)
			g.drawScaledImage(Assets.sprEatman, a.x, a.y, a.width, a.height, 144, 0, 16, 16);
		
		if(eatman.dir == 'R')
			g.drawScaledImage(Assets.sprEatman, eatman.x, eatman.y, eatman.width, eatman.height, animEatRight.getCurrentRect().left, animEatRight.getCurrentRect().top, animEatRight.getCurrentRect().width(), animEatRight.getCurrentRect().height());
		else if(eatman.dir == 'D')
			g.drawScaledImage(Assets.sprEatman, eatman.x, eatman.y, eatman.width, eatman.height, animEatDown.getCurrentRect().left, animEatDown.getCurrentRect().top, animEatDown.getCurrentRect().width(), animEatDown.getCurrentRect().height());
		else if(eatman.dir == 'L')
			g.drawScaledImage(Assets.sprEatman, eatman.x, eatman.y, eatman.width, eatman.height, animEatLeft.getCurrentRect().left, animEatLeft.getCurrentRect().top, animEatLeft.getCurrentRect().width(), animEatLeft.getCurrentRect().height());
		else if(eatman.dir == 'U')
			g.drawScaledImage(Assets.sprEatman, eatman.x, eatman.y, eatman.width, eatman.height, animEatUp.getCurrentRect().left, animEatUp.getCurrentRect().top, animEatUp.getCurrentRect().width(), animEatUp.getCurrentRect().height());
		
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

	
	class Eatman
	{
		int x, y, width, height;
		Rect body;
		int speed = (int) GameManager.speed * 5 + 5;
		char dir;
		
		
		public Eatman()
		{
			x = 440;
			y = 200;
			width = 128;
			height = 128;
			dir = 'R';
			
			body = new Rect(x,y,x + width,y + height);
		}
		
		public void update()
		{
			if(TouchSlide.dir == 'L')
			{
				if(y == 200 || y == 600 - height)
					dir = 'L';
			}
			else if(TouchSlide.dir == 'R')
			{
				if(y == 200 || y == 600 - height)
					dir = 'R';
			}
			
			if(TouchSlide.dir == 'U')
			{
				if(x == 840 - width || x == 440)
					dir = 'U';
			}
			else if(TouchSlide.dir == 'D')
			{
				if(x == 840 - width || x == 440)
					dir = 'D';
			}
			
			if(dir == 'R')
				x += speed;
			if(dir == 'D')
				y += speed;
			if(dir == 'L')
				x -= speed;
			if(dir == 'U')
				y -= speed;
			
			if(x < 440)
				x = 440;
			else if(x > 840 - width)
				x = 840 - width;
			
			if(y < 200)
				y = 200;
			else if(y > 600 - height)
				y = 600 - height;
			
			ArrayList<Apple> temp = new ArrayList<Apple>();
			temp.addAll(apples);
			
			for(Apple a : temp)
			{
				if(Rect.intersects(body, a.body))
				{
					apples.remove(a);
					SoundThread.add( Assets.sfxChomp, 1f);
				}
			}
			
			body = new Rect(x,y,x + width,y + height);
		}
	}
	
	class Apple
	{
		
		int x, y, width, height;
		Rect body;
		
		public Apple(int i)
		{
			width = 128;
			height = 128;
			
			if(i < 3)
			{
				x = 440 + (i * 128);
				y = 200;
			}
			else if(i < 6)
			{
				x = 440 + ((i - 3) * 128);
				y = 600 - height;
			}
			else if(i == 6)
			{
				x = 440;
				y = 200 + height;
			}
			else if(i == 7)
			{
				x = 840 - width;
				y = 200 + height;
			}
			
			body = new Rect(x + width / 3,y + height / 3 ,x + width / 3 + width / 3,y + height / 3 + height / 3);
		}
	}
}
