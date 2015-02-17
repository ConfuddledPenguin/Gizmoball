package model.gizmos;

public class Triangle extends Gizmo{
	
	private char orientation;

	public Triangle(int x, int y, char o) {
		super(x, y, 1, 1, Gizmo.Type.Triangle);
		orientation = o;
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}
	
	public char getOrientation(){
		return orientation;
	}

}
