package com.johnp.rf;

import android.graphics.Rect;

public class Animation 
{

	Rect[] srcRect;
	int imageIndex;
	float frameTime;
	float counter = 0;
	
	public Animation()
	{
		srcRect = new Rect[1];
		imageIndex = 0;
		frameTime = 1f;
	}
	
	public Animation(Rect[] sRect, float time)
	{
		srcRect = sRect;
		frameTime = time;
		imageIndex = 0;
	}
	
	public void updateAnimation(float deltaTime)
	{
		if(counter >= frameTime)
		{
			if(imageIndex == srcRect.length - 1)
				imageIndex = 0;
			else
				imageIndex++;
			
			counter = 0;
		}
		else
			counter += deltaTime / 10f;
	}
	
	public Rect getCurrentRect()
	{
		return srcRect[imageIndex];
	}
	
	public Rect[] getCurrentRectArray()
	{
		return srcRect;
	}
	
	public int getImageIndex()
	{
		return imageIndex;
	}
	
	public void setImageIndex(int x)
	{
		imageIndex = x;
	}
	
	public float getFrameTime()
	{
		return frameTime;
	}
	
	public void setFrameTime(float t)
	{
		frameTime = t;
	}
	
}
