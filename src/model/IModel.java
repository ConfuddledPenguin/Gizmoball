package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Observer;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

/**
 * An Interface for the Model
 */
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
	 * @throws GridPosAlreadyTakenException Grid pos already taken
	 * @throws InvalidGridPosException Invalid Grid pos
	 * 
	 */
	public abstract void addGizmo(IGizmo g) throws InvalidGridPosException, GridPosAlreadyTakenException;

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
	 * @param gizmoPoint The pint the gizmo is at
	 * @param newPoint The new location for the gizmo
	 * 
	 * @throws GridPosAlreadyTakenException Grid pos already taken
	 * @throws InvalidGridPosException Invalid Grid pos
	 */
	public abstract void moveGizmo(Point gizmoPoint, Point newPoint) throws InvalidGridPosException, GridPosAlreadyTakenException;

	/**
	 * Connect the g2 to g1 
	 * 
	 * @param g1 The producer
	 * @param g2 The consumer
	 */
	public void connectGizmos(IGizmo g1, IGizmo g2);
	
	/**
	 * Disconnect g2 from g1
	 * 
	 * @param g1 The producer
	 * @param g2 The consumer
	 */
	public void disconnectGizmos(IGizmo g1, IGizmo g2);
	
	/**
	 * Returns the ball
	 * 
	 * @return the ball
	 */
	public abstract List<IBall> getBalls();


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
	 * Unregister a previously registered key-gizmo connection
	 * 
	 * @param key The key number
	 * @param gizmo The gizmo registered 
	 */
	public abstract void unRegisterKeyStroke(int key, IGizmo gizmo);
	
	/**
	 * Inform the model that a key has been pressed
	 * 
	 * @param key The key number that has been pressed
	 * @param onDown If on key pressed down pass true else false
	 */
	public abstract void triggerKeyPress(int key, boolean onDown);
	
	/**
	 * Updates the model
	 */
	public abstract void update();

	/**
	 * Adds an observer to the set of observers for this object
	 * , provided that it is not the same as some observer 
	 * already in the set.
	 * 
	 * @param o The object to add
	 */
	public abstract void addObserver(Observer o);
	
	/**
	 * Adds a ball to the model with the given values
	 * 
	 * @param x The x coord
	 * @param y The y coord
	 * @param xv The x velocity
	 * @param yv The y velocity
	 * 
	 * @return The added ball
	 * @throws InvalidGridPosException Grid pos invalid
	 */
	public IBall addBall(double x, double y, double xv, double yv) throws InvalidGridPosException;
	
	/**
	 * Get all gizmos
	 * 
	 * @return this.gizmos
	 */
	public List<IGizmo> getGizmos();
	
	/**
	 * Removes a ball from the board
	 * 
	 * @param p The point the ball is at
	 */
	public abstract void deleteBall(Point p);
	
	/**
	 * Clears the board
	 */
	public void clear();
	
	/**
	 * resets the board
	 */
	public void reset();

}