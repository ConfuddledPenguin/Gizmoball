package model;

import model.gizmos.IGizmo;
import physics.Vect;

/**
 * Stores the time until there is a collision and the post collision velocity
 */
public class CollisionDetails {
	
	private Vect velocity;
	private double timeUntilCollision;
	private IGizmo gizmo;

	/**
	 * The constructor for the CollisionsDetials object
	 * 
	 * @param time The time until the next collision
	 * @param velocity The velocity
	 * @param gizmo The gizmo collided with
	 */
	public CollisionDetails(double time, Vect velocity, IGizmo gizmo) {
		this.velocity = velocity;
		timeUntilCollision = time;
		this.gizmo = gizmo;
	}
	
	/**
	 * Get the time until the next collision
	 * 
	 * @return this.time
	 */
	public double getTimeUntilCollision() {
		return timeUntilCollision;
	}
	
	/**
	 * Get the velocity
	 * 
	 * @return this.velocity
	 */
	public Vect getVelocity() {
		return velocity;
	}

	/**
	 * Get the gizmo collided with
	 * 
	 * @return this.gizmo
	 */
	public IGizmo getGizmo() {
		return gizmo;
	}

}
