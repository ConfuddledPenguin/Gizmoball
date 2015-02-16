package model.gizmos;

/**
 * The gizmo interface
 * 
 * The gizmo interface provides public
 * access to useful methods for the gizmos 
 */
public interface IGizmo {
	
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
	
}
