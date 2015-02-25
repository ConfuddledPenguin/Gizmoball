package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import physics.Circle;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.IncorrectFileFormatException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;
import model.Global;

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
	
	/**
	 * The constructor
	 * 
	 * @param boardHeight The height of the board
	 * @param boardWidth The width of the board;
	 */
	public Model(int boardHeight, int boardWidth) {
		new Global(boardHeight, boardWidth);
		board = new Board();
		walls = new Walls(0, -0, 30, 30);
	}
	
	/**
	 * Loads a board from the givin file
	 * 
	 * @param file The file to load from
	 * 
	 * @throws FileNotFoundException File not found
	 * @throws IOException Error reading file 
	 * @throws IncorrectFileFormatException File in the wrong format
	 */
	public void loadBoard(File file) throws FileNotFoundException, IOException, IncorrectFileFormatException{
		FileManager fm = new FileManager();
		board = fm.load(this, file);
	}
	
	/**
	 * Add a gizmo to the board
	 * 
	 * @param g the gizm to add
	 * 
	 */
	public void addGizmo(IGizmo g){
			
		try {
			board.addGizmo(g);
			setChanged();
			notifyObservers(g);
		} catch (GridPosAlreadyTakenException | InvalidGridPosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/**
	 * Returns the Board
	 * 
	 * @return the board
	 */
	public IBoard getBoard() {
		return board;
	}
	
	/**
	 * Returns the ball
	 * 
	 * @return the ball
	 */
	public IBall getBall() {
		return ball;
	}

	public void addBall() {
		
		ball = new Ball(10,19,0,-50);
		
		setChanged();
		notifyObservers(ball);
	}
	/*
	 * (non-Javadoc)
	 * @see model.IModel.setGravity(double gravity)
	 */
	@Override
	public void setGravity(double gravity) {
		Global.GRAVITY = gravity;		
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getGravity()
	 */
	@Override
	public double getGravity() {

		return Global.GRAVITY;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.setFriction(float my, float, mu2)
	 */
	@Override
	public void setFriction(float mu, float mu2) {
		
		Global.FRICTIONMU = mu;
		Global.FRICTIONMU2 = mu2;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getFrictionMU()
	 */
	@Override
	public double getFrictionMU() {
		
		return Global.FRICTIONMU;
	}

	/*
	 * (non-Javadoc)
	 * @see model.IModel.getFrictionMU2()
	 */
	@Override
	public double getFrictionMU2() {

		return Global.FRICTIONMU2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.IModel.moveBall()
	 */
	@Override
	public void moveBall() {
		double moveTime = Global.MOVETIME;
		System.out.println("movetime " + moveTime);
		
		if (ball != null && !ball.stopped()) {
			CollisionDetails cd = timeUntilCollision();
			double timeUntilCollision = cd.getTimeUntilCollision();
			
			if (timeUntilCollision > moveTime) {
				// no collision this move
				ball = moveBallForTime(ball, moveTime);
			}
			else {
				// there is a collision this move
				ball = moveBallForTime(ball, timeUntilCollision); 
				ball.setVelo(cd.getVelocity()); // update velocity after collision
			}
			
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	
	/**
	 * Updates the X and Y coordinates of ball b to
	 * represent movement over a given time
	 * @param b a ball
	 * @param moveTime the time over which the ball has moved
	 * @return ball b with updated coordinates
	 */
	private Ball moveBallForTime(Ball b, double moveTime) {
		
		
		//Detect possible crashes into shit
		
		//Move ball
		double xVelocity = b.getVelo().x();
		double yVelocity = b.getVelo().y();
		
		// calculate distance travelled
		double xDistance = xVelocity * moveTime;
		double yDistance = yVelocity * moveTime;
		System.out.println("Distance: x " + xDistance + " y " + yDistance);
		
		double newX = b.getX() + xDistance;
		double newY = b.getY() + yDistance;
		System.out.println("new coords: x " + newX + " y " + newY);
		
		b.setX(newX);
		b.setY(newY);
		
		//Work out new v
		xVelocity = xVelocity;
		yVelocity = (Global.GRAVITY * moveTime) + yVelocity; 
		Vect v = new Vect(xVelocity, yVelocity);
		b.setVelo(v);
		
		return b;
	}
	
	private CollisionDetails timeUntilCollision() {
		// physics.Circle - not gizmos.circle
		Circle ballSim = ball.getCircle(); // simulate the ball
		Vect ballVelocity = ball.getVelo();
		
		Vect newVelocity = new Vect(0,0);
		
		double shortestTime = Double.MAX_VALUE;
		double timeToObject = 0;
		
		// check for collision with walls
		ArrayList<LineSegment> wallLines = walls.getWalls();
		for (LineSegment line : wallLines) {
			timeToObject = Geometry.timeUntilWallCollision(line, ballSim, ballVelocity);
			if (timeToObject < shortestTime) {
				shortestTime = timeToObject;
				newVelocity = Geometry.reflectWall(line, ballVelocity);
			}
		}
		
		ArrayList<IGizmo> gizmos = new ArrayList<IGizmo>(board.getGizmos());
		for (IGizmo gizmo : gizmos) {
			if (gizmo instanceof model.gizmos.Circle) {
				model.gizmos.Circle mCircle = (model.gizmos.Circle)gizmo; // need access to model.Circle methods
				Vect pos = new Vect(0,0);
				
				//double radius = mCircle.getRadius(); // TODO: get circle radius
				double radius = 0.5; // use 1 for now
				Circle circleSim = new Circle(pos, radius);  // simulate the circle
				timeToObject = Geometry.timeUntilCircleCollision(circleSim, ballSim, ballVelocity);
				if (timeToObject < shortestTime) {
					shortestTime = timeToObject;
					newVelocity = Geometry.reflectCircle(circleSim.getCenter(), ballSim.getCenter(), ballVelocity);
				}
			}
			
			else if (gizmo instanceof model.gizmos.Square) {
				ArrayList<LineSegment> SqaureLines = new ArrayList<LineSegment>();
				int x = gizmo.getXPos();
				int y = gizmo.getYPos();
				int w = gizmo.getWidth();
				int h = gizmo.getHeight();
				
				LineSegment ls1 = new LineSegment(x, y, x+w, y); // top wall
				LineSegment ls2 = new LineSegment(x, y, x, y+h);
				LineSegment ls3 = new LineSegment(x+w, y, x+w, y+h);
				LineSegment ls4 = new LineSegment(x, y+h, x+w, y+h);
				
				SqaureLines.add(ls1);
				SqaureLines.add(ls2);
				SqaureLines.add(ls3);
				SqaureLines.add(ls4);
				
				for (LineSegment sqLine : SqaureLines) {
					timeToObject = Geometry.timeUntilWallCollision(sqLine, ballSim, ballVelocity);
					if (timeToObject < shortestTime) {
						shortestTime = timeToObject;
						newVelocity = Geometry.reflectWall(sqLine, ballVelocity);
					}
				}
				
			}
		}
		
		
		// TODO: check for collision with gizmos
		
		return new CollisionDetails(shortestTime, newVelocity);
		
	}
	
}
