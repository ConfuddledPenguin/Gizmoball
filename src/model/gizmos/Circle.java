package model.gizmos;

public class Circle extends Gizmo {

	public Circle(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Circle);
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}

}
