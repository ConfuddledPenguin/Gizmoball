package model.gizmos;

public class RightFlipper extends Gizmo {

	public RightFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.RightFlipper);
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}

}
