package model;

import java.util.HashSet;
import java.util.Set;

import model.exceptions.*;
import model.gizmos.*;

/**
 * Represents the board the game is played on
 *
 */
public class Board {

	/*
	 * This a bad idea, but it works with little tought for now
	 */
	private boolean[][] grid = new boolean[Global.BOARDHEIGHT][Global.BOARDWIDTH];
	private Set<IGizmo> gizmos = new HashSet<IGizmo>();
	
	/**
	 * Adds the gizmo to the board.
	 * 
	 * @param g The gizmo to add
	 * @throws InvalidGridPosException Invalid grid position
	 * @throws GridPosAlreadyTakenException Gird position already occupied
	 */
	public void addGizmo(IGizmo g) throws GridPosAlreadyTakenException, InvalidGridPosException {
		checkArea(g);
		
		//add
		gizmos.add(g);
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();		
		
		//mark as taken
		for(int i = x; i < width + x; i++){
			for (int j = y; j < height + y; j++){
				System.out.flush();
				grid[i][j] = true;
			}
		}
	}//End addGizmo();
	
	
	/**
	 * Checks the area on the board.
	 * 
	 * @param g The gizmo to added.s
	 */
	private boolean checkArea(IGizmo g){
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();
		
		
		if (x < 30){//check if already filled
			for(int i = x; i < x+width; i++){
				for (int j = y; j < y+height; j++){
					if(grid[i][j]){
						g.setPos(x+1, y);
						if(checkArea(g)) return true;
						else{
							g.setPos(0, y+1);
							if(checkArea(g)) return true;
						}
					}
				}
			}
		return true;
		}else return false;
	}
	
	/**
	 * Remove the given gizmo from the board
	 * 
	 * @param g The gizmo to remove;
	 */
	public void removeGizmo(IGizmo g){
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();
		
		//add
		gizmos.remove(g);
		
		//mark as empty
		for(int i = x; i < width; i++){
			for (int j = y; j < height; j++){
				grid[i][j] = false;
			}
		}
	}
}
