package com.johnp.rf.microgames;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.framework.implementation.SoundThread;
import com.johnp.rf.Assets;

public class MicroRetroPump extends MicroGame {

	Paint paint;
	Man man;
	
	public MicroRetroPump(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 125)
			timer = 125;
		
		setWin(false);
		setHint("Pump!");
		setDescription("Touch the screen repeatedly to pump air into the ballon.");
		
		man = new Man();
		
		paint = new Paint();
        paint.setAntiAlias(true);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		man.update();
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.rgb(153, 204, 255));

		g.drawScaledImage(Assets.sprJumpman,128, 400, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,300, 200, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,800, 300, 128, 128, 112, 0, 21, 16);
		g.drawScaledImage(Assets.sprJumpman,1200, 500, 128, 128, 112, 0, 21, 16);
		
		for(int i = 0; i < 10; i++)
			g.drawScaledImage(Assets.sprJumpman, i * 128, man.y + man.height, 128, 128, 80, 0, 16, 16);
		
		//Man
		if(man.pumps < 5)
		{
			if(man.state == 0)
				g.drawScaledImage(Assets.sprPump, man.x, man.y, man.width, man.height, 0, 0, 16, 16);
			else
				g.drawScaledImage(Assets.sprPump, man.x, man.y, man.width, man.height, 16, 0, 16, 16);
		}
		else
			g.drawScaledImage(Assets.sprPump, man.x, man.y, man.width, man.height, 128, 0, 16, 16);
		
		//Ballon
		g.drawScaledImage(Assets.sprPump, man.x + man.width, man.y - man.height, man.width * 2, man.height * 2, (man.pumps + 2) * 16, 0, 16, 16);
		
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

	class Man
	{
		int x, y, width, height;
		int pumps;
		int state;
	
		public Man()
		{
			width = 128;
			height = 128;
			x = 640 - width;
			y = 720 - height;	
			
			pumps = 0;
			state = 0;
		}
		
		public void update()
		{
			
			if(pumps < 5)
			{
		        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	
		        int len = touchEvents.size();
		        for (int i = 0; i < len; i++)
		        {
		            TouchEvent event = touchEvents.get(i);
	
		            if(event.type == TouchEvent.TOUCH_UP && state == 1)
		            {
		            	state = 0;
		            	pumps++;
		            	SoundThread.add( Assets.sfxPump, 1f);
		            	break;
		            }
		            else if(event.type == TouchEvent.TOUCH_DOWN && state == 0)
		            {
		            	state = 1;
		            	break;
		            }
	
		        }	
			}
			else if (!getWin())
			{
				setWin(true);
        		if(getTimer() > 50)
        			setTimer(50);
			}
				
		}
		
	}
	
}
