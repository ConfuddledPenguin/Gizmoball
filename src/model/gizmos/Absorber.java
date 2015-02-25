package model.gizmos;

/**
 * The absorber gizmo
 *
 */
public class Absorber extends Gizmo {
	
	public Absorber(int x, int y, int width, int height) {
		super(x, y, width, height, Gizmo.Type.Absorber);
	}

	@Override
	public void trigger() {
		
		triggerConnections();
		
	}

}
