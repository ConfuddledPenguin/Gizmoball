package model.gizmos;

import java.awt.Point;

public abstract class Gizmo implements IGizmo {
	protected Point position;
	protected Integer width;
	protected Integer height;

	public Gizmo() {
		
	}

	public abstract void trigger();

	public void connection(IGizmo g){
		
	}

	public void setPos(int x, int y) {
		position.setLocation(x, y);
	}

	public void setWidthHeight(int w, int h) {
		width = w;
		height = h;
	}

	public Point getPos() {
		return position.getLocation();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
