package com.johnp.framework;

public interface Sound {
    public int play(float volume);

    public void dispose();

    public int playAtSpeed(float volume, float rate);
    
    public int playAtLoop(float volume, float rate, int loop);
	
	public void stop(int id);
}
 