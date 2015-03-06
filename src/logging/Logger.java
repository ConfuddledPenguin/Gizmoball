package logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;


/**
 * This class is to be accessed statically, it
 * is designed to set up the java.util.Logging utils.   
 * 
 * @author Tom Maxwell
 *
 */
public class Logger {
	
	/**
	 * The logger
	 */
	private static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Used to represent the physics level
	 */
	public static Level PHYSICS = new Level("PHYSICS", 10 ){

		/**
		 * 
		 */
		private static final long serialVersionUID = -1572706036321940041L;
		
	};
	
	/**
	 * Sets up the log to be used with the given
	 * filter. The filter is used to control what is 
	 * written to the .log file
	 * 
	 * @param f The filter to use
	 */
	static public void setUp(java.util.logging.Logger l) {
		
		LOGGER = l;
		
		LOGGER.setUseParentHandlers(false);
		File dir = new File("logs/");
		if(!dir.exists()){
			dir.mkdirs();
		}
		FileHandler fh = null;
		try {
			//log to l.getName, with 5MB max file size, use one file appending to the end
			fh = new FileHandler("logs/" + l.getName() + ".log", 1024 * 1024 * 5, 1, true);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		fh.setFilter(f);
		SimpleFormatter sf = new SimpleFormatter();
		fh.setFormatter(sf);
		LOGGER.addHandler(fh);
		LOGGER.setLevel(Level.ALL);
	}
}
