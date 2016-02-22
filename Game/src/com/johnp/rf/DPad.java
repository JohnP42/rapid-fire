package com.johnp.rf;

import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;

import android.graphics.Rect;

public class DPad 
{

	boolean[] buttons = new boolean[5];
	Rect[] btnRects = new Rect[5];
	
	public DPad()
	{
		btnRects[0] = new Rect(0,500,100,800);
		btnRects[1] = new Rect(0,500,300,600);
		btnRects[2] = new Rect(200,500,300,800);
		btnRects[3] = new Rect(0,700,300,800);
		btnRects[4] = new Rect(1080,600,1280,800);
	}
	
	public void inputUpdate(TouchEvent e)
	{

		if(inBounds(e, btnRects[0]))
		{
			buttons[0] = true;
			buttons[2] = false;
		}
		else if(inBounds(e, btnRects[2]))
		{
			buttons[0] = false;
			buttons[2] = true;
		}
		else if(!inBounds(e,btnRects[4]))
		{
			buttons[0] = false;
			buttons[2] = false;
		}
		
		if(inBounds(e, btnRects[1]))
		{
			buttons[1] = true;
			buttons[3] = false;
		}
		else if(inBounds(e, btnRects[3]))
		{
			buttons[1] = false;
			buttons[3] = true;
		}
		else if(!inBounds(e,btnRects[4]))
		{
			buttons[1] = false;
			buttons[3] = false;
		}
		
		if(inBounds(e, btnRects[4]))
		{
			buttons[4] = true;
		}
		else if (!inBounds(e,0,500,300,300))
		{
			buttons[4] = false;
		}

		
		if(e.type == TouchEvent.TOUCH_UP)
		{
			if(inBounds(e, btnRects[0]))
				buttons[0] = false;
			if(inBounds(e, btnRects[1]))
				buttons[1] = false;
			if(inBounds(e, btnRects[2]))
				buttons[2] = false;
			if(inBounds(e, btnRects[3]))
				buttons[3] = false;
			if(inBounds(e, btnRects[4]))
				buttons[4] = false;
		}
	}


	
    public static boolean inBounds(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }
	
    public static boolean inBounds(TouchEvent event, Rect r) {
        if (event.x > r.left && event.x < r.left + r.width() - 1 && event.y > r.top
                && event.y < r.top + r.height() - 1)
            return true;
        else
            return false;
    }
    
    public static float getDirectionTo(double x1, double y1, double x2, double y2)
    {
    	float dir = 0.0f;
    	
    	
    	double opp = y2 - y1;
    	double adj = x2 - x1;
    		
    	
    	dir =  (float) Math.toDegrees(Math.atan(opp / adj)); 	
    	
    	if(x2 > x1)
    		dir += 180;
    	
    	return (dir);
    }
    
	public boolean[] getButtons()
	{
		return buttons;
	}
	
	public Rect[] getRects()
	{
		return btnRects;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(Assets.dPad, 0, 500, 0, 0, 300, 300);
		g.drawScaledImage(Assets.dPad, 1080, 600, 200, 200, 300, 0, 100, 100);
	}
	
	
}
