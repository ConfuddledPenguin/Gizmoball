/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Ball;
import model.IBall;
import model.IModel;
import model.Model;
import model.exceptions.InvalidGridPosException;
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
	public void testAddBall() throws InvalidGridPosException {
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

	@Test
	public void testClear() throws InvalidGridPosException {
		// add some stuff
		m.addBall(19.5, 18.5, 0, -50);
		IGizmo g = new Absorber(0, 19, 20, 1);
		m.addGizmo(g);
		m.registerKeyStroke(32, g);
		m.addGizmo(new Circle(10, 10));
		m.addGizmo(new Square(11, 10));
		m.addGizmo(new RightFlipper(11, 11));
		m.addGizmo(new LeftFlipper(10, 11));
		m.addGizmo(new Circle(15, 15));
		m.addGizmo(new Circle(15, 10));
		m.addGizmo(new Circle(18, 9));

		// test stuff has been added
		assertFalse("Stuff should exist", m.getBalls().isEmpty());
		assertFalse("Stuff should exist", m.getGizmos().isEmpty());

		m.clear();

		// test stuff has gone
		assertTrue("Stuff should not exist", m.getBalls().isEmpty());
		assertTrue("Stuff should not exist", m.getGizmos().isEmpty());

	}

	/**
	 * Check that new gizmo's don't start with any connections which would mess
	 * tests up
	 */
	@Test
	public void testGizmoConnection() {

		// create a couple of gizmos
		IGizmo circ = new Circle(10, 10);
		IGizmo sqr = new Square(11, 10);

		assertTrue("Must be no connections initially", circ.getConnections()
				.isEmpty());
		assertTrue("Must be no connections initially", sqr.getConnections()
				.isEmpty());
	}

	@Test
	public void testConnectGizmos() {

		// create a couple of gizmos
		IGizmo circ = new Circle(10, 10);
		IGizmo sqr = new Square(11, 10);

		m.addGizmo(circ);
		m.addGizmo(sqr);

		m.connectGizmos(circ, sqr);

		assertTrue("Should be 1 conncection to sqr", circ.getConnections()
				.contains(sqr));
		assertTrue("Should be 1 conncection to sqr", 1 == circ.getConnections()
				.size());
		assertTrue("Must be no connections in sqr", sqr.getConnections()
				.isEmpty());

		// add again
		m.connectGizmos(circ, sqr);

		assertTrue("Should still be 1 conncection to sqr", circ
				.getConnections().contains(sqr));
		assertTrue("Should still be 1 conncection to sqr", 1 == circ
				.getConnections().size());
		assertTrue("Must be no connections in sqr", sqr.getConnections()
				.isEmpty());

	}

	/**
	 * Test what happens when a connection is requested between Gizmos where 1
	 * or both don't exist in the model, or where 1 is null
	 */
	@Test
	public void testConnectGizmosNotExist() {
		// create a couple of gizmos
		IGizmo circ = new Circle(10, 10);
		IGizmo sqr = new Square(11, 10);
		IGizmo gNull = null;

		// add null
		m.connectGizmos(sqr, gNull);

		assertTrue("Must be no connections in sqr", sqr.getConnections()
				.isEmpty());

		// not exists
		m.connectGizmos(circ, sqr);
		assertTrue("Must be no connections", circ.getConnections().isEmpty());
		assertTrue("Must be no connections", sqr.getConnections().isEmpty());

		// just circ exists
		m.clear();
		m.addGizmo(circ);
		m.connectGizmos(circ, sqr);
		assertTrue("Must be no connections", circ.getConnections().isEmpty());
		assertTrue("Must be no connections", sqr.getConnections().isEmpty());

		// just sqr exists
		m.clear();
		m.addGizmo(sqr);
		m.connectGizmos(circ, sqr);
		assertTrue("Must be no connections", circ.getConnections().isEmpty());
		assertTrue("Must be no connections", sqr.getConnections().isEmpty());
	}

	@Test
	public void testDisconnectGizmos() {

		// create a couple of gizmos
		IGizmo circ = new Circle(10, 10);
		IGizmo sqr = new Square(11, 10);

		m.addGizmo(circ);
		m.addGizmo(sqr);

		m.connectGizmos(circ, sqr);
		m.disconnectGizmos(circ, sqr);

		assertTrue("Must be no connections", circ.getConnections().isEmpty());
		assertTrue("Must be no connections", sqr.getConnections().isEmpty());
	}

	@Test
	public void testDeleteBall() throws InvalidGridPosException {
		m.addBall(19.5, 18.5, 0, -50);
		m.deleteBall(new Point(1, 1));
		assertFalse("Ball should still exist", m.getBalls().isEmpty());

		m.deleteBall(new Point(19, 18));
		assertTrue("Ball should be gone", m.getBalls().isEmpty());

	}

	@Test
	public void testDeleteGizmo() throws InvalidGridPosException {
		m.addGizmo(new Square(5, 5));
		m.deleteGizmo(new Point(5, 1));
		assertFalse("Gizmo should still exist", m.getGizmos().isEmpty());

		m.deleteGizmo(new Point(5, 5));
		assertTrue("Gizmo should be gone", m.getGizmos().isEmpty());

	}
}
