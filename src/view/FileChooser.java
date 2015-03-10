package view;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import controller.RestrictedFileSystemView;

/**
 * FileChooser
 * 
 * A wrapper on the JFileChooser to make getting files from the user
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
		
		File dir = new File((System.getProperty("user.dir"))+"\\res");
		FileSystemView fsv = new RestrictedFileSystemView(new File[] {dir});
		
		chooser = new JFileChooser(fsv);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text");
		chooser.setFileFilter(filter);
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
	public File saveFile() throws IOException{
		
		
		int returnval = chooser.showSaveDialog(chooser);
		
		if(returnval == JFileChooser.APPROVE_OPTION) {
			   File file = chooser.getSelectedFile();
			   
			   String path = file.getAbsolutePath();
			   
			   if( ! ( path.endsWith(".txt") || path.endsWith(".text") ) ){
				   path = path + ".txt";
			   }
			   
			   file = new File(path);
			   
			   if(file.exists()){
				   //TODO ask if we should overwrite
			   }else{
				   file.createNewFile();
			   }
			   
			   
			   return file;
		}
		
		return null;
	}

}
