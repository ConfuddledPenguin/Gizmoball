package model.gizmos;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * The square gizmo
 *
 */
public class Square extends Gizmo{
	
	public Square(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Square);
		
	}
	
	/**
	 * sets the Collision Details
	 */
	protected void setCollisionDetails(){
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();

		LineSegment ls1 = new LineSegment(xcoord, ycoord, xcoord + width, ycoord); // top wall
		LineSegment ls2 = new LineSegment(xcoord, ycoord, xcoord, ycoord + height);
		LineSegment ls3 = new LineSegment(xcoord + width, ycoord, xcoord + width, ycoord + height);
		LineSegment ls4 = new LineSegment(xcoord, ycoord + height, xcoord + width, ycoord + height);

		Circle c1 = new Circle(new Vect(xcoord, ycoord), 0);
		Circle c2 = new Circle(new Vect(xcoord + width, ycoord), 0);
		Circle c3 = new Circle(new Vect(xcoord + width, ycoord + height), 0);
		Circle c4 = new Circle(new Vect(xcoord, ycoord + height), 0);

		edges.add(ls1);
		edges.add(ls2);
		edges.add(ls3);
		edges.add(ls4);

		corners.add(c1);
		corners.add(c2);
		corners.add(c3);
		corners.add(c4);
	}
}
