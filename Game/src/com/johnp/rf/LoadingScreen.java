package com.johnp.rf;

import android.graphics.Color;
import android.graphics.Paint;

import com.johnp.framework.Audio;
import com.johnp.framework.Game;
import com.johnp.framework.Graphics.ImageFormat;
import com.johnp.framework.Screen;
import com.johnp.framework.Graphics;

public class LoadingScreen extends Screen {

	Paint paint;
	
	public LoadingScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		paint = new Paint();
        paint.setTextSize(200);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
	}

	@Override
	public void update(float deltaTime) {
		
        Graphics g = game.getGraphics();
        
        Audio a = game.getAudio();
        

        Assets.menu = g.newImage("sprites/menu/bgMenu.png", ImageFormat.RGB565);
        Assets.robot = g.newImage("sprites/menu/neutralRobot.png", ImageFormat.RGB565);
        Assets.robotWin = g.newImage("sprites/menu/winRobot.png", ImageFormat.RGB565);
        Assets.robotLose = g.newImage("sprites/menu/loseRobot.png", ImageFormat.RGB565);
        Assets.robotTransition = g.newImage("sprites/menu/transitionRobot.png", ImageFormat.RGB565);
        Assets.dPad = g.newImage("sprites/menu/dPad.png", ImageFormat.ARGB4444);
        
        //Retro
        Assets.sprSpaceShooter = g.newImage("sprites/retro/spaceShooter.png", ImageFormat.RGB565);
        Assets.sprJumpman = g.newImage("sprites/retro/jumpman.png", ImageFormat.ARGB4444);
        Assets.sprEatman = g.newImage("sprites/retro/eatman.png", ImageFormat.RGB565);
        Assets.sprPump = g.newImage("sprites/retro/srcRetroBalloon.png", ImageFormat.ARGB4444);
        Assets.sprDuck = g.newImage("sprites/retro/sprDuck.png", ImageFormat.ARGB4444);
        Assets.sprStack = g.newImage("sprites/retro/sprStack.png", ImageFormat.RGB565);
        Assets.sprFrog = g.newImage("sprites/retro/sprFrog.png", ImageFormat.RGB565);
        Assets.spMeteors = g.newImage("sprites/retro/spMeteors.png", ImageFormat.ARGB4444);
        Assets.sprSnake = g.newImage("sprites/retro/sprSnake.png", ImageFormat.RGB565);
        
        Assets.sfxPong = a.createSound("audio/sound/sfxPong.wav");
        Assets.sfxGun = a.createSound("audio/sound/gun.wav");
        Assets.sfxJump = a.createSound("audio/sound/jump.wav");
        Assets.sfxLaser = a.createSound("audio/sound/laser.wav");
        Assets.sfxPump = a.createSound("audio/sound/pump.wav");
        Assets.sfxChomp = a.createSound("audio/sound/chomp.wav");
        Assets.sfxFlap = a.createSound("audio/sound/flap.wav");
        Assets.sfxEngine = a.createSound("audio/sound/engine.wav");
        Assets.sfxCrash = a.createSound("audio/sound/crash.wav");
        
        //Sports
       
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        game.setScreen(new MainMenuScreen(game));


	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
				
		g.drawString("Loading...", g.getWidth() / 2, g.getHeight() / 2, paint);
		
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

}
