package view;

import java.io.File;
import java.io.IOException;

/**
 * An interface for the file chooser.
 * 
 * A wrapper on the JFileChooser to make geting files from the user
 * easy for the controller.
 * 
 * This is taken from the portfolio tracker exercise from the class CS308.
 * Code can be found here:
 * https://github.com/ConfuddledPenguin/Folio-Tracker
 * 
 * @author Tom Maxwell
 *
 */
public interface IFileChooser {

	/**
	 * Ask the user for a file to load
	 * 
 	 * @return The file the user wishes to load
	 */
	public abstract File getFile();

	/**
	 * Ask the user for a file to save to
	 * 
	 * @return The file the user wishes to save to
	 * 
	 * @throws IOException Everything has gone wrong 
	 */
	public abstract File saveFile() throws IOException;

}