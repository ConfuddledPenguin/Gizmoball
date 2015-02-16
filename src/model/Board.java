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
	public void addGizmo(IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException{
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();
		
		//check if in bounds
		if(x + width >= Global.BOARDWIDTH || y + height >= Global.BOARDHEIGHT){
			throw new InvalidGridPosException("Position: " + x + ":" + y + 
					"is invalid. Please ensure Grid position is viable.");
		}
		
		//check if already filled
		for(int i = x; i < width; i++){
			for (int i1 = y; i1 < height; i++){
				if(grid[i][i1]){
					throw new GridPosAlreadyTakenException("Postion: " + x + ":" + y + 
					"is already taken");
				}
			}
		}
		
		//add
		gizmos.add(g);
		
		//mark as taken
		for(int i = 0; i < width; i++){
			for (int i1 = 0; i1 < height; i++){
				grid[i1][i] = true;
			}
		}
	}//End addGizmo();
	
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
			for (int i1 = y; i1 < height; i++){
				grid[i][i1] = false;
			}
		}
	}
}
