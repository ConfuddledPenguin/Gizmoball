package model.exceptions;

/**
 * File is in the incorrect format
 */
public class IncorrectFileFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198914037893632341L;
	public IncorrectFileFormatException(String msg) {
		super(msg);
	}
	
	public IncorrectFileFormatException(String msg, Exception e){
		super(msg + e.toString());
	}
}
