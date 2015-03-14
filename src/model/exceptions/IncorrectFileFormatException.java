package model.exceptions;

/**
 * File is in the incorrect format
 */
public class IncorrectFileFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198914037893632341L;
	
	/**
	 * The constructor
	 * 
	 * @param msg The message
	 */
	public IncorrectFileFormatException(String msg) {
		super(msg);
	}
	
	/**
	 * The other constructor. This one takes an exceptions as well.
	 * This is used for when an exceptions has cuased this exception
	 * to be thrown.
	 * 
	 * This wraps it up and prints out the reason nicely(ish)
	 * 
	 * @param msg The message to show
	 * @param e The causing exceptions
	 */
	public IncorrectFileFormatException(String msg, Exception e){
		super(msg + e.toString());
	}
}
