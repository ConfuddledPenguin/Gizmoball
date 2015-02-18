package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.exceptions.*;
import model.gizmos.*;

/**
 * Represents the board the game is played on
 *
 */
public class Board implements IBoard {

	/*
	 * This a bad idea, but it works with little thought for now
	 */
	private IGizmo[][] grid = new IGizmo[Global.BOARDHEIGHT][Global.BOARDWIDTH];
	private Set<IGizmo> gizmos = new HashSet<IGizmo>();
	
	/*
	 * (non-Javadoc)
	 * @see model.IBoard#addGizmo()
	 */
	public void addGizmo(IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException{
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();
		
		//check if in bounds
		if(x + width > Global.BOARDWIDTH || y + height > Global.BOARDHEIGHT){
			throw new InvalidGridPosException("Position: " + x + ":" + y + 
					"is invalid. Please ensure Grid position is viable.");
		}
		
		//check if already filled
		for(int i = x; i < x + width; i++){
			for (int j = y; j < y + height; j++){
				if(grid[i][j] != null){
					throw new GridPosAlreadyTakenException("Postion: " + x + ":" + y + 
					"is already taken");
				}
			}
		}
		
		//add
		gizmos.add(g);
		
		//mark as taken
		for(int i = x; i < width + x; i++){
			for (int j = y; j < height + y; j++){
				grid[i][j] = g;
			}
		}
	}//End addGizmo();
	
	
	/*
	 * (non-Javadoc)
	 * @see model.IBoard#removeGizmo()
	 */
	public void removeGizmo(IGizmo g){
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getWidth();
		
		//remove
		gizmos.remove(g);
		
		//mark as empty
		for(int i = x; i < x + width; i++){
			for (int j = y; j < y + height; j++){
				grid[i][j] = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see model.IBoard#getGizmos()
	 */
	@Override
	public Set<IGizmo> getGizmos() {
		
		return Collections.unmodifiableSet(gizmos);
	}


	/*
	 * (non-Javadoc)
	 * @see model.IBoard#getGizmo(int x, int y)
	 */
	@Override
	public IGizmo getGizmo(int x, int y) {
		return grid[x][y];
	}
}
