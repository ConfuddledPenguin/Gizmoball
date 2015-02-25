package main;

import model.*;
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
		m.addGizmo(new Circle(10,5));
		m.addGizmo(new Circle(1,29));
		m.addGizmo(new Circle(27,29));
		m.addGizmo(new Circle(10,29));
		m.addGizmo(new Circle(15,15));
		m.addGizmo(new Circle(25,10));
		m.addGizmo(new Circle(18,9));
		m.addGizmo(new Circle(14,14));
		m.addGizmo(new Circle(29,4));
	}
}
