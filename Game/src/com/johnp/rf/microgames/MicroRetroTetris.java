package com.johnp.rf.microgames;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.johnp.framework.Game;
import com.johnp.framework.Graphics;
import com.johnp.framework.Input.TouchEvent;
import com.johnp.rf.Assets;

public class MicroRetroTetris extends MicroGame {

	Paint paint;
	int[][] grid;
	Piece currentPiece;
	boolean done;
	
	//Suck it Nintendo!
	public MicroRetroTetris(Game game) {
		super(game);
		
		setTimer(500 - GameManager.speed * 100f);
		if (timer < 400)
			timer = 400;
		
		setWin(false);
		setHint("Stack!");
		setDescription("Touch the screen to guide the falling piece to complete a row.");
		
		grid = new int[7][11];
		currentPiece = new Piece();
		done = false;
		
		//set up Grid
		
		switch(currentPiece.type)
		{
		case 1: 
			grid[6][9] = 5;
			grid[5][9] = 5;
			grid[6][10] = 5;
			grid[5][10] = 5;
			grid[0][10] = 7;
			grid[1][10] = 7;
			grid[2][10] = 7;
			grid[3][10] = 7;
			break;
		case 2: 
			grid[3][10] = 7;
			grid[4][10] = 7;
			grid[5][10] = 7;
			grid[6][10] = 7;
			grid[5][9] = 5;
			grid[6][9] = 5;
			grid[5][8] = 5;
			grid[6][8] = 5;
			break;
		case 3: 
			grid[0][9] = 5;
			grid[1][9] = 5;
			grid[0][10] = 5;
			grid[1][10] = 5;
			grid[6][10] = 7;
			grid[3][10] = 7;
			grid[4][10] = 7;
			grid[5][10] = 7;
			break;
		case 4: 
			grid[6][9] = 5;
			grid[5][9] = 5;
			grid[6][10] = 5;
			grid[5][10] = 5;
			grid[0][10] = 7;
			grid[1][10] = 7;
			grid[2][10] = 7;
			grid[3][10] = 7;
			break;
		case 5: 
			grid[6][9] = 5;
			grid[5][9] = 5;
			grid[6][10] = 5;
			grid[5][10] = 5;
			grid[0][10] = 6;
			grid[0][9] = 6;
			grid[0][8] = 6;
			grid[1][9] = 6;
			grid[1][10] = 2;
			grid[2][10] = 2;
			grid[2][9] = 2;
			grid[2][8] = 2;
			break;
		case 6: 
			grid[6][9] = 5;
			grid[5][9] = 5;
			grid[6][10] = 5;
			grid[5][10] = 5;
			grid[4][10] = 7;
			grid[1][10] = 7;
			grid[2][10] = 7;
			grid[3][10] = 7;
			break;
		case 7:
			grid[0][10] = 6;
			grid[0][9] = 6;
			grid[0][8] = 6;
			grid[1][9] = 6;
			grid[1][10] = 2;
			grid[2][10] = 2;
			grid[2][9] = 2;
			grid[2][8] = 2;
			break;
		}
		
		paint = new Paint();
        paint.setAntiAlias(true);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

		setTimer(getTimer() - deltaTime);
		
		if(timer <= 0)
			returnToGameScreen();
		
		currentPiece.update();
		
		if(done)
		{
			currentPiece = new Piece();
			done = false;
		}
		
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
		
		g.clearScreen(Color.BLACK);
		
		//Border
		g.drawScaledImage(Assets.sprStack, 384, 0, 512, 768, 48, 0, 64, 96);
		
		//grid
		for(int i = 0; i < grid.length; i++)
		{
			for(int z = 0; z < grid[0].length; z++)
			{
				if(grid[i][z] != 0)
				g.drawScaledImage(Assets.sprStack, 416 + i * 64, 32 + z * 64, 64, 64, (grid[i][z] - 1) * 4, 0, 4, 4);
			}
		}
		
		//Piece
		if(currentPiece != null)
		{
			for(int i = 0; i < currentPiece.blocks.length; i++)
			{
				for(int z = 0; z < currentPiece.blocks[0].length; z++)
				{
					if(currentPiece.blocks[i][z] != 0)
					g.drawScaledImage(Assets.sprStack, 416 + currentPiece.x * 64 + i * 64, 32 + currentPiece.y * 64 + z * 64, 64, 64, (currentPiece.blocks[i][z] - 1) * 4, 0, 4, 4);
				}
			}
		}
		
		//Score
		
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setColor(Color.WHITE);
		paint.setTextSize(80);
		
		if(getWin())
			g.drawString("Score:" + (GameManager.score + 1), 0, 200, paint);
		else
			g.drawString("Score:" + GameManager.score, 0, 200, paint);
		
		paint.setColor(Color.WHITE);
		g.drawString("Speed:" + (int)(GameManager.speed * 4), 0, 400, paint);
		
		//timer
        paint.setColor(Color.WHITE);
        paint.setTextSize(26);
        paint.setTextAlign(Align.LEFT);
		g.drawString((int)(timer / 100) + "", 0, 20, paint);
		g.drawRect(28, 5, (int)timer, 5, Color.WHITE);
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

	
	class Piece
	{
		int[][] blocks;
		int x, y;
		Random random = new Random();
		int delay, count;
		int type;
		
		public Piece()
		{
			x = 2;
			y = 0;
			
			delay =  20 - (int)(GameManager.speed * 2);
			count = 0;
			
			int x = random.nextInt(7) + 1;
			
			type = x;
			
			switch(x)
			{
			case 1: blocks = new int[][]
					{
					{x,0},
					{x,0},
					{x,x},
					};
				break;
			case 2: blocks = new int[][]
					{
					{0,x},
					{0,x},
					{x,x},
					};
				break;
			case 3: blocks = new int[][]
					{
					{0,x,x},
					{x,x,0},
					};
				break;
			case 4: blocks = new int[][]
					{
					{x,x,0},
					{0,x,x},
					};
				break;
			case 5: blocks = new int[][]
					{
					{x,x},
					{x,x},
					};
				break;
			case 6: blocks = new int[][]
					{
					{x,x,x},
					{0,x,0},
					};
				break;
			case 7: blocks = new int[][]
					{
					{x},
					{x},
					{x},
					{x}
					};
				break;
			}
		}
		
		public void update()
		{
			
	        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


	        int len = touchEvents.size();
	        for (int i = 0; i < len; i++)
	        {
	            TouchEvent event = touchEvents.get(i);

	            int moveTo = (int)((float)(event.x - 480) / 64f);
	            
	            if(moveTo < x && canMove(-1))
	            {
	            	x--;
	            }
	            else if(moveTo > x && canMove(1))
	            	x++;
	     
	        }
	        
	        //gravity
	        if(count >= delay)
	        {
	        	
	        	
	        	if(canFall())
	        	{
		        	y++;
		        	count = 0;
	        	}
	        	else
	        	{
	        		setPiece();    		
	        		fixGrid(check());
	        	}
	        }
	        else
	        	count++;
		}
		
		public boolean canFall()
		{
			
			for(int i = 0; i < blocks.length; i++)
			{
				for(int z = 0; z < blocks[0].length; z++)
				{
					if(blocks[i][z] != 0)
					{
						if(y + z + 1 < grid[0].length)
						{
							if(grid[x + i][y + z + 1] != 0)
								return false;
						}
						else
							return false;
					}
				}
			}
			
			return true;
		}
		
		//1 for right -1 for left
		public boolean canMove(int dir)
		{
			
			for(int i = 0; i < blocks.length; i++)
			{
				for(int z = 0; z < blocks[0].length; z++)
				{
					if(blocks[i][z] != 0)
					{
						if(x + i + dir < grid.length && x + i + dir >= 0)
						{
							if(grid[x + i + dir][y + z] != 0)
								return false;
						}
						else 
							return false;
					}
				}
			}
			
			return true;
		}
		
		
		public void setPiece()
		{
			for(int i = 0; i < blocks.length; i++)
			{
				for(int z = 0; z < blocks[0].length; z++)
				{
					if(blocks[i][z] != 0)
					{
						grid[x + i][y + z] = blocks[i][z];
					}
				}
			}
			
			done = true;
		}
		
		public int[] check()
		{
			int lowest = -1;
			int gap = 0;
			
			for(int z = 0; z < grid[0].length; z++)
			{
				boolean canWin = false;
				
				for(int i = 0; i < grid.length; i++)
				{
					if(grid[i][z] != 0)
						canWin = true;
					else
					{
						canWin = false;
						break;	
					}
					
				}
				
				if(canWin)
				{
					setWin(true);
					
	        		if(getTimer() > 25)
	        			setTimer(25);
					
					for(int i = 0; i < grid.length; i++)
					{
						if(z - 1 > 0)
						{
							grid[i][z] = 0;
						}
					}
					lowest = z;
					gap++;
				}
			}
			
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		return new int[]{lowest,gap};
			
		}
		
		public void fixGrid(int[] lg)
		{
			
			if(lg[0] != -1)
			{
				for(int i = grid.length - 1; i >= 0; i--)
				{
					for(int z = lg[0] - lg[1] + 1; z >= 0; z--)
					{
						if(z - lg[1] >= 0)
						{
							grid[i][z] = grid[i][z - lg[1]];
							grid[i][z - lg[1]] = 0;
						}
					}
				}
			}
		}
	}
}
