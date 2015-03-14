package model.exceptions;

/**
 * Represents the case where a grid position is already taken
 * 
 * @author Tom Maxwell
 *
 */
public class GridPosAlreadyTakenException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8016348933313459294L;

	/**
	 * The constructor
	 * 
	 * @param msg The message
	 */
	public GridPosAlreadyTakenException(String msg) {
		super(msg);
	}
	
}
