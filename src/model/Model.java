package model;

import interfaces.IModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

/**
 * 
 * The model
 * 
 * This class is designed to be a controller for the model
 *
 */
public class Model extends Observable implements IModel {
	
	private Board board;
	
	/**
	 * The constructor
	 * 
	 * @param boardHeight The height of the board
	 * @param boardWidth The width of the board;
	 */
	public Model(int boardHeight, int boardWidth) {
		
		new Global(boardHeight, boardWidth);
		board = new Board();		
	}
	
	/**
	 * Loads a board from the givin file
	 * 
	 * @param file The file to load from
	 * 
	 * @throws FileNotFoundException File not found
	 * @throws IOException Error reading file 
	 * @throws IncorrectFileFormatException File in the wrong format
	 */
	public void loadBoard(File file) throws FileNotFoundException, IOException, IncorrectFileFormatException{
		FileManager fm = new FileManager();
		board = fm.load(this, file);
	}
	
	/**
	 * Add a gizmo to the board
	 * 
	 * @param g the gizm to add
	 * 
	 * @deprecated use {@link model.IBoard#addGizmo(IGizmo)}
	 */
	public void addGizmo(IGizmo g){
			
		try {
			board.addGizmo(g);
		} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers(g);	
	}

	/**
	 * Returns the Board
	 * 
	 * @return the board
	 */
	public IBoard getBoard() {
		return board;
	}
}
