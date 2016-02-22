package com.johnp.framework.implementation;

import android.media.SoundPool;

import com.johnp.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    @Override
    public int play(float volume) {
        return soundPool.play(soundId, volume, volume, 0, 0, 1);
    }
    
    @Override
    public int playAtSpeed(float volume, float rate)
    {
    	return soundPool.play(soundId, volume, volume, 0, 0, rate);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

	@Override
	public void stop(int id) {
		soundPool.stop(id);
		
	}

	@Override
	public int playAtLoop(float volume, float rate, int loop) {
		
		return soundPool.play(soundId, volume, volume, 0, loop, rate);
	}

}