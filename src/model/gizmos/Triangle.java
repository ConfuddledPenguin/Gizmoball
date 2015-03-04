package model.gizmos;

import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;


/**
 * The triangle gizmo
 *
 */
public class Triangle extends Gizmo{
	
	private ArrayList<LineSegment> edges;
	private ArrayList<Circle> corners;
	
	public Triangle(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Triangle);
		
		this.corners = new ArrayList<Circle>();
		this.edges = new ArrayList<LineSegment>();
		
		setCollisionDetails();
	}
	
	/**
	 * sets the Collision Details
	 */
	
	public void setCollisionDetails(){
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();
		
		int x = this.getXPos();
		int y = this.getYPos();
		int w = this.getWidth();
		int h = this.getHeight();

		LineSegment ls1 = new LineSegment(0, 0, 0, 0);
		LineSegment ls2 = new LineSegment(0, 0, 0, 0);
		LineSegment ls3 = new LineSegment(0, 0, 0, 0);
		
		Circle c1 = new Circle(0,0,0);
		Circle c2 = new Circle(0,0,0);
		Circle c3 = new Circle(0,0,0);


		if (this.getOrientation().equals(Orientation.BottomLeft)) {
			ls1 = new LineSegment(x, y, x, y + h);
			ls2 = new LineSegment(x, y + h, x + w, y + h);
			ls3 = new LineSegment(x + w, y + h, x, y);
			
			c1 = new Circle(x,y,0);
			c2 = new Circle(x,y+h,0);
			c3 = new Circle(x+w,y+h,0);
			
		} else if (this.getOrientation().equals(Orientation.BottomRight)) {
			ls1 = new LineSegment(x, y + h, x + w, y);
			ls2 = new LineSegment(x + w, y, x + w, y + h);
			ls3 = new LineSegment(x + w, y + h, x, y + h);
			
			c1 = new Circle(x+w,y,0);
			c2 = new Circle(x,y+h,0);
			c3 = new Circle(x+w,y+h,0);
			
		} else if (this.getOrientation().equals(Orientation.TopLeft)) {
			ls1 = new LineSegment(x, y, x + w, y);
			ls2 = new LineSegment(x + w, y, x, y + h);
			ls3 = new LineSegment(x, y + h, x, y);
			
			c1 = new Circle(x,y,0);
			c2 = new Circle(x+w,y,0);
			c3 = new Circle(x,y+h,0);
			
		} else if (this.getOrientation().equals(Orientation.TopRight)) {
			ls1 = new LineSegment(x, y, x + w, y);
			ls2 = new LineSegment(x + w, y, x + w, y + h);
			ls3 = new LineSegment(x + w, y + h, x, y);

			c1 = new Circle(x,y,0);
			c2 = new Circle(x+w,y,0);
			c3 = new Circle(x+w,y+h,0);
		}

		edges.add(ls1);
		edges.add(ls2);
		edges.add(ls3);
		
		corners.add(c1);
		corners.add(c2);
		corners.add(c3);
	}
	
	public ArrayList<LineSegment> getEdges(){
		return edges;
	}
	
	public ArrayList<Circle> getCorners(){
		return corners;
	}
	
}
