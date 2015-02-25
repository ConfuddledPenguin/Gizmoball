package model;
import physics.Vect;

/**
 * Stores the time until there is a collision and the post collision velocity
 */
public class CollisionDetails {
	private Vect velocity;
	private double timeUntilCollision;
	
	public CollisionDetails(double time, Vect velocity) {
		this.velocity = velocity;
		timeUntilCollision = time;
	}
	
	public double getTimeUntilCollision() {
		return timeUntilCollision;
	}
	
	public Vect getVelocity() {
		return velocity;
	}

}
