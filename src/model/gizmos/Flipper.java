package model.gizmos;


/**
 * An abstract class for the flippers
 */
public abstract class Flipper extends Gizmo {

	private int angularVelocity;
	
	public Flipper(int x, int y, int width, int height, Type type) {
		super(x, y, width, height, type);
	}	
	
	public double getAngularVelocity() {
		return Math.toRadians(angularVelocity/10);
	}
	
	public void setAngularVelocity(int angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
}
