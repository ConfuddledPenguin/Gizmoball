package model;

import model.gizmos.IGizmo;
import physics.Vect;

/**
 * Stores the time until there is a collision and the post collision velocity
 */
public class CollisionDetails {
	
	private Vect velocity;
	private Vect velocity2;
	private IBall secondBall;
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
	 * The constructor for the {@link CollisionDetails} object
	 * 
	 * @param time The time until the next collision
	 * @param velocity The velocity of ball 1
	 * @param velocity2 The velocity of ball two
	 * @param secondBall The ball collided with
	 */
	public CollisionDetails(double time, Vect velocity, Vect velocity2, IBall secondBall) {
		this.velocity = velocity;
		timeUntilCollision = time;
		this.velocity2 = velocity2;
		this.secondBall = secondBall;
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
	 * Get the velocity
	 * 
	 * @return this.velocity
	 */
	public Vect getVelocity2() {
		return velocity2;
	}

	/**
	 * Get the gizmo collided with
	 * 
	 * @return this.gizmo
	 */
	public IGizmo getGizmo() {
		return gizmo;
	}

	/**
	 * Get the ball collided with
	 * 
	 * @return this.ball
	 */
	public IBall getSecondBall(){
		return secondBall;
	}
}
