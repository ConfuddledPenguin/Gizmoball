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

	private Vect startVelocity;
	private double startxpos;
	private double startypos;

	private boolean stopped = false;

	/**
	 * The ball constructor
	 * 
	 * @param x
	 *            The x coord
	 * @param y
	 *            The y coord
	 * @param xv
	 *            The x velocity
	 * @param yv
	 *            The y velocity
	 */
	public Ball(double x, double y, double xv, double yv) {

		radius = 0.25;

		xpos = x; // Centre coordinates
		ypos = y;
		velocity = new Vect(xv, yv);

		startVelocity = velocity;
		startxpos = x;
		startypos = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#getVelo()
	 */
	@Override
	public Vect getVelo() {
		return velocity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#setVelo(model.physics.Vect)
	 */
	@Override
	public void setVelo(Vect v) {
		velocity = v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#getRadius()
	 */
	@Override
	public double getRadius() {
		return radius;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#getCircle()
	 */
	@Override
	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#getExactX()
	 */
	public double getExactX() {
		return xpos * Global.L + radius;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * @see model.IBall#stop()
	 */
	public void stop() {
		stopped = true;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IBall#start()
	 */
	public void start() {
		stopped = false;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IBall#isStopped()
	 */
	public boolean isStopped() {
		return stopped;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IBall#reset()
	 */
	public void reset() {

		xpos = startxpos;
		ypos = startypos;
		velocity = startVelocity;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Ball) {
			Ball ball = (Ball) obj;
			result = this.xpos == ball.xpos && this.ypos == ball.ypos
					&& this.velocity.equals(ball.velocity);
		}
		return result;
	}

}
