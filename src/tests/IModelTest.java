/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Ball;
import model.IBall;
import model.IModel;
import model.Model;
import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.IGizmo;
import model.gizmos.LeftFlipper;
import model.gizmos.RightFlipper;
import model.gizmos.Square;
import model.gizmos.Triangle;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Model Interface IModel
 * 
 * @author cyb12158
 *
 */
public class IModelTest {
	IModel m;


	@Before
	public void setUp() {
		m = new Model();
	}

	@Test
	public void testAddBall() {
		assertEquals("Model must initially have no balls", 0, m.getBalls()
				.size());

		IBall ball = m.addBall(5, 5, 5, 5);

		assertEquals("addBall method should return a ball", ball, new Ball(5,
				5, 5, 5));

		assertEquals("Model should now have one ball", 1, m.getBalls().size());
		assertEquals("Ball should be represented in the model", ball, m
				.getBalls().get(0));
	}

	@Test
	public void testAddGizmoSquare() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new Circle(X, Y);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}

	@Test
	public void testAddGizmoCircle() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new Circle(X, Y);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}
	
	@Test
	public void testAddGizmoTriangle() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new Triangle(X, Y);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}
	
	@Test
	public void testAddGizmoAbsorber() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new Absorber(X, Y, 5, 1);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}
	
	@Test
	public void testAddGizmoLeftFlipper() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new LeftFlipper(X, Y);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}
	
	@Test
	public void testAddGizmoRightFlipper() {
		assertEquals("Model must initially have no Gizmos", 0, m.getGizmos()
				.size());

		int X = 5, Y = 5;
		IGizmo g = new RightFlipper(X, Y);
		m.addGizmo(g);

		assertEquals("Model should now have one gizmo", 1, m.getGizmos().size());
		assertEquals("Gizmo should be represented in the model", g, m
				.getGizmos().get(0));
		assertEquals("Gizmo should be at it's point", g,
				m.getGizmo(new Point(X, Y)));
	}

}
