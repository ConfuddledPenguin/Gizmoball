package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import model.Global;
import model.IBall;
import model.IModel;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Absorber;
import model.gizmos.IGizmo;
import view.BuildBoard;
import view.GUI;

public class MouseClickedListener extends MouseAdapter{
	
	private BuildBoard board;
	private IModel model;
	private GUI ui;
	private ActionListener listener;
	
	private Point clickedCell;
	
	public MouseClickedListener(BuildBoard board, IModel model, GUI ui, ActionListener listener) {
		
		this.board = board;
		this.model = model;
		this.ui = ui;
		this.listener = listener;
	}
	
	public void mousePressed(MouseEvent e) {

		int width = board.getWidth();
		int height = board.getHeight();
		//System."Key " + KeyEvent.getKeyText(connections.getKey())
		int cellWidth = width / Global.BOARDWIDTH;
		int cellHeight = height / Global.BOARDHEIGHT;
		int column = e.getX() / cellWidth;
		int row = e.getY() / cellHeight;

		clickedCell = new Point(column, row);
		board.setClickedCell(clickedCell);
		board.repaint();
			
		ActionListener connectingGizmos = board.getConnectingGizmos();
		if(connectingGizmos != null){
			
			connectingGizmos.actionPerformed(null);
			connectingGizmos = null;
			
			board.setConnectingGizmo(null);
			
			return;
		}
		
		
		
		// popup menus are triggered in mousePressed rather than mouseReleased on Linux
		// so we check for popup triggers in both methods to ensure cross platform compatibility 
		if (e.isPopupTrigger()) {
			Point p = new Point(e.getX() /Global.L, e.getY()/Global.L);
			IGizmo g;
			IBall b;
			if( (g=model.getGizmo(p)) != null){
				board.createGizmoPopupMenu(listener, g).show(e.getComponent(), e.getX(), e.getY());
			}else if( (b = model.getBall(p)) != null){
				board.createBallPopupMenu(listener, b).show(e.getComponent(), e.getX(), e.getY());
			}else{
				board.createEmptyPopupMenu(listener).show(e.getComponent(), e.getX(), e.getY());
			}
			if (clickedCell == null)
				mousePressed(e);
			
			return;
		}		
		
		//got here? must be moving a gizmo
		board.setGizmoMoving(true);
		board.setMoveTarget(null);
	}

	public void mouseReleased(MouseEvent e) {
		int width = board.getWidth();
		int height = board.getHeight();
		//System."Key " + KeyEvent.getKeyText(connections.getKey())
		int cellWidth = width / Global.BOARDWIDTH;
		int cellHeight = height / Global.BOARDHEIGHT;
		int column = e.getX() / cellWidth;
		int row = e.getY() / cellHeight;

		clickedCell = new Point(column, row);
		board.repaint();
		
		if(SwingUtilities.isMiddleMouseButton(e)){
			listener.actionPerformed(new ActionEvent(this, 0, "Add Last Gizmo"));
			return;
		}
		
		Point absorberStart = board.getAbsorberStart();
		if (absorberStart != null) {
			// the previous click started absorber definition, this click finishes it
			int x = 0;  // x coordinate of top left corner of absorber
			int y = 0;  // y coordinate of top left corner of absorber
			width = 0;
			height = 0;
			
			if (absorberStart.x < clickedCell.x) {
				x = absorberStart.x;
				width = clickedCell.x - absorberStart.x;
			}
			else {
				x = clickedCell.x;
				width = absorberStart.x - clickedCell.x;
			}
			
			if (absorberStart.y < clickedCell.y) {
				y = absorberStart.y;
				height = clickedCell.y - absorberStart.y;
			}
			else {
				y = clickedCell.y;
				height = absorberStart.y - clickedCell.y;
			}
			// add 1 to width and height to include clicked cells
			Absorber a = new Absorber(x, y, width+1, height+1);
			try {
				model.addGizmo(a);
				model.registerKeyStroke(32, a);
			} catch (InvalidGridPosException
					| GridPosAlreadyTakenException e1) {
				ui.displayErrorMessage(e1.getMessage());
			}
			absorberStart = null;
			board.setAbsorberStart(null);
			
			return;
		}
		

		if (e.isPopupTrigger()) {
			Point p = new Point(e.getX() /Global.L, e.getY() /Global.L);
			IGizmo g;
			IBall b;
			if( (g=model.getGizmo(p)) != null){
				board.createGizmoPopupMenu(listener, g).show(e.getComponent(), e.getX(), e.getY());
			}else if( (b = model.getBall(p)) != null){
				board.createBallPopupMenu(listener, b).show(e.getComponent(), e.getX(), e.getY());
			}else{
				board.createEmptyPopupMenu(listener).show(e.getComponent(), e.getX(), e.getY());
			}
			if (clickedCell == null)
				mousePressed(e);
		
			return;
		}
		
		//Got here must be moving something
		if(board.isGizmoMoving() && ui.getMovedPoint() != null){
			listener.actionPerformed(new ActionEvent(this, 0, "Move"));
			board.setGizmoMoving(false);
			board.setMoveTarget(null);
		}
		
	}
}
