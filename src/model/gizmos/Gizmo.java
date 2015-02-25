package model.gizmos;

import java.util.HashSet;
import java.util.Set;


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

	/*
	 * (non-Javadoc)
	 * @see model.gizmos.IGizmo#trigger()
	 */
	public void trigger(){
		
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
			g.trigger();
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
}
