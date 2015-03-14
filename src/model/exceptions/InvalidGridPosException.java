package model.exceptions;

/**
 * Invalid grid pos exception
 * 
 * @author Tom Maxwell
 *
 */
public class InvalidGridPosException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5173134310656961042L;

	public InvalidGridPosException(String ms) {
		super(ms);
	}

}
