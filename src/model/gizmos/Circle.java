package model.gizmos;


import physics.Vect;

/**
 * The circle gizmo
 *
 */
public class Circle extends Gizmo {

	public Circle(int x, int y) {
		super(x, y, 1, 1, Gizmo.Type.Circle);
	}
	
	@Override
	public void setCollisionDetails() {
		
		if(!corners.isEmpty()) corners.clear();
		if(!edges.isEmpty()) edges.clear();
		
		physics.Circle c1 = new physics.Circle(new Vect(xcoord + 0.5, ycoord + 0.5), (double) width / 2);
		
		corners.add(c1);
	}

}
