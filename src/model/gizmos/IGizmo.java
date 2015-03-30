package model.gizmos;

import java.util.List;
import java.util.Set;

import model.IBall;
import model.gizmos.Gizmo.Orientation;
import model.gizmos.Gizmo.TriggerType;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

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
	 * 
	 * @param type the type of event
	 */
	void trigger(Gizmo.TriggerType type);
	
	/**
	 * Connect the gizmo g to this gizmo
	 * for triggering when this gizmo is 
	 * triggered
	 * 
	 * @param g The gizmo to connect
	 */
	void connection(IGizmo g);
	
	/**
	 * Disconnect the given gizmo from this one
	 * 
	 * @param g The gizmo to disconnect
	 */
	public void Disconnect(IGizmo g);
	
	/**
	 * Set the positions of the gizmo
	 * 
	 * @param x The x cord
	 * @param y The y cord
	 */
	void setPos(int x, int y);
	
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
	
	/**
	 * Get the LineSegments that can be collided with
	 * 
	 * @return The linesegments
	 */
	public List<LineSegment> getEdges();
	
	/**
	 * Get the Circle objects that can be collided with
	 * 
	 * @return The circles
	 */
	public List<Circle> getCorners();
	
	/**
	 * Returns the triggered state of the gizmo
	 * 
	 * @return this.triggered
	 */
	public boolean isTriggered();
	
	/**
	 * Add the ball to the list of balls gizmo knows about
	 * 
	 * All balls are removed if an action uses them
	 * 
	 * @param ball the ball to add
	 */
	public void addBall(IBall ball);
	
	/**
	 * Sets the Collision Details of the gizmo
	 * 
	 * This should be over ridden by the individual
	 * gizmos
	 */
	public void setCollisionDetails();
	
	/**
	 * Gets the list of gizmos listening for this gizmo
	 * 
	 * @return The set of gizmos
	 */
	public Set<IGizmo>getConnections();
	
	/**
	 * Get the Coefficient of the gizmo
	 * 
	 * @return the Coefficient
	 */
	public double getCoefficient();
	
	/**
	 * Release all the balls stored by this gizmo
	 */
	public void releaseBalls();
	
	/**
	 * Sets the velocity at which balls exit the absorber
	 * 
	 * @param v the velocity
	 */
	public void setBallExitVelocity(Vect v);
	
	/**
	 * Gets the type of the trigger
	 * 
	 * @return The type of the trigger
	 */
	public TriggerType getTriggerType();
	
}
