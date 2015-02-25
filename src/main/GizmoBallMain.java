package main;

import model.*;
import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.Square;
import view.GUI;

public class GizmoBallMain {
	/**
	 * @author Cameron, Andrew, Tom, Marc, Nathan
	 * 
	 */
	public static GUI gui;
	
	public static void main(String[] args){
		
		Model m = new Model(50, 50);
		
		gui = new GUI('r',m);
		m.addBall();
		//m.addGizmo(new Square(10,5));
		m.addGizmo(new Absorber(0, 20, 30, 10));
	}
}
