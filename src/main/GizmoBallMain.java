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
		
		Triangle t1 = new Triangle(1,29);//tl
		t1.rotateClockwise();
		
		Triangle t2 = new Triangle(10,10);//bl
		
		Triangle t3 = new Triangle(10,29);
		t3.rotateAntiClockwise();
		
		Triangle t4 = new Triangle(15,15);
		t4.rotateClockwise();
		t4.rotateClockwise();
		
		m.addGizmo(t1);
		m.addGizmo(t2);
		m.addGizmo(t3);
		m.addGizmo(t4);
		
		
		//m.addGizmo(new Absorber(0, 20, 30, 1));
		
		m.addGizmo(new Square(14,29));
		m.addGizmo(new Circle(27,29));
		m.addGizmo(new Square(10,29));
		/*
		FOR LATER:
		need to think about how we add the gizmos...
		if these add gizmos were done before the add 
		absorber then the absorber wouldnt get added.
		 
		*/
		m.addGizmo(new Circle(15,15));
		m.addGizmo(new Square(25,10));
		m.addGizmo(new Circle(18,9));
		m.addGizmo(new Square(14,14));
		m.addGizmo(new Circle(29,4));		
		m.addGizmo(new Square(10,8));
		
		
	}
}
