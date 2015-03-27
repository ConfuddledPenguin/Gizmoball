package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Global;
import view.BuildBoard;

/**
 * Handles the movement of the mouse on the board screen.
 * 
 * This listens for mouse movement events and then updates
 * the view with the currently selected cell.
 *
 */
public class MouseMovementListener extends MouseAdapter {
	
	private BuildBoard board;
	
	/**
	 * The constructor for the {@link MouseMovementListener}
	 * 
	 * @param board  the board to listen for
	 */
	public MouseMovementListener(BuildBoard board) {
		
		this.board = board;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

		int width = board.getWidth();
		int height = board.getHeight();

		int cellWidth = width / Global.BOARDWIDTH;
		int cellHeight = height / Global.BOARDHEIGHT;

		int column = e.getX() / cellWidth;
		int row = e.getY() / cellHeight;

		board.setSelectedCell(new Point(column, row));
		board.repaint();

	}
}
