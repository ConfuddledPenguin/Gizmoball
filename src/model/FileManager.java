package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import physics.Vect;

/**
 * Responsible for loading and saving the files
 * 
 * This object is responsible for loading from the file and 
 * creating new boards, as well as saving existing boards.
 */
class FileManager {	
	
	private char space = ' ';
	private char newLine = '\n';
	private String GIZMO_BASE = "GIZMO";
	private int noGizmos = 0;
	private String BALL_BASE = "BALL";
	private int noBalls = 0;
	
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
		Map<String, IBall> balls = new HashMap<String, IBall>();
		Board board = (Board) m.getBoard();
		m.clear();
		
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
			IBall ball;
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
					}else if ( balls.containsKey(name)){
						ball = balls.get(name);
						xstring = st.nextToken();
						float xf = Float.parseFloat(xstring);
						ystring = st.nextToken();
						float yf = Float.parseFloat(ystring);
						ball.setX(xf);
						ball.setY(yf);
					}
					continue;
				case "Connect":
					name = st.nextToken();
					String name2 = st.nextToken();
					g = gizmos.get(name);
					g.connection(gizmos.get(name2));
					continue;
				case "KeyConnect":
					if(!st.nextToken().equals("key")){
						throw new IncorrectFileFormatException("'key' expected on line " + reader.getLineNumber());
					}
					int keynumber = Integer.parseInt(st.nextToken());
					String state = st.nextToken();
					boolean onDown = true;
					if(state.equals("up")){
						onDown = false;
					}
					name = st.nextToken();
					g = gizmos.get(name);
					m.registerKeyStroke(keynumber, g);
					continue;
				case "Gravity":
					token = st.nextToken();
					float value = Float.parseFloat(token);
					m.setGravity(value);
					continue;
				case "Friction":
					token = st.nextToken();
					float mu = Float.parseFloat(token);
					token = st.nextToken();
					float mu2 = Float.parseFloat(token);
					m.setFriction(mu, mu2);
					continue;
				case "Ball":
					name = st.nextToken();
					xstring = st.nextToken();
					float xf = Float.parseFloat(xstring);
					ystring = st.nextToken();
					float yf = Float.parseFloat(ystring);
					xstring = st.nextToken();
					float vx = Float.parseFloat(xstring);
					ystring = st.nextToken();
					float vy = Float.parseFloat(ystring);
					ball = m.addBall(xf, yf, vx, vy);
					balls.put(name, ball);
					continue;
			}
			
			Gizmo.Type type = null;
			try{
				type = Gizmo.Type.valueOf(token);
			}catch(Exception e){
				reader.close();
				throw new IncorrectFileFormatException("File Error: Unknown Command on line " + reader.getLineNumber());
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
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
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
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
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
					g = new Absorber(x, y, x2-x, y2-y);
					gizmos.put(name, g);
					try {
						board.addGizmo(g);
					} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
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
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
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
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
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
						reader.close();
						throw new IncorrectFileFormatException("File Error: on line " + reader.getLineNumber() + " ", e);
					}
					continue;
			}
		}
		
		reader.close();
		
		return board;
	}//Close loadfile()
	
	/**
	 * Save out the given model to the given file
	 * 
	 * @param m The model to save
	 * @param file The file to save to
	 * @throws IOException Something went wrong
	 */
	public void saveFile(Model m, File file) throws IOException {
		
		
		List<IGizmo> gizmos = m.getGizmos();
		
		Map<IGizmo, String> nameMapping = new HashMap<IGizmo, String>();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		//Gizmos
		for(IGizmo g: gizmos){
			
			String name = GIZMO_BASE + noGizmos++;
			
			nameMapping.put(g, name);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(g.getType());
			sb.append(space);
			sb.append(name);
			sb.append(space);
			sb.append(g.getXPos());
			sb.append(space);
			sb.append(g.getYPos());
			
			if(g.getType() == Gizmo.Type.Absorber){
				sb.append(space);
				sb.append(g.getXPos() + g.getWidth());
				sb.append(space);
				sb.append(g.getYPos() + g.getHeight());
			}
			
			sb.append(newLine);
			
			bw.write(sb.toString());
			
			Gizmo.Orientation orientation = g.getOrientation();
			
			switch (orientation) {
			
				case BottomRight:
					
					rotateSaveHelper(bw, name);
					
				case BottomLeft:
					
					rotateSaveHelper(bw, name);
			
				case TopRight:
					
					rotateSaveHelper(bw, name);
					
					break;
			}
		}
		
		for(IGizmo g: gizmos){
			
			Set<IGizmo> cons = g.getConnections();
			
			for(IGizmo g2: cons){
				String producer = nameMapping.get(g2);
				String consumer = nameMapping.get(g);
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("Connect");
				sb.append(space);
				sb.append(producer);
				sb.append(space);
				sb.append(consumer);
				sb.append(newLine);
				
				bw.write(sb.toString());
			}
		}
		
		for(Map.Entry<Integer, Set<IGizmo>> mapping: m.getKeyStrokes().entrySet()){
			
			for(IGizmo g : mapping.getValue()){
			
				StringBuilder sb = new StringBuilder();
				
				sb.append("KeyConnect key");
				sb.append(space);
				
				sb.append(mapping.getKey());
				
				sb.append(space);
				
				sb.append("ondown");
				sb.append(space);
				
				String name = nameMapping.get(g);
				
				sb.append(name);
				sb.append(newLine);
				
				bw.write(sb.toString());
			}			
		}
		
		for(IBall ball : m.getBalls()){
			
			String name = BALL_BASE + noBalls++;
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Ball");
			sb.append(space);
			
			sb.append(name);
			sb.append(space);
			
			sb.append(ball.getX());
			sb.append(space);
			sb.append(ball.getY());
			sb.append(space);
			
			Vect v = ball.getVelo();
			
			sb.append(v.x());
			sb.append(space);
			sb.append(v.y());	
			
			sb.append(newLine);
			bw.write(sb.toString());
		}
		
		//Settings
		StringBuilder sb = new StringBuilder();
		
		sb.append(newLine);
		sb.append("Gravity");
		sb.append(space);
		sb.append(m.getGravity());
		sb.append(newLine);
		sb.append("Friction");
		sb.append(space);
		sb.append(m.getFrictionMU());
		sb.append(space);
		sb.append(m.getFrictionMU2());
		
		bw.write(sb.toString());
		
		bw.close();
	}
	
	private void rotateSaveHelper(BufferedWriter bw, String name) throws IOException {
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("Rotate");
		sb.append(space);
		sb.append(name);
		sb.append(newLine);
		
		bw.write(sb.toString());

	}
}
