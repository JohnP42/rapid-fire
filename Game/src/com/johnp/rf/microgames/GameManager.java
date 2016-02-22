package com.johnp.rf.microgames;

import java.util.ArrayList;
import java.util.Collections;

import com.johnp.framework.Game;

public class GameManager {

	public static ArrayList<MicroGame> games = new ArrayList<MicroGame>();
	public static float speed;
	public static int score;
	public static int success;
	public static int hp, currentGame;
	public static final int maxSpeed = 5;
	
	
	public static void reset()
	{
		speed = 0;
		score = 0;
		success = 0;
		hp = 4;
		currentGame = 0;

		games.clear();
	}
	
	public static void newGameList(Game game)
	{
		games.clear();
		
		//Retro
		games.add(new MicroRetroPong(game));
		games.add(new MicroRetroSpaceShooter(game));
		games.add(new MicroRetroJumpman(game));
		games.add(new MicroRetroEatman(game));
		games.add(new MicroRetroPump(game));
		games.add(new MicroRetroDuckHunt(game));
		games.add(new MicroRetroTetris(game));
		//games.add(new MicroRetroSnake(game));
		games.add(new MicroRetroAsteroids(game));
		games.add(new MicroRetroFrogger(game));
		
		
		//Sports
	
		
		Collections.shuffle(games);
	}
	
	public static MicroGame getNextGame(int x)
	{	
		return games.get(x);
	}
	
}
