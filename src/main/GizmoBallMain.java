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
		m.addGizmo(new Circle(15,15));
		m.addGizmo(new Circle(25,10));
		m.addGizmo(new Circle(18,9));
		m.addGizmo(new Circle(14,14));
		m.addGizmo(new Circle(29,4));	
		
		m.addGizmo(new Circle(10,4));
		m.addGizmo(new Circle(11,5));
		m.addGizmo(new Circle(12,6));
		m.addGizmo(new Circle(13,7));
		m.addGizmo(new Circle(14,8));
		m.addGizmo(new Circle(15,9));
		m.addGizmo(new Circle(16,10));
		m.addGizmo(new Circle(17,11));
		m.addGizmo(new Circle(18,12));
		m.addGizmo(new Circle(19,13));
		m.addGizmo(new Circle(20,14));
		m.addGizmo(new Circle(21,14));
		m.addGizmo(new Circle(22,14));
		m.addGizmo(new Circle(23,13));
		m.addGizmo(new Circle(26,10));
	}
}
