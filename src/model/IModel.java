package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observer;

import model.exceptions.IncorrectFileFormatException;
import model.gizmos.IGizmo;

/**
 * A public interface for the Model
 *
 */
public interface IModel {

	/**
	 * Loads a board from the file
	 * 
	 * @param file The file to load
	 * 
	 * @throws FileNotFoundException File not found
	 * @throws IOException Error Reading file
	 * @throws IncorrectFileFormatException File in wrong format
	 */
	public abstract void loadBoard(File file) throws FileNotFoundException,
			IOException, IncorrectFileFormatException;

	/**
	 * Save the board to the given file
	 * 
	 * @param file The file to write to
	 * @throws IOException Error writing to file
	 */
	public void saveBoard(File file) throws IOException;
	
	/**
	 * Get the Board
	 *  
	 * @return The board
	 */
	public abstract IBoard getBoard();
	
	/**
	 * Set the value of gravity on the model
	 * 
	 * @param gravity the value for gravity
	 */
	public void setGravity(double gravity);
	
	/**
	 * Get the value of gravity on the model
	 * 
	 * @return this.gravity
	 */
	public double getGravity();
	
	
	/**
	 * Sets the value of friction in the model
	 *  
	 * @param mu The mu value
	 * @param mu2 The mu2 values
	 */
	public void setFriction(float mu, float mu2);
	
	/**
	 * Get the value of friction mu on the model
	 * 
	 * @return this.frictionMU
	 */
	public double getFrictionMU();
	
	/**
	 * Get the value of friction mu2 on the model
	 * 
	 * @return this.frictionMU2
	 */
	public double getFrictionMU2();

	/**
	 * Adds a gizmo
	 * 
	 * @param g The gizmo to add
	 * 
	 * @deprecated Add gizmos through the board now.
	 */
	public abstract void addGizmo(IGizmo g);
	
	/**
	 * Updates the balls position after 1 frame of movement
	 */
	public void moveBall();

	/**
	 * Add yourself as a 
	 * @param buildBoard
	 */
	public abstract void addObserver(Observer o);

}