package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Flipper;
import model.gizmos.Gizmo.TriggerType;
import model.gizmos.Gizmo.Type;
import model.gizmos.IGizmo;
import physics.Circle;
import physics.Geometry;
import physics.Geometry.VectPair;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * The model
 * 
 * This class is designed to be a controller for the model
 *
 */
public class Model extends Observable implements IModel {

	private Board board;
	private Walls walls;
	private Map<Integer, HashSet<IGizmo>> keyConnections;
	private List<IBall> balls = new LinkedList<IBall>();

	private Map<IGizmo, HashSet<IGizmo>> connections = new HashMap<IGizmo, HashSet<IGizmo>>();

	private Logger MODELLOG = Logger.getLogger("modelLog");
	private Logger PHYSICSLOG = Logger.getLogger("physicsLog");

	/**
	 * The constructor
	 */
	public Model() {

		logging.Logger.setUp(MODELLOG);
		logging.Logger.setUp(PHYSICSLOG);
		MODELLOG.log(Level.FINE, "Model started");
		MODELLOG.setLevel(Level.FINE); // Change this to change what is visible
										// in the logs

		board = new Board();
		walls = new Walls(0, 0, Global.BOARDWIDTH, Global.BOARDHEIGHT);

		keyConnections = new HashMap<Integer, HashSet<IGizmo>>();

		MODELLOG.log(Level.FINE, "Model loaded");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#loadBoard(java.io.File)
	 */
	@Override
	public void loadBoard(File file) throws FileNotFoundException, IOException,
			IncorrectFileFormatException {

		MODELLOG.log(Level.FINE,
				"Asked to load file at " + file.getAbsolutePath());

		FileManager fm = new FileManager();
		board = fm.load(this, file);
		setChanged();
		notifyObservers(board.getGizmos());

		MODELLOG.log(Level.FINE, "File loaded: " + file.getAbsolutePath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#saveBoard(java.io.File)
	 */
	@Override
	public void saveBoard(File file) throws IOException {
		// TODO write to disk
		MODELLOG.log(Level.WARNING,
				"Asked to load file at : " + file.getAbsolutePath()
						+ " . Feature not added yet");

		FileManager fm = new FileManager();

		fm.saveFile(this, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#addGizmo(model.gizmos.IGizmo)
	 */
	@Override
	public void addGizmo(IGizmo g) throws InvalidGridPosException,
			GridPosAlreadyTakenException {

		MODELLOG.log(Level.FINE,
				"Ading gizmo " + g.getType() + " to pos " + g.getXPos() + ":"
						+ g.getYPos());
		;

		board.addGizmo(g);
		setChanged();
		notifyObservers(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#deleteGizmo(java.awt.Point)
	 */
	@Override
	public void deleteGizmo(Point p) {

		IGizmo g = board.getGizmo(p.x, p.y);

		if (g != null) {
			MODELLOG.log(Level.FINE, "Deleteing gizmo " + g.getType()
					+ " at pos " + g.getXPos() + ":" + g.getYPos());
			;

			g.releaseBalls();

			board.removeGizmo(g);

			/*
			 * Remove connections
			 * 
			 * Required to remove memory leak.
			 */
			connections.remove(g);

			for (Entry<IGizmo, HashSet<IGizmo>> temp : connections.entrySet()) {
				HashSet<IGizmo> gizmos = temp.getValue();

				for (IGizmo gtemp : gizmos) {

					if (g == gtemp) {
						g.Disconnect(gtemp);
						gizmos.remove(gtemp);
					}
				}
			}

			// notify
			setChanged();
			notifyObservers(g);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getGizmo(java.awt.Point)
	 */
	@Override
	public IGizmo getGizmo(Point p) {
		return board.getGizmo(p.x, p.y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Imodel#getBall(java.awt.Point)
	 */
	public IBall getBall(Point p) {

		for (IBall b : balls) {

			System.out.println(p.x + ":" + p.y);
			System.out.println(b.getX() + ":" + b.getY());
			if ((int) b.getX() == p.x && (int) b.getY() == p.y) {
				return b;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getGizmos()
	 */
	public List<IGizmo> getGizmos() {

		return board.getGizmos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#RotateClockwise(java.awt.Point)
	 */
	@Override
	public void RotateClockwise(Point p) {

		IGizmo g = board.getGizmo(p.x, p.y);

		g.rotateClockwise();
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#RotateAntiClockwise(java.awt.Point)
	 */
	@Override
	public void RotateAntiClockwise(Point p) {

		IGizmo g = board.getGizmo(p.x, p.y);

		g.rotateAntiClockwise();
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#moveGizmo(java.awt.Point, java.awt.Point)
	 */
	@Override
	public void moveGizmo(Point gizmoPoint, Point newPoint)
			throws InvalidGridPosException, GridPosAlreadyTakenException{

		IGizmo g = this.board.getGizmo(gizmoPoint.x, gizmoPoint.y);

		if (g == null) {
			return;
		}

		for (IBall b : balls) {
			if (((int) b.getX() >= newPoint.x && (int) b.getY() >= newPoint.y)) {
				if (((int) b.getX() <= newPoint.x + g.getWidth() - 1 && (int) b
						.getY() <= newPoint.y + g.getHeight() - 1))
					throw new GridPosAlreadyTakenException("Ball in location");
			}
		}

		this.board.moveGizmo(g, gizmoPoint, newPoint);

		g.setCollisionDetails();
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#connectGizmos(model.gizmos.IGizmo, model.gizmos.IGizmo)
	 */
	@Override
	public void connectGizmos(IGizmo g1, IGizmo g2) {

		List<IGizmo> gizmos = board.getGizmos();

		if (gizmos.contains(g1) && gizmos.contains(g2)) {
			g1.connection(g2);

			HashSet<IGizmo> temp = null;
			if ((temp = connections.get(g1)) != null) {
				temp.add(g2);
			} else {
				temp = new HashSet<IGizmo>();
				temp.add(g2);
				connections.put(g1, temp);
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#disconnectGizmos(model.gizmos.IGizmo,
	 * model.gizmos.IGizmo)
	 */
	@Override
	public void disconnectGizmos(IGizmo g1, IGizmo g2) {

		List<IGizmo> gizmos = board.getGizmos();

		if (gizmos.contains(g1) && gizmos.contains(g2)) {
			g1.Disconnect(g2);

			HashSet<IGizmo> temp = connections.get(g1);
			temp.remove(g2);

		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getBoard()
	 */
	Board getBoard() {
		return board;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getBalls()
	 */
	@Override
	public List<IBall> getBalls() {

		return Collections.unmodifiableList(balls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#addBall()
	 */
	@Override
	public IBall addBall(double x, double y, double xv, double yv)
			throws InvalidGridPosException {

		IGizmo g;
		if (board.isEmpty((int) x, (int) y, 1, 1)) {
			Ball ball = new Ball(x, y, xv, yv);

			balls.add(ball);

			setChanged();
			notifyObservers(ball);

			return ball;
		}else if((g = board.getGizmo( (int) x, (int) y)).getType() == Type.Absorber){
			Ball ball = new Ball(10, 10, 0, 0);
			g.addBall(ball);
			balls.add(ball);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#deleteBall(Point p)
	 */
	@Override
	public void deleteBall(Point p) {
		// loop through all balls, if we find ball at point p -> remove it
		IBall ballsRemove = null;
		for (IBall b : balls) {
			if ((int) b.getX() == p.x && (int) b.getY() == p.y) {
				ballsRemove = b;
				
			}
		}
		balls.remove(ballsRemove);
		setChanged();
		notifyObservers(ballsRemove);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#setGravity(double)
	 */
	@Override
	public void setGravity(double gravity) {

		MODELLOG.log(Level.INFO, "Gravity set to " + gravity);
		Global.GRAVITY = gravity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getGravity()
	 */
	@Override
	public double getGravity() {

		return Global.GRAVITY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#setFriction(float, float)
	 */
	@Override
	public void setFriction(float mu, float mu2) {

		MODELLOG.log(Level.INFO, "Friction set to: mu - " + mu + " mu2 - "
				+ mu2);

		Global.FRICTIONMU = mu;
		Global.FRICTIONMU2 = mu2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getFrictionMU()
	 */
	@Override
	public double getFrictionMU() {

		return Global.FRICTIONMU;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getFrictionMU2()
	 */
	@Override
	public double getFrictionMU2() {

		return Global.FRICTIONMU2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#registerKeyStroke(int, boolean, model.gizmos.IGizmo)
	 */
	@Override
	public void registerKeyStroke(int key, IGizmo gizmo) {
		HashSet<IGizmo> set;
		if (keyConnections.get(key) != null) { // key already has asssignment
			keyConnections.get(key).add(gizmo); // add new gizmo to set
		} else {
			// key already registered
			set = new HashSet<IGizmo>();
			set.add(gizmo);
			keyConnections.put(key, set);
		}

		MODELLOG.log(Level.INFO, "Keystroke \"" + key
				+ "\" added to model, now triggering "
				+ keyConnections.get(key).size() + "gizmos");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#unRegisterKeyStroke(int, model.gizmos.IGizmo)
	 */
	@Override
	public void unRegisterKeyStroke(int key, IGizmo gizmo) {
		if (keyConnections.get(key) != null) { // key already has asssignment
			keyConnections.get(key).remove(gizmo); // remove gizmo from set
		} else
			// key already registered
			throw new RuntimeException(
					"Can't remove key connection: No such key connection exists");
		// throw out any registered keys without connections
		if (keyConnections.get(key).size() == 0)
			keyConnections.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#getKeyStrokes()
	 */
	@Override
	public Map<Integer, HashSet<IGizmo>> getKeyStrokes() {

		return Collections.unmodifiableMap(keyConnections);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#clear()
	 */
	public void clear() {

		board.clear();
		balls.clear();

		keyConnections.clear();

		setChanged();
		notifyObservers(board.getGizmos());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#reset()
	 */
	public void reset() {

		for (IBall b : balls) {
			b.reset();
		}
		this.setChanged();
		this.notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#triggerKeyPress(int, boolean)
	 */
	@Override
	public void triggerKeyPress(int key, boolean onDown) {
		MODELLOG.log(Level.INFO, "Key " + key + " processed by model");

		Set<IGizmo> gizmos = keyConnections.get(key);
		if (gizmos != null) {

			for (IGizmo g : keyConnections.get(key)) {

				TriggerType type = null;
				if (onDown) {
					type = TriggerType.ONDOWN;
				} else {
					type = TriggerType.ONUP;
				}

				g.trigger(type);
			}
		}
	}

	public void update() {

		// Update the state of all the gizmos
		for (IGizmo g : board.getGizmos()) {
			g.update();
		}

		// Move the balls
		for (IBall b : balls) {
				moveBall(b);
		}

		this.setChanged();
		this.notifyObservers();

	}

	private CollisionDetails movingGizmoCollision(IBall ball2) {

		
		Circle ballSim = ball2.getCircle();
		Vect ballVelocity = ball2.getVelo();
		Vect newVelocity = new Vect(0, 0);
		double shortestTime = Double.MAX_VALUE;
		double timeToObject = 0;
		IGizmo colidingGizmo = null;

		// Check for collision with gizmo
		for (IGizmo gizmo : board.getGizmos()) {
			if(gizmo.getType() == Type.RightFlipper || gizmo.getType() == Type.LeftFlipper){
				
				
	
				gizmo.update();
				double coefficient = gizmo.getCoefficient();
				
				int cx = (gizmo.getXPos()*Global.L)+(gizmo.getWidth()/2);
				int cy = (gizmo.getYPos()*Global.L)+(gizmo.getHeight()/4);
	
				gizmo.setCollisionDetails();
				// The gizmos edges
				for (LineSegment edge : gizmo.getEdges()) {
					timeToObject = Geometry.timeUntilRotatingWallCollision(edge, new Vect(cx,cy), ((Flipper) gizmo).getAngularVelocity(), ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectRotatingWall(edge, new Vect(cx,cy), ((Flipper) gizmo).getAngularVelocity(), ballSim, ballVelocity, coefficient);
						colidingGizmo = gizmo;
	
					}
				}
	
				// The gizmos circle components
				for (Circle corner : gizmo.getCorners()) {
					timeToObject = Geometry.timeUntilRotatingCircleCollision(corner, new Vect(cx,cy), ((Flipper) gizmo).getAngularVelocity(), ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectRotatingCircle(corner, new Vect(cx,cy), ((Flipper) gizmo).getAngularVelocity(), ballSim, ballVelocity);
						colidingGizmo = gizmo;
	
					}
				}
			}
		}// finish gizmo collision checking
		
		return new CollisionDetails(shortestTime, newVelocity, colidingGizmo);
	}

	/**
	 * Updates the given ball.
	 * 
	 * @param ball
	 */
	private void moveBall(IBall ball) {

		// move the ball
		double moveTime = Global.MOVETIME;
		PHYSICSLOG.log(Level.FINE, "Moving ball for " + moveTime);

		if (ball != null && !ball.isStopped()) {
			
			CollisionDetails cd;
			double timeUntilCollision;
			
			CollisionDetails cd1 = timeUntilCollision(ball);
			CollisionDetails cd2 = movingGizmoCollision(ball);
			
			if(cd1.getTimeUntilCollision() < cd2.getTimeUntilCollision()){
				cd = cd1;
				timeUntilCollision = cd1.getTimeUntilCollision();
			} else {
				cd = cd2;
				timeUntilCollision = cd2.getTimeUntilCollision();
			}
			
			
			if (timeUntilCollision > moveTime) { // no collisions
				PHYSICSLOG.log(Level.FINE, "No  collisions");
				ball = moveBallForTime(ball, moveTime);
			} else {// collision

				PHYSICSLOG.log(Level.FINE, "Collision detected. Handling");

				if (cd.getGizmo() != null) {

					ball = moveBallForTime(ball, timeUntilCollision);
					ball.setVelo(cd.getVelocity()); // update velocity after
													// collision

					if (cd.getGizmo() != null
							&& cd.getGizmo().getType() == Type.Absorber) {
						cd.getGizmo().addBall(ball);
					}
					cd.getGizmo().trigger(TriggerType.BALL);
				} else if (cd.getSecondBall() != null) {

					moveBallForTime(ball, timeUntilCollision);
					ball.setVelo(cd.getVelocity());
					cd.getSecondBall().setVelo(cd.getVelocity2());

				} else {
					ball = moveBallForTime(ball, timeUntilCollision);
					ball.setVelo(cd.getVelocity()); // update velocity after
													// collision
				}
			}
		}
	}

	/**
	 * Updates the X and Y coordinates of ball b to represent movement over a
	 * given time
	 * 
	 * @param ball2
	 *            a ball
	 * @param moveTime
	 *            the time over which the ball has moved
	 * @return ball b with updated coordinates
	 */
	private IBall moveBallForTime(IBall ball2, double moveTime) {

		String logString = "MOVING BALL--------------------------\nFor "
				+ moveTime + "\n";

		// Move ball
		double xVelocity = ball2.getVelo().x();
		double yVelocity = ball2.getVelo().y();

		logString = logString + "Current velocity: x " + xVelocity + " y "
				+ yVelocity + "\n";

		// calculate distance travelled
		double xDistance = xVelocity * moveTime;
		double yDistance = yVelocity * moveTime;

		logString = logString + "Moving distance: x " + xDistance + " y "
				+ yDistance + "\n";

		double newX = ball2.getX() + xDistance;
		double newY = ball2.getY() + yDistance;

		logString = logString + "New Coords: x " + newX + " y " + newY
				+ "\nTime for Friction and Gravity calcs\n";

		ball2.setX(newX);
		ball2.setY(newY);

		// Work out new v

		// Gravity
		yVelocity = (Global.GRAVITY * moveTime) + yVelocity;

		// Friction
		xVelocity = xVelocity
				* (1 - Global.FRICTIONMU * moveTime - Global.FRICTIONMU2
						* Math.abs(xVelocity) * moveTime);
		yVelocity = yVelocity
				* (1 - Global.FRICTIONMU * moveTime - Global.FRICTIONMU2
						* Math.abs(yVelocity) * moveTime);

		Vect v = new Vect(xVelocity, yVelocity);
		ball2.setVelo(v);

		PHYSICSLOG.log(Level.FINE, logString + "New velocity: x " + xVelocity
				+ " y " + yVelocity);

		return ball2;
	}

	/**
	 * Checks to see which object the ball will colide with next, and recieves
	 * the collision details.
	 * 
	 * @return New Collision Details
	 */
	private CollisionDetails timeUntilCollision(IBall ball2) {

		Circle ballSim = ball2.getCircle();
		Vect ballVelocity = ball2.getVelo();
		Vect newVelocity = new Vect(0, 0);
		double shortestTime = Double.MAX_VALUE;
		double timeToObject = 0;
		IGizmo colidingGizmo = null;

		// check for collision with walls
		for (LineSegment line : walls.getWalls()) {
			timeToObject = Geometry.timeUntilWallCollision(line, ballSim,
					ballVelocity);
			if (timeToObject < shortestTime) {
				shortestTime = timeToObject;
				newVelocity = Geometry.reflectWall(line, ballVelocity);
			}
		}

		// Check for collision with gizmo
		for (IGizmo gizmo : board.getGizmos()) {

			double coefficient = gizmo.getCoefficient();

			gizmo.setCollisionDetails();
			// The gizmos edges
			for (LineSegment edge : gizmo.getEdges()) {
				timeToObject = Geometry.timeUntilWallCollision(edge, ballSim,
						ballVelocity);
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectWall(edge, ballVelocity,
							coefficient);
					colidingGizmo = gizmo;

				}
			}

			// The gizmos circle components
			for (Circle corner : gizmo.getCorners()) {
				timeToObject = Geometry.timeUntilCircleCollision(corner,
						ballSim, ballVelocity);
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectCircle(corner.getCenter(),
							ballSim.getCenter(), ballVelocity, coefficient);
					colidingGizmo = gizmo;

				}
			}
		}// finish gizmo collision checking

		// Check for collisions with other balls
		for (IBall otherBall : balls) {

			if (otherBall != ball2) { // make sure not same ball

				Circle otherBallSim = otherBall.getCircle();
				Vect otherBallVelocity = otherBall.getVelo();
				timeToObject = Geometry.timeUntilBallBallCollision(ballSim,
						ballVelocity, otherBallSim, otherBallVelocity);

				if (timeToObject < shortestTime) {

					shortestTime = timeToObject;
					Vect center1 = new Vect(ball2.getX(), ball2.getY());
					int mass1 = 1;
					Vect velocity1 = ball2.getVelo();

					Vect center2 = new Vect(otherBall.getX(), otherBall.getY());
					int mass2 = 1;
					Vect velocity2 = otherBall.getVelo();
					VectPair pair = Geometry.reflectBalls(center1, mass1,
							velocity1, center2, mass2, velocity2);

					newVelocity = pair.v1;

					return new CollisionDetails(shortestTime, newVelocity,
							pair.v2, otherBall);

				}
			}
		}// finish other ball checking

		return new CollisionDetails(shortestTime, newVelocity, colidingGizmo);
	}
}
