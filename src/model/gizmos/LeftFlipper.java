package model.gizmos;

import model.Global;

/**
 * 
 * The left flipper
 *
 */
public class LeftFlipper extends Gizmo {
	private int angle = 0;
	private boolean natural = true; // 0˚ represents natural position, 90˚ !natural (false)

	public LeftFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.LeftFlipper);
	}

	@Override
	protected void action() {

		int rotationAngle = 90; // in deg ˚
		int av = 1080; // angular velocity in degrees per second
		int angleStep = (int) (av / rotationAngle * 1 / Global.MOVETIME);

		if (natural) {
			if (angle < 90) {
				angle += angleStep;
				if (angle > 90) {
					angle = 90;
					natural = false;
					triggered = false;
				}
			}
		} else if (!natural) {
			if (angle > 0) {
				angle -= angleStep;
				if (angle < 0) {
					angle = 0;
					natural = true;
					triggered = false;
				}
			}
		}

	}

	@Override
	public int getAngle() {
		return angle % 180;
	}

}
