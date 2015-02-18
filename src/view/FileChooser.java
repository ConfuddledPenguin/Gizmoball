package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FileChooser
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
public class FileChooser implements IFileChooser{
	
		JFileChooser chooser;
	
	public FileChooser() {
		chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		chooser.setFileFilter(filter);
		File dir = new File(System.getProperty("user.home"));
		chooser.setCurrentDirectory(dir);
	}
	
	/* (non-Javadoc)
	 * @see gui.FileChooserInterface#GetFile()
	 */
	@Override
	public File getFile(){
		
		int returnval = chooser.showOpenDialog(chooser);
		
		if(returnval == JFileChooser.APPROVE_OPTION) {
			   return chooser.getSelectedFile();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gui.FileChooserInterface#SaveFile()
	 */
	@Override
	public File saveFile(){
		
		
		int returnval = chooser.showSaveDialog(chooser);
		
		if(returnval == JFileChooser.APPROVE_OPTION) {
			   return chooser.getSelectedFile();
		}
		
		return null;
	}

}
