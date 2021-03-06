package com.johnp.rf;

import java.util.List;
import java.util.ArrayList;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Screen;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.rf.microgames.GameManager;
import com.johnp.rf.microgames.MicroGame;


public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}


    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {


            	if (inBounds(event, 520, 240, 245, 80)) {
                    //START GAME
            		GameManager.speed = 1;
            		GameManager.score = 0;
            		GameManager.success = -1;
            		GameManager.hp = 4;
            		GameManager.newGameList(game);
                    game.setScreen(new GameScreen(game));               
                }
            	else if (inBounds(event, 520, 600, 245, 80)) 
            	{
                    //Quit
            		android.os.Process.killProcess(android.os.Process.myPid());             
                }


            }
        }
    }


    private boolean inBounds(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }


    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.menu, 0, 0);
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {


    }


    @Override
    public void dispose() {


    }


    @Override
    public void backButton() {
        //Display "Exit Game?" Box
    	android.os.Process.killProcess(android.os.Process.myPid());
    }

    
    //Mine
    public ArrayList<MicroGame> getRandomGames()
    {
    	
    	
    	return null;
    }
}
