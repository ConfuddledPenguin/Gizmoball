package model;

import java.awt.Color;

import physics.*;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball implements IBall {

	private Vect velocity;
	private double radius;
	private double xpos;
	private double ypos;
	private Color colour;

	private boolean stopped;

	// x, y coordinates(L) and x,y velocity
	public Ball(double x, double y, double xv, double yv) {
		radius = 10;
		xpos = (x * Global.L) + radius; // Centre coordinates
		ypos = (y * Global.L) + radius;
		colour = Color.GREEN;
		velocity = new Vect(xv, yv);
		stopped = false;
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
	@Override
	public double getExactX() {
		return xpos;
	}

	/* (non-Javadoc)
	 * @see model.IBall#getExactY()
	 */
	@Override
	public double getExactY() {
		return ypos;
	}

	/* (non-Javadoc)
	 * @see model.IBall#setExactX(double)
	 */
	@Override
	public void setExactX(double x) {
		xpos = x;
	}

	/* (non-Javadoc)
	 * @see model.IBall#setExactY(double)
	 */
	@Override
	public void setExactY(double y) {
		ypos = y;
	}

	/* (non-Javadoc)
	 * @see model.IBall#stop()
	 */
	@Override
	public void stop() {
		stopped = true;
	}

	/* (non-Javadoc)
	 * @see model.IBall#start()
	 */
	@Override
	public void start() {
		stopped = false;
	}

	/* (non-Javadoc)
	 * @see model.IBall#stopped()
	 */
	@Override
	public boolean stopped() {
		return stopped;
	}

	/* (non-Javadoc)
	 * @see model.IBall#getColour()
	 */
	@Override
	public Color getColour() {
		return colour;
	}

	/* Set x using L as the measurement */
	@Override
	public void setX(int x) {
		xpos = x * Global.L;
	}

	/* Set y using L as the measurement */
	@Override
	public void setY(int y) {
		ypos = y * Global.L;
	}

}
