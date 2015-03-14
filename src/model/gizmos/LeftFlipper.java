package model.gizmos;

import java.awt.geom.AffineTransform;

import model.Global;
import physics.LineSegment;
import physics.Vect;
import physics.Circle;


/**
 * 
 * The left flipper
 *
 */
public class LeftFlipper extends Flipper {
	private int angle = 0;

	public LeftFlipper(int x, int y) {
		super(x, y, 1, 2, Gizmo.Type.LeftFlipper);
		
		//TODO find a good time
		TRIGGER_TIME = 2500;

	}

	
	public void setCollisionDetails(){
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();
		
		int w = this.getWidth()*Global.L;
		int h = this.getHeight()*Global.L;
		int a = this.getAngle();
		
		int cx = (this.getXPos()*Global.L)+(w/2);
		int cy = (this.getYPos()*Global.L)+(h/4);
		
		int cx2 = (this.getXPos()*Global.L)+(w/2);
		int cy2 = (this.getYPos()*Global.L)+((h/4)*3);
		
		int x1 = this.getXPos()*Global.L;
		int y1 = (this.getYPos()*Global.L) + h/4;
		
		double x2 = (x1+w);
		double y2 = (y1);
		double x3 = x1+w;
		double y3 = y1+(h/2);
		double x4 = x1;
		double y4 = y1 + (h/2);
		
		double[] pt = {x1, y1, x2, y2, x3, y3, x4, y4, cx, cy, cx2, cy2};
		AffineTransform.getRotateInstance(Math.toRadians(a),cx,cy).transform(pt, 0, pt, 0, 6); 		
		
		LineSegment ls1 = new LineSegment(pt[4]/Global.L, pt[5]/Global.L, pt[2]/Global.L, pt[3]/Global.L); // right wall
		LineSegment ls2 = new LineSegment(pt[6]/Global.L,  pt[7]/Global.L, pt[0]/Global.L, pt[1]/Global.L); // left wall
		
		edges.add(ls1);
		edges.add(ls2);

		Circle c5 = new Circle(new Vect(pt[10]/Global.L, pt[11]/Global.L), 0.5);
		Circle c6 = new Circle(new Vect(pt[8]/Global.L, pt[9]/Global.L), 0.5);

		corners.add(c5);
		corners.add(c6);

	}
	
	
	@Override
	protected void action() {

		int rotationAngle = 90; // in deg ˚
		int av = 15; // angular velocity in degrees per second
		
		if(triggerType == TriggerType.ONDOWN){
			if(angle < rotationAngle){
				angle=angle+av;
			}
		}else if(triggerType == TriggerType.ONUP){
			if(angle > 0){
				angle=angle-av;
			}
		}
		
		if(triggerType == TriggerType.GIZMO || triggerType == TriggerType.BALL){
			if( triggeredPercentage < 0.5){
				if(angle < rotationAngle){
					angle=angle+av;
				}
			}else{
				if(angle > 0){
					angle=angle-av;
				}
			}
		}
		
		System.out.println(angle);
		
		setCollisionDetails();
		
	}

	@Override
	public int getAngle() {
		return -angle;
	}
}
