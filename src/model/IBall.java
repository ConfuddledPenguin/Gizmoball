package model;

import java.awt.Color;

import physics.*;

public interface IBall {

	public abstract Vect getVelo();

	public abstract void setVelo(Vect v);

	public abstract double getRadius();

	public abstract Circle getCircle();

	// Ball specific methods that deal with double precision.
	public abstract double getExactX();

	public abstract double getExactY();

	public abstract void setExactX(double x);

	public abstract void setExactY(double y);
	
	// set X using L as the measurement
	public abstract void setX(int x);
	
	// set Y using L as the measurement
	public abstract void setY(int y);

	public abstract void stop();

	public abstract void start();

	public abstract boolean stopped();

	public abstract Color getColour();

}