package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Absorber;
import model.gizmos.Gizmo;
import model.gizmos.Gizmo.Orientation;
import model.gizmos.IGizmo;
import model.gizmos.Square;
import model.gizmos.Triangle;
import physics.Circle;
import physics.Geometry;
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
	private Ball ball;
	private Walls walls;
	private boolean absorberHit;
	private Map<Integer, HashSet<IGizmo>> keyConnections;

	private Logger MODELLOG = Logger.getLogger("modelLog");
	private Logger PHYSICSLOG = Logger.getLogger("physicsLog");

	/**
	 * The constructor
	 * 
	 * @param boardHeight
	 *            The height of the board
	 * @param boardWidth
	 *            The width of the board;
	 */
	public Model() {

		logging.Logger.setUp(MODELLOG);
		logging.Logger.setUp(PHYSICSLOG);
		MODELLOG.log(Level.FINE, "Model started");
		
		board = new Board();
		walls = new Walls(0, 0, Global.BOARDWIDTH, Global.BOARDHEIGHT);

		keyConnections = new HashMap<Integer, HashSet<IGizmo>>();

		MODELLOG.log(Level.FINE, "Model loaded");

		absorberHit = false;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#addGizmo(model.gizmos.IGizmo)
	 */
	@Override
	public void addGizmo(IGizmo g) {

		MODELLOG.log(Level.FINE,
				"Ading gizmo " + g.getType() + " to pos " + g.getXPos() + ":"
						+ g.getYPos());
		;

		try {
			board.addGizmo(g);
			setChanged();
			notifyObservers(g);
		} catch (GridPosAlreadyTakenException | InvalidGridPosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#deleteGizmo(java.awt.Point)
	 */
	@Override
	public void deleteGizmo(Point p) {

		IGizmo g = board.getGizmo(p.x, p.y);

		MODELLOG.log(Level.FINE, "Deleteing gizmo " + g.getType() + " at pos "
				+ g.getXPos() + ":" + g.getYPos());
		;

		board.removeGizmo(g);

		setChanged();
		notifyObservers(g);
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
	public void moveGizmo(Point gizmoPoint, Point newPoint) throws InvalidGridPosException, GridPosAlreadyTakenException {

		IGizmo g = this.board.getGizmoForMove(gizmoPoint);
		this.board.moveGizmo(g, gizmoPoint, newPoint);

		setChanged();
		notifyObservers();
	}

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
	 * @see model.IModel#getBall()
	 */
	@Override
	public IBall getBall() {
		return ball;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#addBall()
	 */
	@Override
	public void addBall() {

		// ball = new Ball(9.5,19,0,-50); //old one
		// ball = new Ball(28.5,19,0,-50); //new one
		ball = new Ball(18.5, 18.5, -10, -50); // testing one

		setChanged();
		notifyObservers(ball);
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
	
	public void clear(){
		board.clear();
		
		keyConnections = new HashMap<Integer, HashSet<IGizmo>>();
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
				
				if(g.getType() == Gizmo.Type.Absorber){
					if(absorberHit){
						
						Vect v = new Vect(-10, -50);
						ball.setVelo(v);
						absorberHit = false;
						ball.start();
					}
				}else{
					g.trigger();
				}
				System.out.println("TRIGGER");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#moveBall()
	 */
	@Override
	public void moveBall() {

		// Update the state of all the gizmos
		for (IGizmo g : board.getGizmos()) {
			g.update();
		}

		// move the ball
		double moveTime = Global.MOVETIME;
		PHYSICSLOG.log(Level.FINE, "Moving ball for " + moveTime);

		if (ball != null && !ball.isStopped()) {
			CollisionDetails cd = timeUntilCollision();
			double timeUntilCollision = cd.getTimeUntilCollision();

			if (timeUntilCollision > moveTime) { // no collisions
				PHYSICSLOG.log(Level.FINE, "No  collisions");
				ball = moveBallForTime(ball, moveTime);
			} else {// collision

				PHYSICSLOG.log(Level.FINE, "Collision detected. Handling");
				
				ball = moveBallForTime(ball, timeUntilCollision); 
				ball.setVelo(cd.getVelocity()); // update velocity after collision
				
				if(getAbsorberHit()){
					System.out.println("ABSORBER HIT"); //testing
					ball.setX(19.5);
					ball.setY(18.5);
					ball.stop();
					Vect v = new Vect(0, 0);
					ball.setVelo(v);
				}
			}

			this.setChanged();
			this.notifyObservers();
		}
	}

	/**
	 * 
	 * @param hit
	 * 
	 *            this is to set the absorberHit attribute that is used for the
	 *            absorber
	 */
	public void setAbsorberHit(boolean hit) {
		absorberHit = hit;
	}

	/**
	 * 
	 * @return absorberHit this is so moveBall() knows if it has hit the
	 *         absorber or not
	 */
	private boolean getAbsorberHit() {
		return absorberHit;
	}

	/**
	 * Updates the X and Y coordinates of ball b to represent movement over a
	 * given time
	 * 
	 * @param b
	 *            a ball
	 * @param moveTime
	 *            the time over which the ball has moved
	 * @return ball b with updated coordinates
	 */
	private Ball moveBallForTime(Ball b, double moveTime) {

		String logString = "MOVING BALL--------------------------\nFor "
				+ moveTime + "\n";

		// Move ball
		double xVelocity = b.getVelo().x();
		double yVelocity = b.getVelo().y();

		logString = logString + "Current velocity: x " + xVelocity + " y "
				+ yVelocity + "\n";

		// calculate distance travelled
		double xDistance = xVelocity * moveTime;
		double yDistance = yVelocity * moveTime;

		logString = logString + "Moving distance: x " + xDistance + " y "
				+ yDistance + "\n";

		double newX = b.getX() + xDistance;
		double newY = b.getY() + yDistance;

		logString = logString + "New Coords: x " + newX + " y " + newY
				+ "\nTime for Friction and Gravity calcs\n";

		b.setX(newX);
		b.setY(newY);

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
		b.setVelo(v);

		PHYSICSLOG.log(Level.FINE, logString + "New velocity: x " + xVelocity
				+ " y " + yVelocity);

		return b;
	}

	
	/**
	 * Checks to see which object the ball will colide with next, and recieves
	 * the collision details.
	 * 
	 * @return New Collision Details
	 */
	
	private CollisionDetails timeUntilCollision() {

		Circle ballSim = ball.getCircle();
		Vect ballVelocity = ball.getVelo();
		Vect newVelocity = new Vect(0, 0);
		double shortestTime = Double.MAX_VALUE;
		double timeToObject = 0;

		
		// check for collision with walls
		for (LineSegment line : walls.getWalls()) {
			timeToObject = Geometry.timeUntilWallCollision(line, ballSim,ballVelocity);
			if (timeToObject < shortestTime) {
				shortestTime = timeToObject;
				newVelocity = Geometry.reflectWall(line, ballVelocity);
			}
		}

		
		for (IGizmo gizmo : board.getGizmos()) {
			if (gizmo instanceof model.gizmos.Circle) {

				model.gizmos.Circle mCircle = (model.gizmos.Circle) gizmo;													
				Vect pos = new Vect(mCircle.getXPos()+0.5, mCircle.getYPos()+0.5);

				double radius = (double) mCircle.getWidth() / 2;
				Circle circleSim = new Circle(pos, radius); 
				
				timeToObject = Geometry.timeUntilCircleCollision(circleSim,ballSim, ballVelocity);
				
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectCircle(circleSim.getCenter(),
							ballSim.getCenter(), ballVelocity);
					setAbsorberHit(false);
				}
			
			} else if (gizmo instanceof model.gizmos.Square) {

				for (LineSegment edge : ((Square) gizmo).getEdges()) {
					timeToObject = Geometry.timeUntilWallCollision(edge,ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectWall(edge, ballVelocity);
					}
				}

				for (Circle corner : ((Square) gizmo).getCorners()) {
					timeToObject = Geometry.timeUntilCircleCollision(corner,
							ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectCircle(corner.getCenter(),ballSim.getCenter(), ballVelocity);
						setAbsorberHit(false);
					}
				}
				
			} else if (gizmo instanceof Triangle) {
				
				/*Recalculates the collison details for the triangle incase there was a rotation*/
				((Triangle) gizmo).setCollisionDetails();

				for (LineSegment tLine : ((Triangle) gizmo).getEdges()) {
					timeToObject = Geometry.timeUntilWallCollision(tLine,ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectWall(tLine, ballVelocity);
					}
				}

				for (Circle corner : ((Triangle) gizmo).getCorners()) {
					timeToObject = Geometry.timeUntilCircleCollision(corner,ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectCircle(corner.getCenter(), ballSim.getCenter(),ballVelocity);
					}
				}
				
			} else if (gizmo instanceof Absorber) {
				
				for (LineSegment abLine : ((Absorber) gizmo).getEdges()) {
					
					timeToObject = Geometry.timeUntilWallCollision(abLine,ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectWall(abLine, ballVelocity);
						setAbsorberHit(true);
					}
				}
				
				for (Circle corner : ((Absorber) gizmo).getCorners()) {
					timeToObject = Geometry.timeUntilCircleCollision(corner,ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectCircle(corner.getCenter(),ballSim.getCenter(), ballVelocity);
						setAbsorberHit(true);
					}
				}
			}
		}

		return new CollisionDetails(shortestTime, newVelocity);
	}

}
