package model.gizmos;

import physics.Circle;
import physics.LineSegment;


/**
 * The triangle gizmo
 *
 */
public class Triangle extends Gizmo{
	
	
	public Triangle(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Triangle);		
	}
	
	/**
	 * sets the Collision Details
	 */
	@Override
	public void setCollisionDetails(){
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();

		LineSegment ls1 = new LineSegment(0, 0, 0, 0);
		LineSegment ls2 = new LineSegment(0, 0, 0, 0);
		LineSegment ls3 = new LineSegment(0, 0, 0, 0);
		
		Circle c1 = new Circle(0,0,0);
		Circle c2 = new Circle(0,0,0);
		Circle c3 = new Circle(0,0,0);


		if (this.getOrientation().equals(Orientation.BottomLeft)) {
			ls1 = new LineSegment(xcoord, ycoord, xcoord, ycoord + height);
			ls2 = new LineSegment(xcoord, ycoord + height, xcoord + width, ycoord + height);
			ls3 = new LineSegment(xcoord + width, ycoord + height, xcoord, ycoord);
			
			c1 = new Circle(xcoord,ycoord,0);
			c2 = new Circle(xcoord,ycoord+height,0);
			c3 = new Circle(xcoord+width,ycoord+height,0);
			
		} else if (this.getOrientation().equals(Orientation.BottomRight)) {
			ls1 = new LineSegment(xcoord, ycoord + height, xcoord + width, ycoord);
			ls2 = new LineSegment(xcoord + width, ycoord, xcoord + width, ycoord + height);
			ls3 = new LineSegment(xcoord + width, ycoord + height, xcoord, ycoord + height);
			
			c1 = new Circle(xcoord+width,ycoord,0);
			c2 = new Circle(xcoord,ycoord+height,0);
			c3 = new Circle(xcoord+width,ycoord+height,0);
			
		} else if (this.getOrientation().equals(Orientation.TopLeft)) {
			ls1 = new LineSegment(xcoord, ycoord, xcoord + width, ycoord);
			ls2 = new LineSegment(xcoord + width, ycoord, xcoord, ycoord + height);
			ls3 = new LineSegment(xcoord, ycoord + height, xcoord, ycoord);
			
			c1 = new Circle(xcoord,ycoord,0);
			c2 = new Circle(xcoord+width,ycoord,0);
			c3 = new Circle(xcoord,ycoord+height,0);
			
		} else if (this.getOrientation().equals(Orientation.TopRight)) {
			ls1 = new LineSegment(xcoord, ycoord, xcoord + width, ycoord);
			ls2 = new LineSegment(xcoord + width, ycoord, xcoord + width, ycoord + height);
			ls3 = new LineSegment(xcoord + width, ycoord + height, xcoord, ycoord);

			c1 = new Circle(xcoord,ycoord,0);
			c2 = new Circle(xcoord+width,ycoord,0);
			c3 = new Circle(xcoord+width,ycoord+height,0);
		}

		edges.add(ls1);
		edges.add(ls2);
		edges.add(ls3);
		
		corners.add(c1);
		corners.add(c2);
		corners.add(c3);
	}
}
