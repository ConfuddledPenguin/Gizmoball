package model.gizmos;

public class Triangle extends Gizmo{

	public Triangle(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Triangle);
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}

}
