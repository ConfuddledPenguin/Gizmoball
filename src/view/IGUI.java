package view;

import java.awt.Point;
import java.awt.event.ActionListener;

import physics.Vect;

public interface IGUI {

	/**
	 * Either starts or stops the game, according to the "Start" or "Stop" string passed
	 * as a parameter.
	 * @param toWhat
	 */
	public abstract void changeStartStop(String toWhat);

	/**
	 * returns the Balls velocity
	 * @return Vect containing Ball Velocity
	 */
	public abstract Vect getBallVelocity();

	/**
	 * Prompt the user to enter numeric values for friction (mu and mu2)
	 * @return array containing values for mu and mu2. Null on cancel
	 */
	public abstract float[] getUserFriction();

	/**
	 * Shows a message informing the user of how to 
	 * connect gizmos together.
	 */
	public abstract void showConnectMessage();

	/**
	 * Alert user to successful connection
	 */
	public abstract void showGizmoConnectedMessage();

	/**
	 * Prompt the user to enter a numeric value for gravity
	 * @return users value for gravity. Null on cancel
	 */
	public abstract Double getUserGravity();

	/**
	 * Displays an error message with the specified string msg
	 * @param msg 
	 */
	public abstract void displayErrorMessage(String msg);

	/**
	 * Switches the mode between Build and Run Mode
	 * @return String specifying which mode has been activated
	 */
	public abstract String switchMode();

	/**
	 * Returns the clicked cell
	 * @return Point containing X,Y of clicked cell
	 */
	public abstract Point getClickedCell();

	/**
	 * Returns the move target for Move operations
	 * @return Point containing X,Y of move target
	 */
	public abstract Point getMovedPoint();

	/**
	 * Sets the absorber start point to the specified Point p
	 * @param p
	 */
	public abstract void setAbsorberStart(Point p);

	/**
	 * 
	 * @param listener
	 */
	public abstract void setGizmoConnecting(ActionListener listener);

	/**
	 * Sets the key connecting action listener. This is used when
	 * connecting a key to a gizmo.
	 * @param listener
	 */
	public abstract void setKeyConnecting(ActionListener listener);

	public abstract IBuildBoard getBuildBoard();

	/**
	 * Display instructions to the user on how connect a key to the selected gizmo
	 */
	public abstract void showKeyConnectMessage();

	/**
	 * Confirm to the user that a key has successfully been connected to a gizmo
	 * 
	 * @param msg the message to show
	 * 
	 */
	public abstract void showKeyConnectedMessage(String msg);
	
	/**
	 * Get the runboard
	 * 
	 * @return this.runboard
	 */
	public IRunBoard getRunBoard();

}