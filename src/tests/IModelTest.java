/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Ball;
import model.IBall;
import model.IModel;
import model.Model;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		m = new Model();
	}

	@Test
	public void testAddBall() {
		assertEquals("Model must initially have no balls", 0, m.getBalls().size());

		IBall ball = m.addBall(5, 5, 5, 5);
		
		assertEquals("addBall method should return a ball", ball, new Ball(5, 5, 5, 5));
		
		assertEquals("Model should now have one ball", 1, m.getBalls().size());
		assertEquals("Ball should be represented in the model", ball, m.getBalls().get(0));
	}

}
