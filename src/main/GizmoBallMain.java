package main;

import model.*;
import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.Square;
import model.gizmos.Triangle;
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
		//m.addGizmo(new Absorber(0, 20, 30, 1));
		
		//m.addGizmo(new Triangle(1,29));
		//m.addGizmo(new Triangle(27,29));
		m.addGizmo(new Triangle(10,29));
		/*
		FOR LATER:
		need to think about how we add the gizmos...
		if these add gizmos were done before the add 
		absorber then the absorber wouldnt get added.
		 
		*/
		//m.addGizmo(new Triangle(15,15));
		//m.addGizmo(new Triangle(25,10));
		//m.addGizmo(new Triangle(18,9));
		//m.addGizmo(new Triangle(14,14));
		//m.addGizmo(new Triangle(29,4));		
		m.addGizmo(new Triangle(10,10));
		
		
	}
}
