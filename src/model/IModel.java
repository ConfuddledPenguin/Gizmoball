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
	 * Adds a gizmo
	 * 
	 * @param g The gizmo to add
	 * 
	 * @deprecated Add gizmos through the board now.
	 */
	public abstract void addGizmo(IGizmo g);

}