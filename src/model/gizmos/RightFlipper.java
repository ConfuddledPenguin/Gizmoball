package model.gizmos;

import java.awt.geom.AffineTransform;

import model.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;


/**
 * The right flipper
 *
 */
public class RightFlipper extends Flipper {
	private final static int restingAngle = 0;
	private int angle = restingAngle;

	public RightFlipper(int x, int y) {
		super(x, y, 2, 2, Gizmo.Type.RightFlipper);
		
		coefficient = 0.95;
		setAngularVelocity(0);
		TRIGGER_TIME = 750;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#setCollisionDetails()
	 */
	@Override
	public void setCollisionDetails(){
		

		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();
		
	
		
		int w = this.getWidth()/2*Global.L;
		int h = this.getHeight()*Global.L;
		int a = this.getAngle();
		
		int cx = ((this.getXPos()+1)*Global.L)+(w/2);
		int cy = (this.getYPos()*Global.L)+(h/4);
		
		int cx2 = ((this.getXPos()+1)*Global.L)+(w/2);
		int cy2 = (this.getYPos()*Global.L)+((h/4)*3);
		
		int x1 = (this.getXPos()+1)*Global.L;
		int y1 = (this.getYPos()*Global.L) + h/4;
		
		double x2 = (x1+w);
		double y2 = (y1);
		double x3 = x1+w;
		double y3 = y1+(h/2);
		double x4 = x1;
		double y4 = y1 + (h/2);
		
		int xrotatePoint = cx;
		int yrotatePoint = cy;
		
		switch (o) {
		case TopLeft: // default
			break;
		case TopRight:
			
			cx -= Global.L;
			cy += Global.L;
			
			x1 -= Global.L - 10;
			y1 += Global.L + 10;
			
			x2 -= Global.L - 10;
			y2 += Global.L + 10;
			
			x4 -= Global.L/2;
			y4 -= Global.L/2;
			
			x3 -= Global.L/2;
			y3 -= Global.L/2;
			
			xrotatePoint = cx2;
			yrotatePoint = cy2;
			
			break;
		case BottomRight:
			
			cx -= Global.L;
			
			x1 -= Global.L;
			x2 -= Global.L;
			x3 -= Global.L;
			x4 -= Global.L;
			
			cx2 = cx;
			
			xrotatePoint = cx2;
			yrotatePoint = cy2;
			
		case BottomLeft:
			
			cx -= Global.L;
			
			x1 -= Global.L/2;
			y1 += 10;
			
			x2 -= Global.L/2;
			y2 += 10;
			
			x4 -= Global.L/2;
			y4 -= Global.L/2 + Global.L;
			
			x3 -= Global.L/2;
			y3 -= Global.L/2 + Global.L;
			
			cy2 -= Global.L;
			
			xrotatePoint = cx2;
			yrotatePoint = cy2;
			
			
			break;
		}
		
		
		double[] pt = {x1, y1, x2, y2, x3, y3, x4, y4, cx, cy, cx2, cy2};
		
		AffineTransform.getRotateInstance(Math.toRadians(a),xrotatePoint,yrotatePoint).transform(pt, 0, pt, 0, 6); 		
		
		LineSegment ls1 = new LineSegment(pt[4]/Global.L, pt[5]/Global.L, pt[2]/Global.L, pt[3]/Global.L); // right wall
		LineSegment ls2 = new LineSegment(pt[6]/Global.L,  pt[7]/Global.L, pt[0]/Global.L, pt[1]/Global.L); // left wall
		
		edges.add(ls1);
		edges.add(ls2);

		Circle c5 = new Circle(new Vect(pt[10]/Global.L, pt[11]/Global.L), 0.5);
		Circle c6 = new Circle(new Vect(pt[8]/Global.L, pt[9]/Global.L), 0.5);

		corners.add(c5);
		corners.add(c6);
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.Gizmo#action()
	 */
	@Override
	protected void action() {

		int rotationAngle = 90; // in deg ˚
		int av = (int) (1080 / Global.REFREASHRATE); // angular velocity in degrees per second
		
		if(triggerType == TriggerType.ONDOWN){
			setAngularVelocity(av);
			angle=angle+av;
			if(angle > rotationAngle){
				angle=90;
				setAngularVelocity(0);
			}
		}else if(triggerType == TriggerType.ONUP){
			setAngularVelocity(av);
			angle=angle-av;
			if(angle < restingAngle){
				setAngularVelocity(0);
				angle=restingAngle;
			}
		}else if(triggerType == TriggerType.GIZMO){
			if( triggeredPercentage < 0.5){
				angle=angle+av;
				setAngularVelocity(av);
				if(angle > rotationAngle){
					setAngularVelocity(0);
					angle=90;
				}
			}else{
				setAngularVelocity(av);
				angle=angle-av;
				if(angle < restingAngle){
					setAngularVelocity(0);
					angle=restingAngle;
				}
			}
		} else if(triggerType == TriggerType.BALL && angle > 0){
			if( triggeredPercentage < 0.5){
				setAngularVelocity(av);
				angle=angle+av;
				if(angle > rotationAngle){
					setAngularVelocity(0);
					angle=90;
				}
			}else{
				setAngularVelocity(av);
				angle=angle-av;
				if(angle < restingAngle){
					setAngularVelocity(0);
					angle=restingAngle;
				}
			}
		} else { 
			setAngularVelocity(av);
			angle=angle-av;
			if(angle < restingAngle){
				setAngularVelocity(0);
				angle=restingAngle;
			}
		}
		
		setCollisionDetails();
		
	}

	@Override
	public int getAngle() {
		return angle;
	}
}
