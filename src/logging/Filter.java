package logging;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 
 * Used to select what levels to write to the 
 * log. 
 * 
 * @author Tom Maxwell
 *
 */
public class Filter implements java.util.logging.Filter{

	/**
	 * The levels to display
	 */
	Set<Level> levels = new HashSet<Level>();
	
	/**
	 * Log all and log off bools
	 */
	private boolean logall = false; 
	private boolean logoff = false; 
	
	/**
	 * Is the test to check whether something should
	 * written to the file or not. 
	 * 
	 * @param record The record to check against the levels
	 */
	@Override
	public boolean isLoggable(LogRecord record) {
		
		if(logoff){
			return false;
		}
		
		if(logall){
			return true;
		}
		
		for(Level l: levels){
			if(record.getLevel() == l){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Ad the given level to the filter
	 * 
	 * @param l The level to add
	 */
	public void addLevel(Level l){
		levels.add(l);
	}
	
	/**
	 * Remove the given level from the
	 * filter
	 * 
	 * @param l the level to remove
	 */
	public void removeLevel(Level l){
		levels.remove(l);
	}
	
	/**
	 * Log everything
	 */
	public void logAll(){
		logall = true;
	}
	
	/**
	 * Log nothing
	 */
	public void logoff(){
		logoff = true;
	}
	
}