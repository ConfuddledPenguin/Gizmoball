package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import view.BuildBoard;

public class MouseDraggedListener extends MouseMotionAdapter{
	
	private BuildBoard board;
	
	public MouseDraggedListener(BuildBoard board) {
		
		this.board = board;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		board.setMoveTarget(e.getPoint());	
		board.setSelectedCell(board.getMousePt());
		board.repaint();
		if(board.getGizmoMoving()) board.setMoveTarget(board.getMousePt());
	}

}
