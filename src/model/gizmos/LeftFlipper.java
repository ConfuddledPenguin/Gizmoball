package model.gizmos;

/**
 * 
 * The left flipper
 *
 */
public class LeftFlipper extends Gizmo {
	private int angle = 0;

	public LeftFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.LeftFlipper);
	}
	
	@Override
	protected void action() {
		angle += 90;
	}
	
	@Override
	public int getAngle() {
		return angle % 180;
	}

}
