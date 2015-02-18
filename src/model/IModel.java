package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.exceptions.IncorrectFileFormatException;
import model.gizmos.IGizmo;

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
	 * Get the Board
	 *  
	 * @return
	 */
	public abstract IBoard getBoard();
	
	/**
	 * Set the value of gravity on the model
	 * 
	 * @param gravity the value for gravity
	 */
	public void setGravity(float gravity);
	
	
	/**
	 * Sets the value of friction in the model
	 *  
	 * @param mu The mu value
	 * @param mu2 The mu2 values
	 */
	public void setFriction(float mu, float mu2);

	/**
	 * Adds a gizmo
	 * 
	 * @param g The gizmo to add
	 * 
	 * @deprecated Add gizmos through the board now.
	 */
	public abstract void addGizmo(IGizmo g);

}