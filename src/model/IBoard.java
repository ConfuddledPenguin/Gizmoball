package model;

import java.util.Set;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

/**
 * 
 * An interface for external access to the models
 * board.
 */
public interface IBoard {

	/**
	 * Adds the gizmo to the board.
	 * 
	 * @param g The gizmo to add
	 * @throws InvalidGridPosException Invalid grid position
	 * @throws GridPosAlreadyTakenException Gird position already occupied
	 */
	public abstract void addGizmo(IGizmo g) throws InvalidGridPosException,
			GridPosAlreadyTakenException;//End addGizmo();

	/**
	 * Remove the given gizmo from the board
	 * 
	 * @param g The gizmo to remove;
	 */
	public abstract void removeGizmo(IGizmo g);

	/**
	 * Returns all the gizmos currently on the board
	 * 
	 * @return A unmodifiable set of gizmos
	 */
	public abstract Set<IGizmo> getGizmos();
	
	/**
	 * Returns the Gizmo at the location specified by the
	 * x and y arguments
	 * 
	 * @param x The x cord
	 * @param y The y cord
	 * 
	 * @return The gizmo located in the cords
	 */
	public IGizmo getGizmo(int x, int y);

}