package model;
import java.util.ArrayList;
import physics.LineSegment;

public class Walls {
	
	private ArrayList<LineSegment> lines;
	
	/**
	 * Creates walls surrounding the board given the co-ordinates
	 * of the top left and bottom right corners of the board.
	 * @param x1 top left corner x-position
	 * @param y1 top left corner y-position
	 * @param x2 bottom right corner x-position
	 * @param y2 bottom right corner y-position
	 */
	public Walls(int x1, int y1, int x2, int y2) {
		lines = new ArrayList<LineSegment>();
		lines.add(new LineSegment(x1, y1, x2, y1)); // top wall
		lines.add(new LineSegment(x1, y1, x1, y2)); // left wall
		lines.add(new LineSegment(x2, y1, x2, y2)); // right wall
		lines.add(new LineSegment(x1, y2, x2, y2)); // bottom wall
	}
	
	/**
	 * Returns the lines which make up the wall surrounding the board
	 * @return the walls of the board
	 */
	public ArrayList<LineSegment> getWalls() {
		return lines;
	}
	

}
