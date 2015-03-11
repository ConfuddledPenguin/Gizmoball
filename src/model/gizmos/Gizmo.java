package model.gizmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import physics.Circle;
import physics.LineSegment;
import model.Global;
import model.IBall;

/**
 * A gizmo
 * 
 * This abstract class provided the base for all
 * of the gizmo's that the model has.
 */
public abstract class Gizmo implements IGizmo {
	
	/**
	 * Used to represent the type of gizmo
	 *
	 */
	public enum Type{
		Circle{
			@Override
			public String toString() {
				return "Circle";
			}
		},
		
		Square{
			@Override
			public String toString() {
				return "Square";
			}
		},
		
		Triangle{
			@Override
			public String toString() {
				return "Triangle";
			}
		},
		
		LeftFlipper{
			@Override
			public String toString() {
				return "LeftFlipper";
			}
		},
		
		RightFlipper{
			@Override
			public String toString() {
				return "RightFlipper";
			}
		},
		
		Absorber{
			@Override
			public String toString() {
				return "Absorber";
			}
		}
	}
	
	/**
	 * Used to represent the orientation
	 * of the gizmo
	 */
	public enum Orientation {
		BottomLeft{
			@Override
			public String toString() {
				return "Bottom Left";
			}
		},
		
		BottomRight{
			@Override
			public String toString() {
				return "Bottom Right";
			}
		},
		
		TopLeft{
			@Override
			public String toString() {
				return "Top Left";
			}
		},
		
		TopRight{
			@Override
			public String toString() {
				return "Top Right";
			}
		}
	}
	
	protected int xcoord;
	protected int ycoord;
	protected int width;
	protected int height;
	protected int angle;
	protected Type type;
	protected Orientation o;
	protected boolean triggered = false;
	protected boolean onDown = false;
	protected double TRIGGER_TIME = 500; // in ms
	protected double triggeredFor = 0; // in ms
	protected List<IBall> balls = new LinkedList<IBall>();
	
	/**
	 * This expresses the triggeredFor time as a
	 * percentage of the TRIGGER_TIME.
	 * 
	 * This value is expressed as a decimal and should
	 * be between 0 and 1; 
	 */
	protected double triggeredPercentage = 0;
	
	/**
	 * All of the gizmos interesting in being triggered 
	 * when this gizmo is.
	 */
	protected Set<IGizmo> connections = new HashSet<IGizmo>();
	
	/**
	 * The collision physics objects
	 */
	protected List<LineSegment> edges;
	protected List<Circle> corners;

	/**
	 * The constructor for the gizmo
	 * 
	 * @param x The x cord
	 * @param y The y cord
	 * @param width the width
	 * @param height The height
	 * @param type The Type of gizmo
	 */
	public Gizmo(int x, int y, int width, int height, Type type) {
		
		this.xcoord = x;
		this.ycoord = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.o = Orientation.TopLeft;
		
		this.corners = new ArrayList<Circle>();
		this.edges = new ArrayList<LineSegment>();
		
		setCollisionDetails();
	}
	
	/**
	 * Sets the Collision Details of the gizmo
	 * 
	 * This should be over ridden by the individual
	 * gizmos
	 */
	protected void setCollisionDetails(){
		
	}
	
	/**
	 * Performs the action of the gizmo
	 *
	 * This is called by the update method
	 * and should be overridden by individual 
	 * gizmos.
	 * 
	 * By default this does nothing, so it should
	 * be overridden to make things more interesting
	 * 
	 * This action is interested in the triggeredPercentage
	 * value.
	 */
	protected void action(){
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#update()
	 */
	public void update(){

		if(triggered){
			
			triggeredPercentage = TRIGGER_TIME / triggeredFor;
			
			triggeredFor += Global.MOVETIME;
			
			if(triggeredFor >= (TRIGGER_TIME / 1000)){
				triggered = false;
			}
			
			action();
			
		}else{
			triggeredPercentage = 0;
			triggeredFor = 0;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#trigger()
	 */
	public void trigger(Boolean onDown){
		this.onDown = onDown;

		triggered = true;
		
		triggerConnections();	
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#connection(model.gizmos.IGizmo)
	 */
	public void connection(IGizmo g){
		
		connections.add(g);
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#setPos(int, int)
	 */
	public void setPos(int x, int y) {
		this.xcoord = x;
		this.ycoord = y;
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getXPos()
	 */
	public int getXPos() {
		return xcoord;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getYPos()
	 */
	public int getYPos() {
		return ycoord;
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getHeight()
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Trigger the connections listed in the
	 * connections set.
	 */
	protected void triggerConnections(){
		
		for(IGizmo g: connections)
			g.trigger(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#rotateClockwise()
	 */
	public void rotateClockwise(){
		
		switch(this.o){
			
		case BottomLeft:
			this.o = Orientation.TopLeft;
			break;
		case BottomRight:
			this.o = Orientation.BottomLeft;
			break;
		case TopLeft:
			this.o = Orientation.TopRight;
			break;
		case TopRight:
			this.o = Orientation.BottomRight;
			break;
		default:
			break;
		}
		
		//update collision mesh
		setCollisionDetails();
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#rotateAntiClockwise()
	 */
	public void rotateAntiClockwise(){
		
		switch(this.o){
			
		case BottomLeft:
			this.o = Orientation.BottomRight;
			break;
		case BottomRight:
			this.o = Orientation.TopRight;
			break;
		case TopLeft:
			this.o = Orientation.BottomLeft;
			break;
		case TopRight:
			this.o = Orientation.TopLeft;
			break;
		default:
			break;
		}
		
		//update collision mesh
		setCollisionDetails();
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getOrientation()
	 */
	public Orientation getOrientation(){
		return o;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getType()
	 */
	public Type getType(){
		
		return type;
	}
	
	/* (non-Javadoc)
	 * @see model.gizmos.IGizmo#getAngle()
	 */
	@Override
	public int getAngle() {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see model.gizmos.IGizmo#getEdges()
	 */
	@Override
	public List<LineSegment> getEdges(){
		return Collections.unmodifiableList(edges);
	}
	
	/* (non-Javadoc)
	 * @see model.gizmos.IGizmo#getCorners()
	 */
	@Override
	public List<Circle> getCorners(){
		
		return Collections.unmodifiableList(corners);
	}
	
	/* (non-Javadoc)
	 * @see model.gizmos.IGizmo#isTriggered()
	 */
	@Override
	public boolean isTriggered(){
	
		return triggered;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#addBall()
	 */
	@Override
	public void addBall(IBall ball) {
		
		balls.add(ball);
	}
}
