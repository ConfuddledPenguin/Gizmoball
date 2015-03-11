package model.gizmos;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * The left flipper
 *
 */
public class LeftFlipper extends Flipper {
	private int angle = 90;
	private boolean natural = true; // 0Ëš represents natural position, 90Ëš !natural (false)

	public LeftFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.LeftFlipper);
		
	}

	
	public void setCollisionDetails(){
		
//		if(!corners.isEmpty()) corners.clear();
//		if(!edges.isEmpty()) edges.clear();
//		
//		int x = this.getXPos();
//		int y = this.getYPos();
//		int w = this.getWidth();
//		int h = this.getHeight();
//
//		LineSegment ls1 = new LineSegment(x, y, x + w, y); // top wall
//		LineSegment ls2 = new LineSegment(x, y, x, y + h);
//		LineSegment ls3 = new LineSegment(x + w, y, x + w, y + h);
//		LineSegment ls4 = new LineSegment(x, y + h, x + w, y + h);
//
//		Circle c1 = new Circle(new Vect(x, y), 0);
//		Circle c2 = new Circle(new Vect(x + w, y), 0);
//		Circle c3 = new Circle(new Vect(x + w, y + h), 0);
//		Circle c4 = new Circle(new Vect(x, y + h), 0);
//
//		edges.add(ls1);
//		edges.add(ls2);
//		edges.add(ls3);
//		edges.add(ls4);
//
//		corners.add(c1);
//		corners.add(c2);
//		corners.add(c3);
//		corners.add(c4);
	}
	
	
	@Override
	protected void action() {

		int rotationAngle = 90; // in deg ˚
		int av = 10; // angular velocity in degrees per second
		int angleStep = (int) (av / rotationAngle * 1 / 0.01);

		if (onDown) {
			if (!natural) {
				natural = true;
				angle = 0;
			}
		} else if (!onDown) {
			if (natural) {
				natural = false;
				angle = rotationAngle;
			}
				
			}
		if (!natural) {
			if (angle < rotationAngle) {
				angle += angleStep;
				if (angle > rotationAngle) {
					angle = rotationAngle;
					natural = false;
					triggered = false;
				}
			}
		} else if (natural) {
			if (angle > 0) {
				angle -= angleStep;
				if (angle < 0) {
					angle = 0;
					natural = true;
					triggered = false;
				}
			}
		}

	}

	@Override
	public int getAngle() {
		return angle;
	}

}
