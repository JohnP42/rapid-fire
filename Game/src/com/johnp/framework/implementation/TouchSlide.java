package com.johnp.framework.implementation;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


public class TouchSlide extends SimpleOnGestureListener
{
	
	public static char dir;
	
	public TouchSlide()
	{
		dir = 'N';
	}
	
	private static final int SWIPE_THRESHOLD = 50;
    private static final int SWIPE_VELOCITY_THRESHOLD = 10;

    @Override
    public boolean onDown(MotionEvent e) {
    	dir = 'N';
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
    {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }	
	
    public void onSwipeRight() {
    	dir = 'R';
    }

    public void onSwipeLeft() {
    	dir = 'L';
    }

    public void onSwipeTop() {
    	dir = 'U';
    }

    public void onSwipeBottom() {
    	dir = 'D';
    }
}
