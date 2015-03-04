package model.gizmos;

import java.util.HashSet;
import java.util.Set;

import model.Global;


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
	
	protected int xcord;
	protected int ycord;
	protected int width;
	protected int height;
	protected int angle;
	protected Type type;
	protected Orientation o;
	protected boolean triggered = false;
	protected boolean onDown = false;
	protected double TRIGGER_TIME = 500; // in ms
	protected double triggeredFor = 0; // in seconds???
	
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
	 * The constructor for the gizmo
	 * 
	 * @param x The x cord
	 * @param y The y cord
	 * @param width the width
	 * @param height The height
	 * @param type The Type of gizmo
	 */
	public Gizmo(int x, int y, int width, int height, Type type) {
		this.xcord = x;
		this.ycord = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.o = Orientation.BottomLeft;
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
	public void trigger(boolean onDown){
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
		this.xcord = x;
		this.ycord = y;
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#setWidthHeight(int, int)
	 */
	public void setWidthHeight(int w, int h) {
		width = w;
		height = h;
	}

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getXPos()
	 */
	public int getXPos() {
		return xcord;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#getYPos()
	 */
	public int getYPos() {
		return ycord;
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
}
