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
	
	public CollisionDetails(double time, Vect velocity) {
		this.velocity = velocity;
		timeUntilCollision = time;
		gizmo = null;
	}
	
	public CollisionDetails(double time, Vect velocity, IGizmo gizmo) {
		this.velocity = velocity;
		timeUntilCollision = time;
		this.gizmo = gizmo;
	}
	
	public double getTimeUntilCollision() {
		return timeUntilCollision;
	}
	
	public Vect getVelocity() {
		return velocity;
	}

	public IGizmo getGizmo() {
		return gizmo;
	}

}
