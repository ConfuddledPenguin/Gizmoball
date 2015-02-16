package model.fileman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
 */
public class FileManager {	
	
	public static void main(String[] args) {
		
		FileManager fm = new FileManager();
		
		File file = new File("res/test2.txt");
		
		try {
			fm.load(file);
		} catch (IOException e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
		
	}

	public void load(File file) throws FileNotFoundException, IOException {
	
		Map<String, IGizmo> gizmos = new HashMap<String, IGizmo>();
		
		LineNumberReader reader = new LineNumberReader(new FileReader(file));
		
		String line = null;
		StringTokenizer st;
		while ( (line = reader.readLine()) != null){
			
			st = new StringTokenizer(line);
			
			if( !st.hasMoreTokens()){
				continue;
			}
			String token = st.nextToken();
			
			switch (token) {
				case "Rotate":
					System.out.println("Rotate");
					continue;
				case "Delete":
					System.out.println("Delete");
					continue;
				case "Move":
					System.out.println("Move");
					continue;
				case "Connect":
					System.out.println("Connect");
					continue;
				case "KeyConnect":
					System.out.println("Key Connect");
					continue;
				case "Gravity":
					token = st.nextToken();
					float value = Float.parseFloat(token);
					System.out.println("Gravity " + value);
					continue;
				case "Friction":
					token = st.nextToken();
					float mu = Float.parseFloat(token);
					token = st.nextToken();
					float mu2 = Float.parseFloat(token);
					System.out.println("Friction " + mu + " " + mu2);
					continue;
				case "Ball":
					System.out.println("Ball");
					continue;
					
			}
			
			Gizmo.Type type;
			try{
				type = Gizmo.Type.valueOf(token);
			}catch(Exception e){
				System.out.println("Shit");
				return;
			}
			
			
			String name;
			String xstring;
			String ystring;
			int x;
			int y;
			IGizmo g;
			
			switch (type) {
				case Circle:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Circle(x, y);
					gizmos.put(name, g);
					continue;
					
				case Square:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Square(x, y);
					gizmos.put(name, g);
					continue;
					
				case Absorber:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					String x2string = st.nextToken();
					int x2 = Integer.parseInt(xstring);
					String y2string = st.nextToken();
					int y2 = Integer.parseInt(ystring);
					g = new Absorber(x, y, x2, y2);
					gizmos.put(name, g);
					continue;
					
				case Triangle:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new Triangle(x, y);
					gizmos.put(name, g);
					continue;
					
				case RightFlipper:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new RightFlipper(x, y);
					gizmos.put(name, g);
					continue;
					
				case LeftFlipper:
					
					name = st.nextToken();
					xstring = st.nextToken();
					x = Integer.parseInt(xstring);
					ystring = st.nextToken();
					y = Integer.parseInt(ystring);
					g = new LeftFlipper(x, y);
					gizmos.put(name, g);
					continue;
			}
		}
		
		reader.close();
	}	
}
