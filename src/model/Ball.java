package model;

import physics.Circle;
import physics.Vect;

/**
 * 
 * This represents any balls in the model
 */
public class Ball implements IBall {

	private Vect velocity;
	private double radius;
	private double xpos;
	private double ypos;

	/**
	 * Tha ball constructor
	 * 
	 * @param x The x coord
	 * @param y The y coord
	 * @param xv The x velocity
	 * @param yv The y velocity
	 */
	public Ball(double x, double y, double xv, double yv) {
		radius = 0.5 * Global.L;
		xpos = x; // Centre coordinates
		ypos = y;
		velocity = new Vect(xv, yv);
	}

	/* (non-Javadoc)
	 * @see model.IBall#getVelo()
	 */
	@Override
	public Vect getVelo() {
		return velocity;
	}

	/* (non-Javadoc)
	 * @see model.IBall#setVelo(model.physics.Vect)
	 */
	@Override
	public void setVelo(Vect v) {
		velocity = v;
	}

	/* (non-Javadoc)
	 * @see model.IBall#getRadius()
	 */
	@Override
	public double getRadius() {
		return radius;
	}

	/* (non-Javadoc)
	 * @see model.IBall#getCircle()
	 */
	@Override
	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	/* (non-Javadoc)
	 * @see model.IBall#getExactX()
	 */
	public double getExactX() {
		return xpos * Global.L + radius;
	}

	/* (non-Javadoc)
	 * @see model.IBall#getExactY()
	 */
	public double getExactY() {
		return ypos * Global.L + radius;
	}

	/* Set x using L as the measurement */
	@Override
	public void setX(double x) {
		xpos = x;
	}

	/* Set y using L as the measurement */
	@Override
	public void setY(double newY) {
		ypos = newY;
	}
	
	/* Set x using L as the measurement */
	public double getX() {
		return xpos;
	}

	/* Set y using L as the measurement */
	public double getY() {
		return ypos;
	}

}
