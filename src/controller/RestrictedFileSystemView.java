package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

public class RestrictedFileSystemView extends FileSystemView{
	
	    private final File[] rootDirectories;

	    RestrictedFileSystemView(File rootDirectory)
	    {
	        this.rootDirectories = new File[] {rootDirectory};
	    }

	    public RestrictedFileSystemView(File[] rootDirectories)
	    {
	        this.rootDirectories = rootDirectories;
	    }

		@Override
		public File createNewFolder(File containingDir) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

	    @Override
	    public File[] getRoots()
	    {
	        return rootDirectories;
	    }

	    @Override
	    public boolean isRoot(File file)
	    {
	        for (File root : rootDirectories) {
	            if (root.equals(file)) {
	                return true;
	            }
	        }
	        return false;
	    }

	
	}
