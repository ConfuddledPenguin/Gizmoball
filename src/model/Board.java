package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

/**
 * Represents the board the game is played on
 *
 */
public class Board {

	/*
	 * This a bad idea, but it works with little thought for now
	 */
	private IGizmo[][] grid = new IGizmo[Global.BOARDHEIGHT][Global.BOARDWIDTH];
	private List<IGizmo> gizmos = new ArrayList<IGizmo>();
	
	/*
	 * (non-Javadoc)
	 * @see model.IBoard#addGizmo()
	 */
	public boolean addGizmo(IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException{
		
		if(gizmos.contains(g)){
			return false;
		}
		
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
		
		return true;
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
	public List<IGizmo> getGizmos() {
		
		return Collections.unmodifiableList(gizmos);
	}


	/*
	 * (non-Javadoc)
	 * @see model.IBoard#getGizmo(int x, int y)
	 */
	public IGizmo getGizmo(int x, int y) {
		return grid[x][y];
	}
	
	public IGizmo getGizmoForMove(Point p){
		for(IGizmo g: this.gizmos){
			if(g.getXPos() == p.x && g.getYPos() == p.y){
				return g;
			}
		}
		return null;
	}


	public void moveGizmo(IGizmo g, Point oldPoint, Point newPoint) {
		this.grid[oldPoint.x][oldPoint.y] = null;
		this.grid[newPoint.x][newPoint.y] = g;
	}
}
