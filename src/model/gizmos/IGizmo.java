package model.gizmos;

import model.gizmos.Gizmo.Orientation;

/**
 * The gizmo interface
 * 
 * The gizmo interface provides public
 * access to useful methods for the gizmos 
 */
public interface IGizmo {
	
	/**
	 * Update the state of the gizmo
	 * 
	 * And performs any action the gizmo
	 * is asked to perform
	 */
	public void update();
	
	/**
	 * Trigger this gizmos action
	 */
	void trigger();
	
	/**
	 * Connect the gizmo g to this gizmo
	 * for triggering when this gizmo is 
	 * triggered
	 * 
	 * @param g The gizmo to connect
	 */
	void connection(IGizmo g);
	
	/**
	 * Set the positions of the gizmo
	 * 
	 * @param x The x cord
	 * @param y The y cord
	 */
	void setPos(int x, int y);
	
	/**
	 * Set the width and height of the
	 * gizmo
	 * 
	 * @param w the width
	 * @param h the height
	 */
	void setWidthHeight(int w, int h);
	
	/**
	 * Get the position of the gizmo
	 *
	 * @return The position as a Point 
	 */
	int getXPos();
	
	/**
	 * Get the position of the gizmo
	 *
	 * @return The position as a Point 
	 */
	int getYPos();
	
	/**
	 * Get the width of the gizmo
	 * 
	 * @return The width
	 */
	int getWidth();
	
	/**
	 * Get height of the gizmo
	 * 
	 * @return The height
	 */
	int getHeight();
	
	/**
	 * Rotates the gizmo.
	 * 
	 */
	void rotateClockwise();

	/**
	 * Rotates the gizmo anticlockwise
	 */
	void rotateAntiClockwise();
	
	/**
	 * 
	 * Get the rotation of the gizmo
	 * 
	 * @return The rotation enum
	 */
	public Orientation getOrientation();
	
	/**
	 * Returns the type of the gizmo
	 * 
	 * @return The type
	 */
	public Gizmo.Type getType();
	
	/**
	 * Get the angle of the gizmo (flippers especially...)
	 * 
	 * @return Angle in Degrees
	 */
	public int getAngle();
	
}
