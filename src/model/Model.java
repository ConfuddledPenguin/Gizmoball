package model;

import interfaces.IModel;

import java.util.Observable;
import java.util.Observer;

import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.IGizmo;

public class Model extends Observable implements IModel {
	
	private Board board;
	
	public Model(int boardHeight, int boardWidth) {
		
		new Global(boardHeight, boardWidth);
		board = new Board();		
	}
	
	public void addGizmo(IGizmo g){
			
		board.addGizmo(g);
		setChanged();
		notifyObservers(g);	

	}
	
}
