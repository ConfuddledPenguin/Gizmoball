package model.gizmos;

import model.Global;

/**
 * The right flipper
 *
 */
public class RightFlipper extends Gizmo {
	private int angle = 0;
	private boolean natural = true; // 0˚ represents natural position, 90˚ !natural (false)

	public RightFlipper(int x, int y) {
		super(x, y, 2, 1, Gizmo.Type.RightFlipper);
	}
	
	@Override
	protected void action() {

		int rotationAngle = -90; // in deg ˚
		int av = 1080; // angular velocity in degrees per second
		int angleStep = (int) (av / Math.abs(rotationAngle) * 1 / Global.MOVETIME);

		if (onDown) {
			if (!natural) {
				natural = true;
				angle = 0;
			}
		} else if (!onDown) {
			if (natural) {
				natural = false;
				angle = rotationAngle;
			}
				
			}
		if (natural) {
			if (angle > rotationAngle) {
				angle -= angleStep;
				if (angle < rotationAngle) {
					angle = rotationAngle;
					natural = false;
					triggered = false;
				}
			}
		} else if (!natural) {
			if (angle < 0) {
				angle += angleStep;
				if (angle > 0) {
					angle = 0;
					natural = true;
					triggered = false;
				}
			}
		}

	}

	@Override
	public int getAngle() {
		return angle;
	}
}
