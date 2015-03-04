package model.gizmos;

import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * The absorber gizmo
 *
 */
public class Absorber extends Gizmo {
	
	private ArrayList<LineSegment> edges;
	private ArrayList<Circle> corners;
	
	public Absorber(int x, int y, int width, int height) {
		super(x, y, width, height, Gizmo.Type.Absorber);
		
		this.corners = new ArrayList<Circle>();
		this.edges = new ArrayList<LineSegment>();
		
		setCollisionDetails();
	}

	@Override
	public void trigger() {
		System.out.println("THIS WILL MOVE THE BALL AND FIRE UP!!!");
		//triggerConnections();
		
	}
	
	private void setCollisionDetails(){
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();
		
		int x = this.getXPos();
		int y = this.getYPos();
		int w = this.getWidth();
		int h = this.getHeight();

		LineSegment ls1 = new LineSegment(x, y, x + w, y); // top wall
		LineSegment ls2 = new LineSegment(x, y, x, y + h);
		LineSegment ls3 = new LineSegment(x + w, y, x + w, y + h);
		LineSegment ls4 = new LineSegment(x, y + h, x + w, y + h);

		Circle c1 = new Circle(new Vect(x, y), 0);
		Circle c2 = new Circle(new Vect(x + w, y), 0);
		Circle c3 = new Circle(new Vect(x + w, y + h), 0);
		Circle c4 = new Circle(new Vect(x, y + h), 0);

		edges.add(ls1);
		edges.add(ls2);
		edges.add(ls3);
		edges.add(ls4);

		corners.add(c1);
		corners.add(c2);
		corners.add(c3);
		corners.add(c4);
	}
	
	public ArrayList<LineSegment> getEdges(){
		return edges;
	}
	
	public ArrayList<Circle> getCorners(){
		return corners;
	}
	
	
	
	

}
