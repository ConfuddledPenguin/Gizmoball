package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;
import model.gizmos.LeftFlipper;
import model.gizmos.RightFlipper;
import model.gizmos.Square;
import model.gizmos.Triangle;

/**
 * Responsible for loading and saving the files
 * 
 * This object is responsible for loading from the file and 
 * creating new boards, as well as saving existing boards.
 */
class FileManager {	

	/**
	 * Loads the given file and returns the playable board,
	 * while setting up the model for gameplay.
	 *  
	 * @param m The model we are loading into
	 * @param file The file to load
	 * @return The board
	 * @throws FileNotFoundException File not found
	 * @throws IOException Error reading file
	 * @throws IncorrectFileFormatException File in incorrect format
	 */
	Board load(Model m, File file) throws FileNotFoundException, IOException, IncorrectFileFormatException {
	
		Map<String, IGizmo> gizmos = new HashMap<String, IGizmo>();
		Board board = (Board) m.getBoard();
		
		LineNumberReader reader = new LineNumberReader(new FileReader(file));
		
		String line = null;
		StringTokenizer st;
		while ( (line = reader.readLine()) != null){
			
			st = new StringTokenizer(line);
			
			if( !st.hasMoreTokens()){
				continue;
			}
			String token = st.nextToken();
			
			String name;
			IGizmo g;
			String xstring;
			String ystring;
			int x;
			int y;
			switch (token) {
				case "Rotate":
					name = st.nextToken();
					g = gizmos.get(name);
					g.rotateClockwise();
					continue;
				case "Delete":
					name = st.nextToken();
					g = gizmos.remove(name);
					board.removeGizmo(g);
					continue;
				case "Move":
					name = st.nextToken();
					if(gizmos.containsKey(name)){
						g = gizmos.get(name);
						xstring = st.nextToken();
						x = Integer.parseInt(xstring);
						ystring = st.nextToken();
						y = Integer.parseInt(ystring);
						g.setPos(x, y);
					}else{
						xstring = st.nextToken();
						float xf = Float.parseFloat(xstring);
						ystring = st.nextToken();
						float yf = Float.parseFloat(ystring);
						//TODO move the ball
					}
					continue;
				case "Connect":
					name = st.nextToken();
					String name2 = st.nextToken();
					g = gizmos.get(name);
					g.connection(gizmos.get(name2));
					continue;
				case "KeyConnect":
					System.out.println("Key Connect");
					//TODO
					continue;
				case "Gravity":
					token = st.nextToken();
					float value = Float.parseFloat(token);
					//TODO set gravity
					continue;
				case "Friction":
					token = st.nextToken();
					float mu = Float.parseFloat(token);
					token = st.nextToken();
					float mu2 = Float.parseFloat(token);
					//TODO set friction
					continue;
				case "Ball":
					System.out.println("Ball");
					//TODO
					continue;
					
			}
			
			Gizmo.Type type = null;
			try{
				type = Gizmo.Type.valueOf(token);
			}catch(Exception e){
				throw new IncorrectFileFormatException("File Error: Unknown Command on line " + reader.getLineNumber());
			}finally{
				reader.close();
			}
			
			//using vars from the above switch			
			switch (type) {
				case Circle:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Circle(x, y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
					
				case Square:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Square(x, y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
					
				case Absorber:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					String x2string = st.nextToken();
					int x2 = Integer.parseInt(x2string);
					String y2string = st.nextToken();
					int y2 = Integer.parseInt(y2string);
					g = new Absorber(x, y, x2, y2);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
					
				case Triangle:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Triangle(x, y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
					
				case RightFlipper:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new RightFlipper(x, y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
					
				case LeftFlipper:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new LeftFlipper(x, y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						throw new IncorrectFileFormatException("File Error: one line" + reader.getLineNumber(), e);
					}finally{
						reader.close();
					}
					continue;
			}
		}
		
		reader.close();
		
		return board;
	}//Close loadfile()
}
