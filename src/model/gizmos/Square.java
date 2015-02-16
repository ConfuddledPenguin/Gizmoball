package model.gizmos;

public class Square extends Gizmo{

	public Square(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Square);
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}

}
