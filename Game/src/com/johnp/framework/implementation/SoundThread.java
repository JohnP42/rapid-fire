package com.johnp.framework.implementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.johnp.framework.Sound;


public class SoundThread extends Thread{
	
	public static BlockingQueue<SoundToPlay> queue = new LinkedBlockingQueue<SoundToPlay>();
	
    public SoundThread() {

    }
	
    public static void add(Sound s, float r)
    {
    	queue.add(new SoundToPlay(s,r));
    }
    
	public void run()
	{
		
		while(true)
		{

			SoundToPlay sound;
			
				try {

					
					sound = queue.take();
					
						sound.sound.playAtSpeed(1f, sound.rate);
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			


		}
	
	}

}

class SoundToPlay
{
	public Sound sound;
	public float rate;
	
	public SoundToPlay(Sound s, float r)
	{
		sound = s;
		rate = r;
	}
	
}
