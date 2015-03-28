package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.SwingUtilities;

import view.BuildBoard;

public class MouseDraggedListener extends MouseMotionAdapter{
	
	private BuildBoard board;
	
	public MouseDraggedListener(BuildBoard board) {
		
		this.board = board;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(SwingUtilities.isMiddleMouseButton(e)){
			return;
		}
		
		board.setMoveTarget(e.getPoint());	
		board.setSelectedCell(board.getMousePt());
		board.repaint();
		if(board.isGizmoMoving()) board.setMoveTarget(board.getMousePt());
	}

}
