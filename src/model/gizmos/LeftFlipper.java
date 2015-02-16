package model.gizmos;

public class LeftFlipper extends Gizmo {

	public LeftFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.LeftFlipper);
	}
	
	@Override
	public void trigger() {
		
		triggerConnections();
	}

}
