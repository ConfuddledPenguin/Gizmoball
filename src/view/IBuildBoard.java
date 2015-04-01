package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JPopupMenu;

import model.IBall;
import model.gizmos.IGizmo;

public interface IBuildBoard {

	/**
	 * Builds the popup context menu for a gizmo
	 * 
	 * @param listener The listener to use to listen for events
	 * @param g The gizmo to build for
	 * @return The menu
	 */
	public abstract JPopupMenu createGizmoPopupMenu(ActionListener listener,
			IGizmo g);

	/**
	 * Create a popup context menu for an empty area
	 * 
	 * @param listener The listener to use for events
	 * 
	 * @return The menu
	 */
	public abstract JPopupMenu createEmptyPopupMenu(ActionListener listener);

	/**
	 * Creates a popup context menu for when there is a ball selected
	 * 
	 * @param listener The listener to use
	 * @param b The ball to build for
	 * @return the menu
	 */
	public abstract JPopupMenu createBallPopupMenu(ActionListener listener,
			IBall b);

	/**
	 * Returns the selected cell
	 * 
	 * @return this.selectedCell
	 */
	public abstract Point getSelectedCell();

	/**
	 * Returns the clicked cell
	 * 
	 * @return this.clickedCell
	 */
	public abstract Point getclickedCell();

	public abstract Point getMousePt();

	public abstract Dimension getPreferredSize();

	public abstract void invalidate();

	/**
	 * Sets the starting point for an absorber definition by the user.
	 * @param p the starting point in the grid
	 */
	public abstract void setAbsorberStart(Point p);

	/**
	 * Get the absorber starting point
	 * 
	 * @return The point
	 */
	public abstract Point getAbsorberStart();

	/**
	 * Let the ui know that we are connecting gizmos
	 */
	public abstract void connectingGizmos(ActionListener listener);

	/**
	 * Cancel connecting the gizmos
	 */
	public abstract void cancelGizmoConnect();

	/**
	 * Get the listener responsible for the gizmo connecting
	 * 
	 * @return The listener
	 */
	public abstract ActionListener getConnectingGizmos();

	public abstract void setConnectingGizmo(ActionListener listener);

	/**
	 * Set the action listener to be called when when a key is pressed after
	 * the user has chosen to connect a key to a gizmo. If null, key presses will
	 * be treated normally and not assigned to a gizmo.
	 * @param listener 
	 */
	public abstract void setConnectingKey(ActionListener listener);

	/**
	 * Return the action listener which is to be called when a key is pressed
	 * after the user has chosen to connect a key to a gizmo.
	 */
	public abstract ActionListener getConnectingKey();

	/**
	 * Cancels connecting a key to a gizmo. Key presses will be treated as normal
	 * and not assigned to a gizmo.
	 */
	public abstract void cancelKeyConnect();

	public abstract Point getMoveTarget();

	public abstract void setMoveTarget(Point p);

	/**
	 * Set the selected cell
	 * @param p the cell selected
	 */
	public abstract void setSelectedCell(Point p);

	/**
	 * Set the clicked cell
	 * 
	 * @param clickedCell the clicked cell
	 */
	public abstract void setClickedCell(Point clickedCell);

	/**
	 * Moving a gizmo? let me know
	 * 
	 * @param value tru for moving, otherwise false
	 */
	public abstract void setGizmoMoving(boolean value);

	/**
	 * Get whether a gizmo is being moved;
	 * 
	 * @return this.moving
	 */
	public abstract boolean isGizmoMoving();

	public abstract void update(Observable o, Object arg);
	
	public int getHeight();
	
	public int getWidth();
	
	public void repaint();

}