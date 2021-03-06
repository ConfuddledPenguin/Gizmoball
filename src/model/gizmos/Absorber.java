package model.gizmos;

import model.IBall;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * The absorber gizmo
 *
 */
public class Absorber extends Gizmo {
	
	public Absorber(int x, int y, int width, int height) {
		super(x, y, width, height, Gizmo.Type.Absorber);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.Gizmo#action()
	 */
	@Override
	protected void action() {	
		if(triggerType == TriggerType.BALL){
			return;
		}
		
		synchronized (balls) {
		
			for(IBall b: balls){
				
				b.setX(xcoord + width - b.getRadius() - 0.25);
				b.setY(ycoord - b.getRadius());
				
				b.start();
			}
		
			balls.clear();
		}
			
//		triggerType = null;		
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.Gizmo#addBall(model.IBall)
	 */
	@Override
	public void addBall(IBall ball) {
		
		super.addBall(ball);
		
		ball.stop();
		
		int noBalls = balls.size() - 1;
		
		double offset =  noBalls;
		
		if(offset >= width)
			offset = 0;
		
		ball.setX(xcoord + width - ball.getRadius() - 0.25 - offset);
		ball.setY(ycoord + ball.getRadius() + 0.25);
		
		
		ball.setVelo(ballExitV);
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#setCollisionDetails()
	 */
	public void setCollisionDetails(){
		
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
