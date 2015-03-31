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

	private IGizmo[][] grid = new IGizmo[Global.BOARDHEIGHT][Global.BOARDWIDTH];
	private List<IGizmo> gizmos = new ArrayList<IGizmo>();

	/**
	 * Adds the given gizmo to the board
	 * 
	 * @param g The gizmo to add
	 * 
	 * @return false if gizmo is already present otherwise true.
	 * 
	 * @throws InvalidGridPosException Invalid grid pos
	 * @throws GridPosAlreadyTakenException Grid pos already taken
	 */
	public boolean addGizmo(IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException{
		
		if(gizmos.contains(g)){
			return false;
		}
		
		checkPosValid(g.getXPos(), g.getYPos(), g.getWidth(), g.getHeight(), g);
		
		//add
		gizmos.add(g);
		
		markAsTaken(g);
		
		return true;
	}//End addGizmo();
	
	
	/**
	 * Removes the given gizmo from the board
	 * 
	 * @param g The gizmo to remove
	 * 
	 * @return false if gizmo was not registered, otherwise true;
	 */
	public boolean removeGizmo(IGizmo g){
		
		//remove
		if( ! gizmos.remove(g) ){
			return false;
		}
		
		markAsEmpty(g);
		
		return true;
	}


	/**
	 * Get all gizmos
	 * 
	 * @return this.gizmos
	 */
	public List<IGizmo> getGizmos() {
		
		return Collections.unmodifiableList(gizmos);
	}

	/**
	 * Get the gizmo at the given x and y
	 * 
	 * @param x The x coord
	 * @param y The y coord
	 * 
	 * @return The gizmo
	 */
	public IGizmo getGizmo(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 * Clears the board.
	 * 
	 * WARNING: can not be undone. ENSURE ACTION
	 * IS WANTED.
	 * 
	 * @return true when successful
	 * 
	 */
	public boolean clear(){
		
		gizmos = new ArrayList<IGizmo>();
		
		//mark as empty
		for(int i = 0; i < Global.BOARDWIDTH; i++){
			for (int j = 0; j < Global.BOARDHEIGHT; j++){
				grid[i][j] = null;
			}
		}
		
		return true;
	}

	/**
	 * Move the given gizmo from the old point
	 * to the new point.
	 * 
	 * This also updates the gizmos coords
	 * 
	 * @param g The gizmo to move
	 * @param oldPoint Where it is
	 * @param newPoint Where to move it to
	 * 
	 * @return false if gizmo does not exist, otherwise true;
	 * 
	 * @throws GridPosAlreadyTakenException  Grid pos already taken
	 * @throws InvalidGridPosException Invalid Grid pos
	 */
	public boolean moveGizmo(IGizmo g, Point oldPoint, Point newPoint) throws InvalidGridPosException, GridPosAlreadyTakenException {
		
		if(!gizmos.contains(g)){
			return false;
		}
		
		if(!oldPoint.equals(newPoint)){
			checkPosValid( (int) newPoint.getX(), (int) newPoint.getY(), g.getWidth(), g.getHeight(), g);
		}
		
		markAsEmpty(g);
		
		//update gizmo
		g.setPos(newPoint.x, newPoint.y);
		
		markAsTaken(g);
		return true;
		
		
		
	}
	
	/**
	 * Is the given space empty
	 * 
	 * @param x The xcoord
	 * @param y The ycoord
	 * @param width The width
	 * @param height The height
	 * 
	 * @return true if empty, otherwise false;
	 * 
	 * @throws InvalidGridPosException Gri pos invalid
	 */
	public boolean isEmpty(int x, int y, int width, int height) throws InvalidGridPosException{
		
		try{
			checkPosValid(x, y, width, height, null);
		}catch(GridPosAlreadyTakenException e){
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Marks the grid as taken
	 * 
	 * @param g The gizmo to mark for
	 */
	private void markAsTaken(IGizmo g){
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getHeight();
		
		//mark as taken
		for(int i = x; i < width + x; i++){
			for (int j = y; j < height + y; j++){
				grid[i][j] = g;
			}
		}
		
	}
	
	/**
	 * Mark gird pos as taken for gizmo g
	 * 
	 * @param g The gizmo
	 */
	private void markAsEmpty(IGizmo g){
		
		int x = g.getXPos();
		int y = g.getYPos();
		int width = g.getWidth();
		int height = g.getHeight();
		
		//mark as empty
		for(int i = x; i < x + width; i++){
			for (int j = y; j < y + height; j++){
				grid[i][j] = null;
			}
		}
		
	}
	
	/**
	 * Check if grid pos is valid
	 * 
	 * @param x The x coord
	 * @param y The y coord
	 * @param width The width
	 * @param height The height
	 * @param The gizmo to check for
	 * 
	 * @throws InvalidGridPosException 
	 * @throws GridPosAlreadyTakenException
	 */
	private void checkPosValid(int x, int y, int width, int height, IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException{
		
		//check if in bounds
		if(x + width > Global.BOARDWIDTH || y + height > Global.BOARDHEIGHT){
			throw new InvalidGridPosException("Position: " + x + ":" + y + 
					" is invalid. Please ensure grid position is viable.");
		}
		
		//check if already filled
		for(int i = x; i < x + width; i++){
			for (int j = y; j < y + height; j++){
				if(grid[i][j] != null && grid[i][j] != g){
					throw new GridPosAlreadyTakenException("Postion: " + x + ":" + y + 
					" is already taken. Please ensure the grid position is empty.");
				}
			}
		}	
	}
}
