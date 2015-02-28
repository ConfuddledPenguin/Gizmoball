package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

public interface IModel {

	/**
	 * Loads a board from the given file
	 * 
	 * @param file The file to load from
	 * 
	 * @throws FileNotFoundException File not found
	 * @throws IOException Error reading file 
	 * @throws IncorrectFileFormatException File in the wrong format
	 */
	public abstract void loadBoard(File file) throws FileNotFoundException,
			IOException, IncorrectFileFormatException;

	/**
	 * Save the board to the given file
	 * 
	 * @param file The file to write to
	 * @throws IOException Error writing to file
	 */
	public abstract void saveBoard(File file) throws IOException;

	/**
	 * Add a gizmo to the board
	 * 
	 * @param g the gizmo to add
	 * 
	 */
	public abstract void addGizmo(IGizmo g);

	/**
	 * Removes a gizmo from the board
	 * 
	 * @param p The point the gizmo is at
	 */
	public abstract void deleteGizmo(Point p);

	/**
	 * Get the gizmo at the specified point
	 * 
	 * @param p the point to get
	 * @return The gizmo at that location
	 */
	public abstract IGizmo getGizmo(Point p);

	/**
	 * Rotate the gizmo by 90' clockwise
	 * 
	 * @param p The point the gizmo is at
	 */
	public abstract void RotateClockwise(Point p);

	/**
	 * Rotate the gizmo 90' antiClockwise
	 * 
	 * @param p The point the gizmo is at
	 */
	public abstract void RotateAntiClockwise(Point p);

	/**
	 * Move the gizmo at old point to the new point
	 * 
	 * @param oldPoint The pint the gizmo is at
	 * @param newPoint The new location for the gizmo
	 * 
	 * @throws GridPosAlreadyTakenException 
	 * @throws InvalidGridPosException 
	 */
	public abstract void moveGizmo(Point gizmoPoint, Point newPoint) throws InvalidGridPosException, GridPosAlreadyTakenException;

	/**
	 * Returns the ball
	 * 
	 * @return the ball
	 */
	public abstract IBall getBall();

	/**
	 * Adds a ball
	 */
	public abstract void addBall();

	/**
	 * Set the value of gravity on the model
	 * 
	 * @param gravity the value for gravity
	 */
	public abstract void setGravity(double gravity);

	/**
	 * Get the value of gravity on the model
	 * 
	 * @return this.gravity
	 */
	public abstract double getGravity();

	/**
	 * Sets the value of friction in the model
	 *  
	 * @param mu The mu value
	 * @param mu2 The mu2 values
	 */
	public abstract void setFriction(float mu, float mu2);

	/**
	 * Get the value of friction mu on the model
	 * 
	 * @return this.frictionMU
	 */
	public abstract double getFrictionMU();

	/**
	 * Get the value of friction mu2 on the model
	 * 
	 * @return this.frictionMU2
	 */
	public abstract double getFrictionMU2();

	/**
	 * Register a key to the model
	 * 
	 * @param key The key number -- here since the file represents them this way
	 * @param gizmo The gizmo to trigger
	 */
	public abstract void registerKeyStroke(int key, IGizmo gizmo);
	
	/**
	 * Updates the balls position after 1 frame of movement
	 */
	public abstract void moveBall();

}