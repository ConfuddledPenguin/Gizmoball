package model;

import interfaces.IModel;

import java.util.Observable;

public class Model extends Observable implements IModel {
	
	public Model(int boardHeight, int boardWidth) {
		
		new Global(boardHeight, boardWidth);
	}
	
}
