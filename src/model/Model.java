package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Gizmo;
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
	private Ball ball;
	private Walls walls;
	private Map<Integer, HashSet<IGizmo>> keyConnections;
	private List<Ball> balls = new LinkedList<Ball>();

	private Logger MODELLOG = Logger.getLogger("modelLog");
	private Logger PHYSICSLOG = Logger.getLogger("physicsLog");

	/**
	 * The constructor
	 */
	public Model() {

		logging.Logger.setUp(MODELLOG);
		logging.Logger.setUp(PHYSICSLOG);
		MODELLOG.log(Level.FINE, "Model started");
		MODELLOG.setLevel(Level.FINE);
		
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
	 * @see model.IModel#getBalls()
	 */
	@Override
	public List<IBall> getBalls() {
		List<IBall> iBalls = new LinkedList<IBall>();
		for (Ball b : balls) {
			iBalls.add((IBall)b);
			
		}
		return Collections.unmodifiableList(iBalls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#addBall()
	 */
	@Override
	public IBall addBall(double x, double y, double xv, double yv) {

		Ball ball = new Ball(x,y,xv,yv);
		
		balls.add(ball);
		
		setChanged();
		notifyObservers(ball);
		
		return ball;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IModel#deleteBall(Point p)
	 */
	@Override
	public void deleteBall(Point p){
		
		//TODO remove ball
		System.out.println("Asked to remove ball");
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
		balls.clear();
		
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
					if(ball.isStopped()){
						
						Vect v = new Vect(0, -50);
						ball.setVelo(v);
						ball.start();
					}
				}else{
					g.trigger(onDown);
				}
				System.out.println("TRIGGER");
			}
		}
	}
	
	public void update(){
		
		// Update the state of all the gizmos
		for (IGizmo g : board.getGizmos()) {
			g.update();
		}
		
		for(Ball b : balls){
			moveBall(b);
		}
		
		this.setChanged();
		this.notifyObservers();
		
	}

	/**
	 * Updates the given ball.
	 * 
	 * @param ball
	 */
	public void moveBall(Ball ball) {

		

		// move the ball
		double moveTime = Global.MOVETIME;
		PHYSICSLOG.log(Level.FINE, "Moving ball for " + moveTime);

		if (ball != null && !ball.isStopped()) {
			CollisionDetails cd = timeUntilCollision(ball);
			double timeUntilCollision = cd.getTimeUntilCollision();

			if (timeUntilCollision > moveTime) { // no collisions
				PHYSICSLOG.log(Level.FINE, "No  collisions");
				ball = moveBallForTime(ball, moveTime);
			} else {// collision

				PHYSICSLOG.log(Level.FINE, "Collision detected. Handling");
				
				if(cd.getGizmo() != null){
				
					ball = moveBallForTime(ball, timeUntilCollision); 
					ball.setVelo(cd.getVelocity()); // update velocity after collision
					
					if(cd.getGizmo() != null && cd.getGizmo().getType() == Gizmo.Type.Absorber){
						ball.setX(19.6);
						ball.setY(18.9);
						ball.stop();
						Vect v = new Vect(0, 0);
						ball.setVelo(v);
						
						this.ball = ball;
					}
					
				}else if (cd.getSecondBall() != null){
					
					moveBallForTime(ball, timeUntilCollision);
					ball.setVelo(cd.getVelocity());
					cd.getSecondBall().setVelo(cd.getVelocity2());
					
				}else{
					ball = moveBallForTime(ball, timeUntilCollision); 
					ball.setVelo(cd.getVelocity()); // update velocity after collision
				}
			}
		}
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
	private CollisionDetails timeUntilCollision(Ball ball) {

		Circle ballSim = ball.getCircle();
		Vect ballVelocity = ball.getVelo();
		Vect newVelocity = new Vect(0, 0);
		double shortestTime = Double.MAX_VALUE;
		double timeToObject = 0;
		IGizmo colidingGizmo = null;

		
		// check for collision with walls
		for (LineSegment line : walls.getWalls()) {
			timeToObject = Geometry.timeUntilWallCollision(line, ballSim,ballVelocity);
			if (timeToObject < shortestTime) {
				shortestTime = timeToObject;
				newVelocity = Geometry.reflectWall(line, ballVelocity);
			}
		}

		//Check for collision with gizmo
		for (IGizmo gizmo : board.getGizmos()) {

			//The gizmos edges
			for (LineSegment edge : gizmo.getEdges()) {
				timeToObject = Geometry.timeUntilWallCollision(edge,ballSim, ballVelocity);
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectWall(edge, ballVelocity);
					colidingGizmo = gizmo;
					
				}
			}

			//The gizmos circle components
			for (Circle corner : gizmo.getCorners()) {
				timeToObject = Geometry.timeUntilCircleCollision(corner,
						ballSim, ballVelocity);
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectCircle(corner.getCenter(),ballSim.getCenter(), ballVelocity);
					colidingGizmo = gizmo;
					
				}
			}
		}//finish gizmo collision checking
		
		//Check for collisions with other balls
		for( IBall otherBall: balls){
			
			if(otherBall != ball){ // make sure not same ball
				
				Circle otherBallSim = otherBall.getCircle();
				Vect otherBallVelocity = otherBall.getVelo();
				timeToObject = Geometry.timeUntilBallBallCollision(ballSim, ballVelocity, otherBallSim, otherBallVelocity);
				
				if(timeToObject < shortestTime){
					
					shortestTime = timeToObject;
					Vect center1 = new Vect(ball.getX(), ball.getY());
					int mass1 = 1;
					Vect velocity1 = ball.getVelo();
					
					Vect center2 = new Vect(otherBall.getX(), otherBall.getY());
					int mass2 = 1;
					Vect velocity2 = otherBall.getVelo();
					VectPair pair = Geometry.reflectBalls(center1, mass1, velocity1, center2, mass2, velocity2);
					
					newVelocity = pair.v1;
					
					return new CollisionDetails(shortestTime, newVelocity, pair.v2, otherBall);
					
				}
			}
		}//finish other ball checking
		
		return new CollisionDetails(shortestTime, newVelocity, colidingGizmo);
	}
}
