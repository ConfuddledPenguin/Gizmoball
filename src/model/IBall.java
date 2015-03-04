package model;

import physics.Circle;
import physics.Vect;

/**
 * An interface for the ball
 *
 */
public interface IBall {

	/**
	 * Returns the velocity of the ball 
	 * 
	 * @return this.velocity
	 */
	public abstract Vect getVelo();

	/**
	 * Sets the velocity of the ball
	 * 
	 * @param v The velocity
	 */
	public abstract void setVelo(Vect v);

	/**
	 * Gets the radius of the ball
	 * 
	 * @return The radius
	 */
	public abstract double getRadius();

	/**
	 * Get a MIT physics circle representation of
	 * the ball
	 * 
	 * @return The representation of the ball
	 */
	public abstract Circle getCircle();
	
	/**
	 * Get the X coord
	 * 
	 * @return The x coord
	 */
	public abstract double getX();

	/**
	 * Get the Y coord
	 * 
	 * @return The y coord
	 */
	public abstract double getY();
	
	/**
	 * Set the x coord
	 * 
	 * @param x the coord
	 */
	public abstract void setX(double x);
	
	/**
	 * Set the Y coords
	 * 
	 * @param y The y coord
	 */
	public abstract void setY(double y);
}