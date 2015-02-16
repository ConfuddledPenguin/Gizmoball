package model;

import java.util.Observable;

import interfaces.IModel;

public class Model extends Observable implements IModel {
	
	public Model(int boardHeight, int boardWidth) {
		
		new Global(boardHeight, boardWidth);
	}
	
}
