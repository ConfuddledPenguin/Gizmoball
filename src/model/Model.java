package model;

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
	private Ball ball;
	
	/**
	 * The constructor
	 * 
	 * @param boardHeight The height of the board
	 * @param boardWidth The width of the board;
	 */
	public Model(int boardHeight, int boardWidth) {
		
		new Global(boardHeight, boardWidth);
		board = new Board();
		ball = new Ball(19,19,0,0);
	}
	
	/**
	 * Loads a board from the given file
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
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Save the board to the given file
	 * 
	 * @param file The file to write to
	 * @throws IOException Error writing to file
	 */
	public void saveBoard(File file) throws IOException {
		//TODO write to disk
		System.out.println("--MODEL---: Asked to write to file " + file.getPath());
	}
	
	/**
	 * Add a gizmo to the board
	 * 
	 * @param g the gizmo to add
	 * 
	 */
	public void addGizmo(IGizmo g){
			
		try {
			board.addGizmo(g);
			setChanged();
			notifyObservers(g);
		} catch (GridPosAlreadyTakenException | InvalidGridPosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/**
	 * Returns the Board
	 * 
	 * @return the board
	 */
	public IBoard getBoard() {
		return board;
	}
	
	/**
	 * Returns the ball
	 * 
	 * @return the ball
	 */
	public IBall getBall() {
		return ball;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.setGravity(double gravity)
	 */
	@Override
	public void setGravity(double gravity) {
		Global.GRAVITY = gravity;		
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getGravity()
	 */
	@Override
	public double getGravity() {

		return Global.GRAVITY;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.setFriction(float my, float, mu2)
	 */
	@Override
	public void setFriction(float mu, float mu2) {
		
		Global.FRICTIONMU = mu;
		Global.FRICTIONMU2 = mu2;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getFrictionMU()
	 */
	@Override
	public double getFrictionMU() {
		
		return Global.FRICTIONMU;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getFrictionMU2()
	 */
	@Override
	public double getFrictionMU2() {

		return Global.FRICTIONMU2;
	}
}
