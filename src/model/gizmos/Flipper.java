package model.gizmos;

import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * An abstract class for the flippers
 */
public abstract class Flipper extends Gizmo {

	protected ArrayList<LineSegment> edges;
	protected ArrayList<Circle> corners;

	public Flipper(int x, int y, int width, int height, Type type) {
		super(x, y, width, height, type);
	}
	
	
	public ArrayList<LineSegment> getEdges(){
		return edges;
	}
	
	public ArrayList<Circle> getCorners(){
		return corners;
	}
	
	
}
